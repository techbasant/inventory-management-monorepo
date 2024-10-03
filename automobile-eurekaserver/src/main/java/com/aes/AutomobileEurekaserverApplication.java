package com.aes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class AutomobileEurekaserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomobileEurekaserverApplication.class, args);
	}

}
