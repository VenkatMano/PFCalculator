package com.apache.pfcalculator.dataprocessservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableAutoConfiguration
@EnableEurekaClient
public class DataprocessServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataprocessServiceApplication.class, args);
	}

}
