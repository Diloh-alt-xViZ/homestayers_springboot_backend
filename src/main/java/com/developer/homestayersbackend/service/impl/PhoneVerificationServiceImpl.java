package com.developer.homestayersbackend.service.impl;

import com.developer.homestayersbackend.controller.auth.AuthenticationResponse;
import com.developer.homestayersbackend.dto.OtpResponse;
import com.developer.homestayersbackend.entity.PhoneVerification;
import com.developer.homestayersbackend.entity.User;
import com.developer.homestayersbackend.exception.VerificationTokenExpiredException;
import com.developer.homestayersbackend.exception.VerificationTokenNotFoundException;
import com.developer.homestayersbackend.repository.PhoneVerificationRepository;
import com.developer.homestayersbackend.service.CustomPhoneUserService;
import com.developer.homestayersbackend.service.JwtService;
import com.developer.homestayersbackend.service.api.PhoneVerificationService;
import com.developer.homestayersbackend.service.api.TwilioService;
import com.developer.homestayersbackend.util.PhoneNumberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.developer.homestayersbackend.service.impl.UserServiceImpl.getAuthenticationResponse;

@RequiredArgsConstructor
@Service
public class PhoneVerificationServiceImpl implements PhoneVerificationService {

    private final PhoneVerificationRepository phoneVerificationRepository;
    private final TwilioService twilioService;
    private final CustomPhoneUserService phoneUserService;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse verifyPhone(String phone, String verificationCode) {
        System.out.println("verificationCode: " + verificationCode);
        Optional<PhoneVerification> phoneVerification = phoneVerificationRepository.findPhoneVerificationByPhoneNumberAndVerificationCode(PhoneNumberUtils.formatToE164(phone,""), verificationCode);
        User userDetails;
        if(phoneVerification.isEmpty()){
            throw new VerificationTokenNotFoundException();
        }

        if (phoneVerification.isPresent() && validVerification(phoneVerification.get())) {
            System.out.println("Phone verification passed");
            userDetails = (User) phoneUserService.loadUserByUsername(phoneVerification.get().getPhoneNumber());
            System.out.println("User Details: " + userDetails);
            return getAuthenticationResponse(userDetails, jwtService);

        }
        if(phoneVerification.isPresent() && !validVerification(phoneVerification.get())) {
            throw new VerificationTokenExpiredException();
        }
        return null;
    }

    private boolean validVerification(PhoneVerification phoneVerification) {
        return phoneVerification.getExpirationDate().isAfter(LocalDateTime.now());


    }

    private PhoneVerification checkVerification(String phone){
        System.out.println("Phone:"+phone);
        return phoneVerificationRepository.findByPhoneNumber(PhoneNumberUtils.formatToE164(phone,"+263"));
    }

    @Override
    public PhoneVerification getPhoneVerification(String phone) {
        OtpResponse otpResponse = twilioService.sendVerificationCode(phone);
        System.out.println(otpResponse.getOtp());
        PhoneVerification phoneVerification = new PhoneVerification();
        System.out.println(phone);
        var verificationCodeForNumberExists = checkVerification(phone);
        System.out.println("verificationCodeForNumberExists: "+verificationCodeForNumberExists);
        if(verificationCodeForNumberExists!=null){
            System.out.println("Deleting verification code for phone number: " + phone);
            phoneVerificationRepository.delete(verificationCodeForNumberExists);
        }
        phoneVerification.setPhoneNumber(otpResponse.getFormattedNumber());
        phoneVerification.setExpirationDate(LocalDateTime.now().plusMinutes(5L));
        phoneVerification.setVerificationCode(otpResponse.getOtp());
        return phoneVerificationRepository.save(phoneVerification);
    }
}
