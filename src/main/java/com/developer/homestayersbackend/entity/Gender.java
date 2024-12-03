package com.developer.homestayersbackend.entity;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Male"), FEMALE("Female"),OTHER("Other");

    private final String name;
    private Gender(String name) {
        this.name = name;
    }

}
