package com.developer.homestayersbackend.entity;

import lombok.Getter;

@Getter
public enum StayType {
    URBAN("Urban","Urban setting"),RURAL("Rural","Rural Setting");

    private final String description;
    private final String name;

    private StayType(String name, String description) {
        this.description = description;
        this.name = name;
    }
}
