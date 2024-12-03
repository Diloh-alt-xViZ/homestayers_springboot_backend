package com.developer.homestayersbackend.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(String message) {

        super(message);
    }
}
