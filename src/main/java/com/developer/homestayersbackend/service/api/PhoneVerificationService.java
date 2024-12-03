package com.developer.homestayersbackend.service.api;

import com.developer.homestayersbackend.controller.auth.AuthenticationResponse;
import com.developer.homestayersbackend.entity.PhoneVerification;

public interface PhoneVerificationService {
    public AuthenticationResponse verifyPhone(String phone, String verificationCode);
    public PhoneVerification getPhoneVerification(String phone);
}
