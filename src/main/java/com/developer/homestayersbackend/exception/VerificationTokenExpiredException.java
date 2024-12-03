package com.developer.homestayersbackend.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VerificationTokenExpiredException extends RuntimeException{

    public VerificationTokenExpiredException(String message) {
        super(message);
    }
}
