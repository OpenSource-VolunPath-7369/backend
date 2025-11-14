package com.acme.center.volunpath_backend.iam.application.internal.outboundservices.tokens;

/**
 * TokenService interface
 * Used to generate and validate JWT tokens
 */
public interface TokenService {
    String generateToken(String username);
    String getUsernameFromToken(String token);
    boolean validateToken(String token);
}

