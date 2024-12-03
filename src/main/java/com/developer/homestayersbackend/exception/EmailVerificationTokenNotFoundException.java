package com.developer.homestayersbackend.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailVerificationTokenNotFoundException extends  RuntimeException {

    public EmailVerificationTokenNotFoundException(String message) {
        super(message);
    }
}
