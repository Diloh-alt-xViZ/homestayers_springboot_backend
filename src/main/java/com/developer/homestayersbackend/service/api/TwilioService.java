package com.developer.homestayersbackend.service.api;

import com.developer.homestayersbackend.dto.OtpResponse;
import com.developer.homestayersbackend.dto.PhoneNumber;

public interface TwilioService {
    OtpResponse sendVerificationCode(String phoneNumber);
    void sendBookingNotification(PhoneNumber phoneNumber, String message, PhoneNumber phoneNumber1);
    void sendBookingDenialNotification(PhoneNumber guestPhoneNumber, String message);
    boolean verifyCode(String phoneNumber);

    void sendBookingApprovalNotification(PhoneNumber phoneNumber, String approvalMessage);
}
