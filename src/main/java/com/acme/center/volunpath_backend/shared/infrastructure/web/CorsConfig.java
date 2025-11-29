package com.acme.center.volunpath_backend.shared.infrastructure.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CORS Configuration
 * Allows cross-origin requests from the Angular frontend
 */
@Configuration
public class CorsConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorsConfig.class);

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${cors.allowed-methods}")
    private String allowedMethods;

    @Value("${cors.allowed-headers:*}")
    private String allowedHeaders;

    @Value("${cors.allow-credentials:true}")
    private boolean allowCredentials;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Parse and trim origins (remove any whitespace)
        List<String> origins = Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(origin -> !origin.isEmpty())
                .collect(Collectors.toList());
        
        LOGGER.info("CORS Configuration - Allowed Origins: {}", origins);
        config.setAllowedOrigins(origins);
        
        // Parse and trim methods
        List<String> methods = Arrays.stream(allowedMethods.split(","))
                .map(String::trim)
                .filter(method -> !method.isEmpty())
                .collect(Collectors.toList());
        
        LOGGER.info("CORS Configuration - Allowed Methods: {}", methods);
        config.setAllowedMethods(methods);
        
        // Parse and trim headers
        List<String> headers = Arrays.stream(allowedHeaders.split(","))
                .map(String::trim)
                .filter(header -> !header.isEmpty())
                .collect(Collectors.toList());
        
        LOGGER.info("CORS Configuration - Allowed Headers: {}", headers);
        config.setAllowedHeaders(headers);
        
        // Allow credentials
        config.setAllowCredentials(allowCredentials);
        LOGGER.info("CORS Configuration - Allow Credentials: {}", allowCredentials);
        
        // Expose headers
        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        
        // Apply CORS configuration to all paths
        source.registerCorsConfiguration("/**", config);
        
        LOGGER.info("CORS Filter configured successfully for paths: /**");
        
        return new CorsFilter(source);
    }
}

