package com.developer.homestayersbackend.util;

public enum ApprovalStatus {
    PENDING("Pending"), APPROVED("Approved"), REJECTED("Rejected");

    private final String name;
    private ApprovalStatus(String name) {
        this.name = name;
    }
}
