package com.developer.homestayersbackend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CancellationPolicyAlreadyExistsException extends RuntimeException {

    public CancellationPolicyAlreadyExistsException(String message) {
        super(message);
    }
}
