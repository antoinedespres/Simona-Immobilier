package com.simona.housing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HousingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HousingApplication.class, args);
	}

}
