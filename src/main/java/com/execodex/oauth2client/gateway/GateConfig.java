package com.execodex.oauth2client.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class GateConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/public/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2Login(Customizer.withDefaults())
                ;
        return http.build();
    }

    @Bean
    RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r
                        .path("/public/**")
                        .uri("http://localhost:8081/public")
                )
                .route(r -> r
                        .path("/**")
                        .filters(GatewayFilterSpec::tokenRelay)
                        .uri("http://localhost:8081"))
                .build();
    }
}
