package com.lewisheadden.brigadesqsgateway.sqs;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SqsBrigadeEvent {
  private String project;
  private String type;
  private String payload;
  private String script;
}
