package com.developer.homestayersbackend.config;

import com.developer.homestayersbackend.repository.UserRepository;
import com.developer.homestayersbackend.service.CustomPhoneUserService;
import com.developer.homestayersbackend.service.api.PhoneVerificationService;
import com.developer.homestayersbackend.service.api.TwilioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class PhoneNumberAuthenticationProvider  implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final TwilioService twilioService;
    private final PhoneVerificationService phoneVerificationService;
    private final UserRepository userRepository;
    private final  CustomPhoneUserService customPhoneUserService;

    public PhoneNumberAuthenticationProvider(CustomPhoneUserService customPhoneUserService,UserDetailsService userDetailsService, TwilioService twilioService, PhoneVerificationService phoneVerificationService, UserRepository userRepository) {
            this.userDetailsService = userDetailsService;
            this.twilioService = twilioService;
        this.phoneVerificationService = phoneVerificationService;
        this.userRepository = userRepository;
        this.customPhoneUserService = customPhoneUserService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phoneNumber = (String) authentication.getPrincipal();
        String verificationCode = (String) authentication.getCredentials();
        if(phoneVerificationService.verifyPhone(phoneNumber,verificationCode)!=null){
            throw new BadCredentialsException("Invalid verification code");
        }
        UserDetails userDetails;
        try{
            userDetails = customPhoneUserService.loadUserByUsername(phoneNumber);
            System.out.println("Username:"+userDetails.getUsername());
        }
        catch(UsernameNotFoundException e){
                throw new BadCredentialsException("User not found");
        }
        return new PhoneNumberAuthenticationToken(phoneNumber, verificationCode, userDetails.getAuthorities());
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return  PhoneNumberAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
