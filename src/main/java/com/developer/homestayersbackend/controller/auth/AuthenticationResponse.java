package com.developer.homestayersbackend.controller.auth;

import com.developer.homestayersbackend.entity.User;
import com.developer.homestayersbackend.util.AuthenticationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private Long  userId;
    private String token;
    private String refreshToken;
}
