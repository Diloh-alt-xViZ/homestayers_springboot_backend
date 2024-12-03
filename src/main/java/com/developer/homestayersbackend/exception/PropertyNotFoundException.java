package com.developer.homestayersbackend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PropertyNotFoundException extends RuntimeException {

    public PropertyNotFoundException(String message) {
        super(message);
    }

}
