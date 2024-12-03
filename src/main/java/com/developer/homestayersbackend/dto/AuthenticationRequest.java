package com.developer.homestayersbackend.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {

    @NotNull(message = "Email cannot be empty")
    @Email(message = "Enter a valid email")
    private String email;
    @NotNull(message = "Password cannot be empty")
    @Size(min = 8,message = "Password should have at least 8 characters")
    private String password;
}
