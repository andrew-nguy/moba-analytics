package com.nguay097.moba_analytics.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS configuration for allowing cross-origin requests from the frontend.
 *
 * Reads the allowed origin from the CORS_ORIGINS environment variable.
 * In production this should be set to the deployed frontend URL.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowed.origins}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}