package com.acme.center.volunpath_backend.iam.infrastructure.authorization.sfs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Web Security Configuration
 * Configures Spring Security for the application
 * TODO: Implement JWT authentication filter
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Allow OPTIONS requests for CORS preflight
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/v1/authentication/**").permitAll()
                        .requestMatchers("/api/v1/users/**").permitAll() // Permitir acceso a usuarios para desarrollo
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**").permitAll()
                        .requestMatchers("/error", "/favicon.ico").permitAll()
                        .anyRequest().permitAll() // Temporalmente permitir todo para desarrollo
                )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
        
        return http.build();
    }
}

