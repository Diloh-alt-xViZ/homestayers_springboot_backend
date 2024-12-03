package com.developer.homestayersbackend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AmenityCategoryExistsException extends RuntimeException {

    public AmenityCategoryExistsException(String message) {
        super(message);
    }
}
