package com.lewisheadden.brigadesqsgateway.sqs;

import com.google.common.collect.Lists;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadArgumentResolver;

@Configuration
public class SqsConfiguration {

  @Bean
  public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory() {
    final SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
    factory.setWaitTimeOut(20);
    factory.setQueueMessageHandler(queueMessageHandler());
    return factory;
  }

  private QueueMessageHandler queueMessageHandler() {
    final QueueMessageHandler handler = new QueueMessageHandler();
    final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    final PayloadArgumentResolver resolver = new PayloadArgumentResolver(converter);
    handler.setArgumentResolvers(Lists.newArrayList(resolver));
    return handler;
  }

}
