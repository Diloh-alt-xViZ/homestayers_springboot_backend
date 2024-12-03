package com.developer.homestayersbackend.entity;


import lombok.Getter;

@Getter
public enum RoomType {

    GARAGE("Standard","Garage",""),
    BEDROOM("Standard","Bedroom","")
    ,KITCHEN("Standard","Kitchen","")
    ,BATHROOM("Standard","Bathroom","")
    ,LIVING_ROOM("Standard","Living Room","")
    ,DINING("Standard","Dining","")
    ,SINGLE_ROOM("Common","Single Room","A basic room with one bed, usually for one guest.")
    ,DOUBLE_ROOM("Common","Double Room"," A standard room with one double bed for two guests.")
    ,SUITE("Common","Suite","A larger room with separate living and sleeping areas, offering more space and amenities.")
    ,FAMILY_ROOM("Common","Family Room","A room designed for families, with multiple beds or extra space for children.")
    ,ACCESSIBLE_ROOM("Hotel","Accessible Room"," A room equipped for guests with disabilities, with special features like wider doors and accessible bathrooms."),
    STANDARD_LODGE_ROOM("Lodge","Standard Lodge Room", "A basic room with essential amenities for budget-conscious travelers."),
    DELUXE_LODGE_ROOM("Lodge","Deluxe Lodge Room", "An upgraded room with more space, better views, and premium amenities."),
    PRIVATE_CABIN("Lodge","Private Cabin", "A standalone cabin offering privacy and a rustic, natural experience."),
    FAMILY_LODGE_ROOM("Lodge","Family Lodge Room", "A spacious room designed for families, featuring multiple beds and more space."),
    LUXURY_SUITE("Lodge","Luxury Suite","A luxurious room with separate living areas, premium amenities, and scenic views.")
    ,STANDARD_ROOM("BnB","Standard Room", "A basic room with essential amenities."),
    DELUXE_ROOM("BnB","Deluxe Room", "A larger room with added comforts."),
    TWIN_ROOM("BnB", "Twin Room","A room with two single beds, ideal for two guests."),
    STUDIO_ROOM("BnB","Studio Room", "A room with a small kitchen or kitchenette."),
    LUXURY_ROOM("BnB","Luxury Room", "A high-end room with premium amenities."),
    THEMED_ROOM("BnB","Themed Room", "A uniquely decorated room based on a specific theme.");


    private final String name;
    private final String description;
    private final String category;



    RoomType(String category,String name,String description) {
        this.category = category;
        this.name = name;
        this.description = description;
    }
}
