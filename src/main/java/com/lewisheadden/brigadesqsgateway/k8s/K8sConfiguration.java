package com.lewisheadden.brigadesqsgateway.k8s;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class K8sConfiguration {

  @Bean
  public ApiClient apiClient(final K8sProperties properties) {
    return Config.fromToken(properties.getMaster(), properties.getToken());
  }

  @Bean
  public CoreV1Api coreV1Api(final ApiClient client) {
    return new CoreV1Api(client);
  }

}
