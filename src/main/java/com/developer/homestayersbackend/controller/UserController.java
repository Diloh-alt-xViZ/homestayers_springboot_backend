package com.developer.homestayersbackend.controller;

import com.developer.homestayersbackend.dto.*;
import com.developer.homestayersbackend.entity.User;
import com.developer.homestayersbackend.entity.UserProfile;
import com.developer.homestayersbackend.repository.UserRepository;
import com.developer.homestayersbackend.service.api.PhoneNumberAuthService;
import com.developer.homestayersbackend.service.api.UserProfileService;
import com.developer.homestayersbackend.service.api.UserService;
import com.developer.homestayersbackend.util.PhoneNumberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final String PROFILES_PREFIX = "/{userId}/profile";
    private final String HOSTS_PREFIX = "/hosts";
    private final UserService userService;
    private final UserProfileService userProfileService;
    private final PhoneNumberAuthService authService;
    private Map<String,String> otpStorage = new ConcurrentHashMap<>();
    private UserRepository userRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/checkUserDetails")
    public ResponseEntity<String> checkUserDetails(@AuthenticationPrincipal User user){

        boolean userPresent = userService.checkUserDetails(user.getId());
        if(userPresent){
            return ResponseEntity.ok("Present");
        }
        else {
            return ResponseEntity.ok("Absent");
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("userId")Long userId){
        System.out.println("User Id:"+userId);
        return ResponseEntity.ok().body(userService.getUserDetailsById(userId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/userDetails")
    public  ResponseEntity<UserDto> getUserDetailsFromToken(@AuthenticationPrincipal User  authenticatedUser){
        Long userId = authenticatedUser.getId();
        return ResponseEntity.ok().body(userService.getUserDetailsById(userId));
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping("/{userId}/verifyPhoneNumber")
    public ResponseEntity<String> verifyUserPhone(@PathVariable("userId")Long userId, @RequestParam String phoneNumber) {
        phoneNumber = "+" + phoneNumber;
        OtpResponse otp = authService.sendVerificationCode(phoneNumber);
        System.out.println(otp.getFormattedNumber());
        System.out.println(otp.getOtp());
        otpStorage.put(otp.getFormattedNumber(),otp.getOtp());
        System.out.println(otpStorage.get(otp.getFormattedNumber()));
        return ResponseEntity.ok("Otp sent successfully");
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(PROFILES_PREFIX)
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable("userId")Long userId){

        UserProfileDto userProfileDto = userProfileService.getProfile(userId);
        System.out.println("User Profile Dto:"+userProfileDto);
        return ResponseEntity.ok().body(userProfileDto);

    }

    @PutMapping(PROFILES_PREFIX)
    public ResponseEntity<UserProfile> editUserProfile(@PathVariable Long userId,@RequestBody UserProfileDto userProfileDto) {
        return ResponseEntity.ok(userService.editProfile(userId,userProfileDto));
    }


}
