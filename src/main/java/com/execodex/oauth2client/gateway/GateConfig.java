package com.execodex.oauth2client.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@Configuration
@EnableWebFluxSecurity
public class GateConfig {


    @Bean
    RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder
                .routes()

//                .route(r -> r
//                        .path("/interesting")
//                        .filters(GatewayFilterSpec::tokenRelay)
//                        .uri("http://localhost:8081/interesting")
//                )
//                .route(r -> r
//                        .path("/int")
//                        .filters(GatewayFilterSpec::tokenRelay)
//                        .uri("http://localhost:8081/int")
//                )
                .route(r -> r
                        .path("/public")
                        .uri("http://localhost:8081/public")
                )
                .route(r -> r
                        .path("/**")
                        .filters(GatewayFilterSpec::tokenRelay)
                        .uri("http://localhost:8081"))
                .build();
    }
}
