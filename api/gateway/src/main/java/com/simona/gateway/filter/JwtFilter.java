package com.simona.gateway.filter;

import com.simona.gateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtFilter extends AbstractGatewayFilterFactory<JwtFilter.Config> {

    private final JwtUtil jwtUtil;
    private final RouteValidator routeValidator;

    public JwtFilter(JwtUtil jwtUtil, RouteValidator routeValidator) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
        this.routeValidator = routeValidator;
    }


    public static class Config {
        // Put the configuration properties
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // Check if the request is public or secured
            if (routeValidator.isSecured.test(request)) {
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                String jwt = authorizationHeader.replace("Bearer", "");

                // validate the token
                if (!jwtUtil.isValidToken(jwt)) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            }
            return chain.filter(exchange);

        };
    }
}
