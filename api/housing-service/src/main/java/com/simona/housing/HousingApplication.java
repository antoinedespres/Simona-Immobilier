package com.simona.housing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@OpenAPIDefinition(info =
@Info(title = "Housing service API", version = "1.0", description = "Documentation Housing service API v1.0"), servers = {
		@Server(url = "/housing/api/v1", description = "Gateway Server URL")
}
)
public class HousingApplication {


	public static void main(String[] args) {
		SpringApplication.run(HousingApplication.class, args);
	}
}
