package com.interland.api.gateway;

import java.util.Collections;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.interland.api.gateway.filter.AuthenticationFilter;

@Configuration
public class MyFilterConfig {

    @Bean
    public GlobalFilter myFilter(AuthenticationFilter authenticationFilter) {
        try {
            return (exchange, chain) -> {
                return authenticationFilter.apply(new AuthenticationFilter.Config()).filter(exchange, chain);
            };
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration apiCorsConfiguration = new CorsConfiguration();
        apiCorsConfiguration.setAllowCredentials(true);
        apiCorsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        apiCorsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        apiCorsConfiguration.setAllowedMethods(Collections.singletonList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", apiCorsConfiguration);

        return new CorsWebFilter(source);
    }
}
