package com.developer.homestayersbackend.service.impl;

import com.developer.homestayersbackend.dto.PhoneNumber;
import com.developer.homestayersbackend.entity.UsersOtp;
import com.developer.homestayersbackend.repository.UsersOtpRepository;
import com.developer.homestayersbackend.service.api.UsersOtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsersOtpServiceImpl implements UsersOtpService {
    private final UsersOtpRepository usersOtpRepository;

    @Override
    public UsersOtp getUsersOtp(String otp, PhoneNumber phoneNumber) {

        Optional<UsersOtp> usersOtp = usersOtpRepository.findUsersByOtpAndPhoneNumber(otp, phoneNumber);

        return usersOtp.orElse(null);

    }

    @Override
    public UsersOtp saveOtp(UsersOtp usersOtp) {
        return usersOtpRepository.save(usersOtp);
    }
}
