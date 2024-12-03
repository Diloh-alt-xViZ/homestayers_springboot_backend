package com.developer.homestayersbackend.config;

import com.developer.homestayersbackend.dto.AuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class PhoneNumberAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public PhoneNumberAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authManager, ObjectMapper objectMapper) {

        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authManager);
        this.objectMapper = objectMapper;

    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        AuthRequest authRequest  = objectMapper.readValue(request.getInputStream(), AuthRequest.class);
        System.out.println("Auth Request!!!!!!!!"+ authRequest);
        System.exit(0);
        PhoneNumberAuthenticationToken authenticationToken = new PhoneNumberAuthenticationToken(
                authRequest.getPhoneNumber(), authRequest.getVerificationCode()
        );
        return getAuthenticationManager().authenticate(authenticationToken);
    }
}
