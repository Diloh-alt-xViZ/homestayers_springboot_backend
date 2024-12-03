package com.developer.homestayersbackend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AmenityExistsException extends RuntimeException {

    public AmenityExistsException(String message) {
        super(message);
    }
}
