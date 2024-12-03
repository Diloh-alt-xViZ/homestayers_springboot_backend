package com.developer.homestayersbackend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class HostAlreadyExistsException extends RuntimeException {

    public HostAlreadyExistsException(String message) {
        super(message);
    }
}
