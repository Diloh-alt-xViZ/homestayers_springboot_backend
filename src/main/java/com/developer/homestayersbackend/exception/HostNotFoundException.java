package com.developer.homestayersbackend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class HostNotFoundException extends RuntimeException {

    public HostNotFoundException(String message) {
        super(message);
    }
}
