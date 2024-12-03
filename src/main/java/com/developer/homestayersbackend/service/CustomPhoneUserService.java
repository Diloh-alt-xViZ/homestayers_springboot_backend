package com.developer.homestayersbackend.service;

import com.developer.homestayersbackend.config.PhoneNumberAuthenticationToken;
import com.developer.homestayersbackend.dto.PhoneNumber;
import com.developer.homestayersbackend.entity.Role;
import com.developer.homestayersbackend.entity.User;
import com.developer.homestayersbackend.entity.UserProfile;
import com.developer.homestayersbackend.repository.UserProfileRepository;
import com.developer.homestayersbackend.repository.UserRepository;
import com.developer.homestayersbackend.util.PhoneNumberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomPhoneUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;


    private PhoneNumber parsePhoneNumber(String phoneNumber) {


        if(!phoneNumber.startsWith("+") || phoneNumber.length()<10){
            throw new IllegalArgumentException("Invalid phone number");
        }

        int countryCodeEndIndex = phoneNumber.length()-9;
        String countryCode = phoneNumber.substring(1,countryCodeEndIndex);
        String number = phoneNumber.substring(countryCodeEndIndex);

        return new PhoneNumber(countryCode, number);
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        PhoneNumber phone = parsePhoneNumber(phoneNumber);
        System.out.println(phone);
        UserDetails user  = findUserByPhone(phone);
        if(user == null){
            return createUser(phone);
        }

        else {
            PhoneNumberAuthenticationToken authenticationToken = new PhoneNumberAuthenticationToken(user.getUsername(),null, user.getAuthorities());
            System.out.println("Principal: " + authenticationToken.getPrincipal());
            Authentication authentication = new PhoneNumberAuthenticationToken(user.getUsername(),null, user.getAuthorities());
            System.out.println("Principal: "+authentication.getPrincipal());
            System.out.println(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println(SecurityContextHolder.getContext().getAuthentication());
        }
        return user;
    }

    private UserDetails findUserByPhone(PhoneNumber phone) {

        Optional<User> dbUser = userRepository.findByUsername(PhoneNumberUtils.formatToE164(phone.getNumber(),phone.getCountryCode()));
        System.out.println("DbUser: " + dbUser);
        return dbUser.orElse(null);
    }

    private UserDetails createUser(PhoneNumber phoneNumber){
        User user = new User();

        user.setPhoneNumber(phoneNumber);
        user.setUsername(PhoneNumberUtils.formatToE164(phoneNumber.getNumber(), phoneNumber.getCountryCode()));
        user.setDateRegistered(new Date(System.currentTimeMillis()));
        user.setRole(Role.USER);
        User dbUser = userRepository.save(user);
        Authentication authentication = new PhoneNumberAuthenticationToken(dbUser.getUsername(),null, user.getAuthorities());
        System.out.println("Principal: "+authentication.getPrincipal());
        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return dbUser;
    }
}
