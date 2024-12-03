package com.developer.homestayersbackend.service.api;

public interface OtpService {
    String generateOTP(String phoneNumber);
    boolean verifyOTP(String phoneNumber, String otp);
}
