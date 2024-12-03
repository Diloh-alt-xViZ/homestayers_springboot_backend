package com.developer.homestayersbackend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AmenityCategoryNotFoundException extends RuntimeException {

    public AmenityCategoryNotFoundException(String message) {
        super(message);
    }
}
