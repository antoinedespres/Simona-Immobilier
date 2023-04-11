package com.simona.gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", url = "${gateway.url}/auth/api/v1")
public interface AuthClient {
    @GetMapping("/validate")
    ResponseEntity<String> validateToken(@RequestParam String token);
}
