package com.developer.homestayersbackend.service.impl;

import com.developer.homestayersbackend.service.api.OtpService;
import org.springframework.stereotype.Service;


@Service
public class OtpServiceImpl implements OtpService {

    @Override
    public String generateOTP(String phoneNumber) {
        return "";
    }

    @Override
    public boolean verifyOTP(String phoneNumber, String otp) {
        return false;
    }
}
