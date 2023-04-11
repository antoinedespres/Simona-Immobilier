package com.simona.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> publicApiEndpoints = List.of(
            "/swagger-ui.html",
            "/auth/api/v1/v3/api-docs",
            "/housing/api/v1/v3/api-docs",
            "/rental/api/v1/v3/api-docs"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> publicApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
