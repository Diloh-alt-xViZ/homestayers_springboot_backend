package com.developer.homestayersbackend.service.api;


import com.developer.homestayersbackend.dto.PhoneNumber;
import com.developer.homestayersbackend.entity.UsersOtp;

public interface UsersOtpService {

    UsersOtp saveOtp(UsersOtp usersOtp);
    UsersOtp getUsersOtp(String otp, PhoneNumber phoneNumber);
}
