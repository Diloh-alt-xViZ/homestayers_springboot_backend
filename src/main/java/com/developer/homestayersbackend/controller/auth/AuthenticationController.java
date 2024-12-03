package com.developer.homestayersbackend.controller.auth;


import com.developer.homestayersbackend.dto.*;
import com.developer.homestayersbackend.entity.PhoneVerification;
import com.developer.homestayersbackend.entity.User;
import com.developer.homestayersbackend.exception.UserNotFoundException;
import com.developer.homestayersbackend.service.api.PhoneNumberAuthService;
import com.developer.homestayersbackend.service.api.PhoneVerificationService;
import com.developer.homestayersbackend.service.api.UserService;
import com.developer.homestayersbackend.util.AuthenticationStatus;
import com.developer.homestayersbackend.util.PhoneNumberUtils;
import com.developer.homestayersbackend.util.RegistrationStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final PhoneVerificationService phoneVerificationService;
    private final UserService userService;
    private final PhoneNumberAuthService authService;
    private Map<String,String> otpMap = new ConcurrentHashMap<>();
//
//    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
//    @PostMapping("/{userId}/verifyPhoneNumber")
//    public ResponseEntity<String> verifyUserPhone(@PathVariable("userId")Long userId, @RequestParam String phoneNumber) {
//        phoneNumber = "+" + phoneNumber;
//        OtpResponse otp = authService.sendVerificationCode(phoneNumber);
//        System.out.println(otp.getFormattedNumber());
//        System.out.println(otp.getOtp());
//        otpStorage.put(otp.getFormattedNumber(),otp.getOtp());
//        System.out.println(otpStorage.get(otp.getFormattedNumber()));
//        return ResponseEntity.ok("Otp sent successfully");
//    }
//
//    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
//    @PostMapping("/{userId}/verifyOtp")
//    public ResponseEntity<String> verifyOtp(@PathVariable("userId")Long userId,@RequestBody PhoneNumberAuth phoneNumberAuth) {
//        String phoneNumberUtils = PhoneNumberUtils.formatToE164(phoneNumberAuth.getPhoneNumber().getNumber(),phoneNumberAuth.getPhoneNumber().getCountryCode());
//        System.out.println("Otp Storage Key: "+phoneNumberUtils);
//        String storedOtp = otpStorage.get(phoneNumberUtils);
//        otpStorage.keySet().stream().forEach(System.out::println);
//        System.out.println("Stored Otp:" +storedOtp);
//        if(storedOtp != null && storedOtp.equals(phoneNumberAuth.getOtp())) {
//            otpStorage.remove(phoneNumberAuth.getPhoneNumber().getFullNumber());
//            User user = userService.getUserById(userId);
//
//            if(user != null) {
//                user.setPhoneNumber(phoneNumberAuth.getPhoneNumber());
//                userService.updateUser(user);
//            }
//            return ResponseEntity.ok("Phone Number Verified Successfully");
//        }
//
//        else return ResponseEntity.badRequest().body("Otp doesn't match");
//    }

