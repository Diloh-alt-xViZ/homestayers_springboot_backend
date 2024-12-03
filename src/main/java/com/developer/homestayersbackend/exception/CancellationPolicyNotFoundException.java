package com.developer.homestayersbackend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CancellationPolicyNotFoundException extends RuntimeException {

    public CancellationPolicyNotFoundException(String message) {
        super(message);
    }
}
