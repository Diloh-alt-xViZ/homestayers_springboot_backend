package com.developer.homestayersbackend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AmenityNotFoundException extends RuntimeException {


    public AmenityNotFoundException(String message) {
        super(message);
    }
}
