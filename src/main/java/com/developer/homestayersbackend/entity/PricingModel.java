package com.developer.homestayersbackend.entity;

public enum PricingModel {
    PER_NIGHT("Per Night"),PER_DAY("Per Day"),PER_WEEK("Per Week"),PER_MONTH("Per Month");

    private String descriptiveName;
    private PricingModel(String descriptiveName) {
        this.descriptiveName = descriptiveName;
    }
    public String getDescriptiveName() {
        return descriptiveName;
    }
}
