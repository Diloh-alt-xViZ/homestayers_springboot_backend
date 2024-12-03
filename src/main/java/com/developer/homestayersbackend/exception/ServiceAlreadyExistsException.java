package com.developer.homestayersbackend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ServiceAlreadyExistsException extends RuntimeException {

    public ServiceAlreadyExistsException(String message) {
        super(message);
    }
}
