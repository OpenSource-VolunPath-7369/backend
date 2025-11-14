package com.acme.center.volunpath_backend.iam.application.internal.outboundservices.hashing;

/**
 * HashingService interface
 * Used to encode and match passwords
 */
public interface HashingService {
    String encode(CharSequence rawPassword);
    boolean matches(CharSequence rawPassword, String encodedPassword);
}

