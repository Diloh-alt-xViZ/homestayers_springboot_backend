package com.developer.homestayersbackend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PropertyTypeExistsException extends RuntimeException {
    public PropertyTypeExistsException(String message) {
        super(message);
    }

}
