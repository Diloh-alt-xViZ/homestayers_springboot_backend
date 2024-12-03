package com.developer.homestayersbackend.service.impl;

import com.developer.homestayersbackend.config.PhoneNumberAuthenticationToken;
import com.developer.homestayersbackend.dto.OtpResponse;
import com.developer.homestayersbackend.service.api.PhoneNumberAuthService;
import com.developer.homestayersbackend.service.api.TwilioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhoneNumberAuthServiceImpl implements PhoneNumberAuthService {

    private final TwilioService twilioService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Override
    public OtpResponse sendVerificationCode(String phoneNumber) {
        return    twilioService.sendVerificationCode(phoneNumber);

    }


    @Override
    public boolean verifyCodeAndAuthenticate(boolean valid,String phoneNumber, String code) {

        if(!valid){
            throw new BadCredentialsException("Invalid verification code");
        }

        UserDetails userDetails;

        try{
            userDetails = userDetailsService.loadUserByUsername(phoneNumber);

        }
        catch (UsernameNotFoundException e){
            throw new BadCredentialsException("Invalid verification code");
        }
        Authentication authentication = new PhoneNumberAuthenticationToken(userDetails.getUsername(),null, userDetails.getAuthorities());
        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }
}
