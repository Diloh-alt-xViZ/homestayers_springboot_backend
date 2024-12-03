package com.developer.homestayersbackend.service.api;

import com.developer.homestayersbackend.dto.OtpResponse;

public interface PhoneNumberAuthService {
    OtpResponse sendVerificationCode(String phoneNumber);
    boolean verifyCodeAndAuthenticate(boolean valid,String phoneNumber, String code);

}
