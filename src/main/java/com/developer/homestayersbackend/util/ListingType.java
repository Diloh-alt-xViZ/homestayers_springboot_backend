package com.developer.homestayersbackend.util;

import lombok.Getter;
@Getter
public enum ListingType {
    BED_AND_BREAKFAST("Cozy rooms with breakfast included, offering a homey atmosphere and personalized service."
    ,"BnB"),
    LODGE("Rustic accommodation often located in scenic areas, providing a retreat experience close to nature.","Lodge"),
    HOTEL("Full-service accommodations with modern amenities and professional service, ideal for both leisure and business travelers.","Hotel"),
    HOME_STAYERS_EXPERIENCE("A unique cultural experience where guests live with hosts, enjoying local customs and personal interaction.","Homestay"),
    RENTAL("Private homes or apartments available for short or long-term stays, offering comfort and flexibility for guests.","Rental");

    private final String description;
    private final String name;
    ListingType(String description,String name) {
        this.description = description;
        this.name = name;
    }
}

