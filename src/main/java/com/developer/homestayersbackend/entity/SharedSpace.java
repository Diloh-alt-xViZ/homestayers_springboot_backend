package com.developer.homestayersbackend.entity;


import lombok.Getter;

@Getter
public enum SharedSpace   {
    BEDROOM("Bedroom","Sleeping Area"),
    BATHROOM("Bathroom", "A private or shared bathroom attached to the room."),
    LIVING_ROOM("Living Room", "A separate seating area within the room or suite."),
    KITCHEN("Kitchen", "Cooking area."),
    CORRIDOR("Corridor", "Corridor area."),
    GARAGE("Garage", "Garage area.");
    private final String name;
    private final String description;

    SharedSpace(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
