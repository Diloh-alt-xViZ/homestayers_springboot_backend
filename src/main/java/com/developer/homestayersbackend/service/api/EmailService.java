package com.developer.homestayersbackend.service.api;

public interface EmailService {
    void sendVerificationEmail(String email, String emailToken);
}