/*
    @PostMapping("/email/register")
    public ResponseEntity<AuthenticationResponse> registerUserWithEmail(@Valid @RequestBody EmailAuthRequest authRequest) {
        AuthenticationResponse authenticationResponse = userService.registerUserByEmail(authRequest.getEmail(), authRequest.getPassword());

        if(authenticationResponse.getAuthenticationStatus().equals(AuthenticationStatus.GRANTED)){
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + authenticationResponse.getToken());
            authenticationResponse.setToken("");
            System.out.println("Headers:::"+headers.toString());
            return ResponseEntity.ok().headers(headers).body(authenticationResponse);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("/email/login")
    public ResponseEntity<AuthenticationResponse> loginUserWithEmail(@Valid @RequestBody EmailAuthRequest authRequest) {
        try{
            AuthenticationResponse authResponse = userService.login(authRequest.getEmail(), authRequest.getPassword());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + authResponse.getToken());
            authResponse.setToken("");
            return ResponseEntity.ok().headers(headers).body(authResponse);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }
**/



    @PostMapping("/phone-number/user-profile-details")
    public ResponseEntity<AuthenticationResponse> setPhoneUserProfileDetails(@RequestBody @Valid PhoneUserProfileDetailsDto dto){

            AuthenticationResponse response = userService.createNewPhoneUserAndAuthenticate(dto);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + response.getToken());
            response.setToken("");
            return ResponseEntity.ok().headers(headers).body(response);
    }

    @PostMapping("/oidc/attemptLogin")
    public ResponseEntity<AuthenticationResponse> oidcLogin(@RequestBody OidcRequest oidcRequest){

        AuthenticationResponse authenticationResponse = userService.createNewUserFromOidc(oidcRequest);
        HttpHeaders headers = new HttpHeaders();
        //
        headers.set("Authorization", "Bearer " + authenticationResponse.getToken());
        authenticationResponse.setToken("");
        return new ResponseEntity<>(authenticationResponse, headers, HttpStatus.OK);
    }
    @PostMapping("/email/user-profile-details")
    public ResponseEntity<AuthenticationResponse> setUserProfileDetails(@RequestBody @Valid UserProfileDetailsDto authenticationRequest) {
        AuthenticationResponse authenticationResponse = userService.createNewUserProfileAndAuthenticate(authenticationRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authenticationResponse.getToken());
        authenticationResponse.setToken("");
        System.out.println("Refresh Token:"+authenticationResponse.getRefreshToken());
        return ResponseEntity.ok().headers(headers).body(authenticationResponse);

    }


    @GetMapping("/checkTokenValidity")
    public ResponseEntity<String> checkTokenValidity(@AuthenticationPrincipal User user) {

        Long id = user.getId();

        return ResponseEntity.ok().body(id.toString());
    }

    @PostMapping("/email/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = userService.login(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authenticationResponse.getToken());
        authenticationResponse.setToken("");
        System.out.println("Refresh Token:"+authenticationResponse.getRefreshToken());
        return ResponseEntity.ok().headers(headers).body(authenticationResponse);

    }

    @PostMapping("/email/login")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody EmailAuthRequest emailAuthRequest){
            try{
                RegistrationStatus status = userService.attemptLogin(emailAuthRequest.getEmail());
                System.out.println("Registration Status:"+status);
                return ResponseEntity.ok().body(LoginResponseDto.builder().registrationStatus(status.name()).build());
            }
            catch (UserNotFoundException exception){

                RegistrationStatus status = userService.registerNewEmail(emailAuthRequest.getEmail());
                return ResponseEntity.ok().body(LoginResponseDto.builder().registrationStatus(status.name()).build());
            }
    }


    @GetMapping("/email/verify")
    public ResponseEntity<String> verifyUserEmail(@RequestParam("token")String token){
        boolean isVerified = userService.verifyUserEmail(token);

        if(isVerified){
            return ResponseEntity.status(HttpStatus.OK).body("");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
    }


    @GetMapping("/hi")
    public ResponseEntity<String> getHi(){

        return ResponseEntity.ok("hi");
    }

    @PostMapping("/phone-number/request-verification")
    public ResponseEntity<String> phoneNumber(@RequestParam String phoneNumber) {
                phoneNumber = "+" + phoneNumber;
            PhoneVerification otp = phoneVerificationService.getPhoneVerification(phoneNumber);
            String response = "";
            if(otp!=null){
                response = "Success";
            }
            else {
                response = "Failed";
            }
            return ResponseEntity.ok(response);
    }

    @PostMapping("/phone-number/verifyPhone")
    public ResponseEntity<AuthenticationResponse> verifyPhone(@RequestBody PhoneNumberAuth phoneNumberAuth) {

        AuthenticationResponse authenticationResponse = phoneVerificationService.verifyPhone(phoneNumberAuth.getPhoneNumber(), phoneNumberAuth.getOtp());

            if(authenticationResponse!=null) {
                userService.verifyUser(authenticationResponse.getUserId());
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + authenticationResponse.getToken());
                authenticationResponse.setToken("");
                return ResponseEntity.ok().headers(headers).body(authenticationResponse);
            }

        else return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/refresh")
    public  ResponseEntity<RefreshTokenDto> getNewToken(@RequestBody RefreshTokenRequest request){

        System.out.println("Refresh Token:"+request.getRefreshToken());
        RefreshTokenDto refreshTokenDto = userService.getNewToken(request);
        if(refreshTokenDto!=null){
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + refreshTokenDto.getToken());
            refreshTokenDto.setToken("");
            return ResponseEntity.ok().headers(headers).body(refreshTokenDto);
        }
        else return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

}
