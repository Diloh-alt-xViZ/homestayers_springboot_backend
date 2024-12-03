package com.developer.homestayersbackend.config;

import com.developer.homestayersbackend.dto.PhoneNumber;
import com.developer.homestayersbackend.entity.User;
import com.developer.homestayersbackend.repository.UserRepository;
import com.developer.homestayersbackend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;


@RequiredArgsConstructor
@Component
public class PhoneAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private String homeUrl = "http://localhost:8080/";


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        System.out.println("Successful Authentication");

        if(response.isCommitted()) {
            return;
        }
        String token;
        String  phoneNumber = (String) authentication.getPrincipal();
        System.out.println("Decoded Phone Number: " + phoneNumber);

        Optional<User> dbUser = userRepository.findByUsername(String.valueOf(phoneNumber));
        if(dbUser.isPresent()) {
            token = jwtService.generateToken(dbUser.get());
            response.addHeader("Authorization", "Bearer " + token);
            String redirectUrl = UriComponentsBuilder.fromUriString(homeUrl).build().toUriString();
            System.out.println(response.getHeader("Authorization"));
            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        }

        else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            getRedirectStrategy().sendRedirect(request, response, "http://localhost:8080/error");
        }

    }
}
