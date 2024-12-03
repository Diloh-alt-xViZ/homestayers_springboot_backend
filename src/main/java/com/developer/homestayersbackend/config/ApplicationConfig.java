package com.developer.homestayersbackend.config;


import com.developer.homestayersbackend.dto.UserProfileDto;
import com.developer.homestayersbackend.entity.UserProfile;
import com.developer.homestayersbackend.repository.UserRepository;
import com.developer.homestayersbackend.service.CustomPhoneUserService;
import com.developer.homestayersbackend.service.api.PhoneVerificationService;
import com.developer.homestayersbackend.service.api.TwilioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;
    private final TwilioService twilioService;
    private final PhoneVerificationService phoneVerificationService;
    private final CustomPhoneUserService customPhoneUserService;
    @Bean
    public UserDetailsService userDetailsService(){
        return username ->userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }



    @Bean(name = "profileModelMapper")
    public ModelMapper profileModelMapper(){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        PropertyMap<UserProfileDto, UserProfile> propertyMap = new PropertyMap<>(){
            @Override
            protected void configure() {
                        map().setFirstName(source.getFirstName());
                        map().setLastName(source.getLastName());
                        map().setBio(source.getBio());
                        //map().setAddress(source.getAddressDto());
                        map().setWork(source.getWork());
                        map().setIntro(source.getIntro());
                        map().setDateOfBirth(source.getDob());
                        map().setSchool(source.getSchool());
                        skip().setUser(map().getUser());
                        skip().setPhoto(map().getPhoto());
            }
        };

        modelMapper.addMappings(propertyMap);
        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return new ProviderManager(Arrays.asList(phoneNumberAuthenticationProvider(),authenticationProvider()));
    }

    @Bean
    public PhoneNumberAuthenticationProvider phoneNumberAuthenticationProvider() throws Exception {

            return new PhoneNumberAuthenticationProvider(customPhoneUserService,userDetailsService(),twilioService,phoneVerificationService,userRepository);

    }

    @Bean
    public ObjectMapper objectMapper(){

        return  new ObjectMapper();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}
