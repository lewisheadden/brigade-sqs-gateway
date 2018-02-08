package com.lewisheadden.brigadesqsgateway.k8s;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "k8s")
@Data
@Validated
public class K8sProperties {
  @NotNull
  private String namespace;
  @NotNull
  private String master;
  private String token;
}
