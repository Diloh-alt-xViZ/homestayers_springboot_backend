package com.developer.homestayersbackend.entity;

import lombok.Getter;

@Getter
public enum BookingStatus {
    ACTIVE("Active"),PENDING("Pending"),CANCELLED("Cancelled"),APPROVED("Approved"),REJECTED("Rejected");

    private final String name;
    private BookingStatus(String name) {
        this.name = name;
    }

}
