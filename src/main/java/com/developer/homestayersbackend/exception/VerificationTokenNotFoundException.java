package com.developer.homestayersbackend.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VerificationTokenNotFoundException extends RuntimeException{

    public VerificationTokenNotFoundException(String message) {
        super(message);
    }
}
