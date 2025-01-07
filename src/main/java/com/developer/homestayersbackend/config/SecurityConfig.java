package com.developer.homestayersbackend.config;

import com.developer.homestayersbackend.service.api.TwilioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final OidcUserService oidcUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final TwilioService twilioService;
    private final ObjectMapper objectMapper;
    private final AuthenticationProvider phoneNumberAuthenticationProvider;
    private final PhoneAuthenticationSuccessHandler customPhoneAuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/api/v1/auth/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(new JwtAuthenticationFilter(jwtService,userDetailsService),UsernamePasswordAuthenticationFilter.class)
//                .oauth2Login()
//                .defaultSuccessUrl("/api/v1/property/props",true)
//                .successHandler(authenticationSuccessHandler)
//                .userInfoEndpoint()
//                .userService(auth2UserService);
        http
                .cors(Customizer.withDefaults())
                .csrf()
                .disable()
               // .authenticationManager(authenticationManager)
                //.authenticationProvider(phoneNumberAuthenticationProvider)
                .authorizeRequests()
                .requestMatchers("/api/v1/auth/email/login",
                        "/api/v1/amenities/addAmenities"
                        ,"/api/v1/amenities/category/addCategories"
                        ,"api/v1/auth/phone-number/attemptLogin"
                        ,"/api/v1/property/homestay/getTypes"
                        ,"/api/v1/property/rooms/{roomId}/delete"
                        ,"/auth/email/authenticate"
                        ,"/api/v1/auth/phone-number/login"
                        ,"/api/v1/property/sharedSpaces"
                        ,"/api/v1/property/rooms/deleteAll"
                        ,"/api/v1/property/attachmentTypes"
                        ,"/api/v1/services"
                        ,"/api/v1/services/categories"
                        ,"/api/v1/auth/refresh"
                        ,"/api/v1/property/create-amenity"
                        ,"/api/v1/property/amenity-category"
                        ,"/","/login/phone","/error","/api/v1/auth/**","/api/v1/auth/email/**","/api/v1/auth/phone-number/request-verification","/api/v1/property/listing-types").permitAll()
                .anyRequest()
                .authenticated()
                ;
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



}
