package com.lewisheadden.brigadesqsgateway.sqs;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.lewisheadden.brigadesqsgateway.k8s.K8sProperties;
import de.huxhorn.sulky.ulid.ULID;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.models.V1Secret;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class SqsEventListener {

  private static final Logger logger = LoggerFactory.getLogger(SqsEventListener.class);
  private CoreV1Api coreV1Api;
  private K8sProperties k8sProperties;
  private ULID ulid = new ULID();

  public SqsEventListener(final CoreV1Api coreV1Api, final K8sProperties k8sProperties) {
    this.coreV1Api = coreV1Api;
    this.k8sProperties = k8sProperties;
  }

  @SqsListener("${brigade.queue}")
  public void handleEvent(final SqsBrigadeEvent event) {
    logger.info("Received: {}", event);
    try {
      coreV1Api.createNamespacedSecret(k8sProperties.getNamespace(), createSecret(event), "False");
    } catch (ApiException e) {
      logger.warn("Failed creating secret for event", e);
    }
  }

  private V1Secret createSecret(final SqsBrigadeEvent event) {
    final String buildId = ulid.nextULID();
    final String eventName = ("sqs-gateway-" + buildId).toLowerCase();
    final String projectName = projectName(event.getProject());
    return new V1Secret()
        .metadata(
            new V1ObjectMeta()
                .name(eventName)
                .putLabelsItem("heritage", "brigade")
                .putLabelsItem("project", projectName)
                .putLabelsItem("build", buildId)
                .putLabelsItem("commit", "master")
                .putLabelsItem("component", "build"))
        .type("Opaque")
        .putStringDataItem("event_provider", "brigade-sqs-gateway")
        .putStringDataItem("event_type", event.getType())
        .putStringDataItem("commit", "master")
        .putStringDataItem("build_name", eventName)
        .putStringDataItem("project_id", projectName)
        .putStringDataItem("build_id", buildId)
        .putStringDataItem("payload", "{}")
        .putStringDataItem("script", event.getScript());
  }

  private String projectName(final String name) {
    return "brigade-" + sha256(name).substring(0, 54);
  }

  private String sha256(String name) {
    return Hashing.sha256().hashString(name, Charsets.UTF_16LE).toString();
  }
}
