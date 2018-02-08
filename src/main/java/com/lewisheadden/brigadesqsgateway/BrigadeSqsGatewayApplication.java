package com.lewisheadden.brigadesqsgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;

@SpringBootApplication
@EnableSqs
public class BrigadeSqsGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrigadeSqsGatewayApplication.class, args);
	}
}
