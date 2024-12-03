package com.developer.homestayersbackend.util;


import lombok.Getter;

@Getter
public enum RentalType {
    //RURAL("A serene getaway surrounded by natural beauty, offering guests a peaceful retreat from the urban hustle. These properties are perfect for nature enthusiasts and those seeking a quiet escape."),
    //APARTMENT("A self-contained residential unit in a building."),
    FULL_HOUSE("A standalone house that includes all amenities and rooms.","Full House"),
    //CONDO("A privately owned residential unit within a larger building or community."),
    ROOM("A single room available for rent, often within a shared space.","Room"),
    COTTAGE("A small, typically cozy house in a rural or semi-rural area.","Cottage"),
    FLAT("A unit similar to an apartment.","Apartment"),
    //OFFICE("A commercial space used for business purposes."),
    STUDENT("Accommodation designed for students, often near a university or college.","Student");
    private final String description;
    private final String name;
    RentalType(String description,String name)
    {   this.name = name;
        this.description = description;
    }
}

