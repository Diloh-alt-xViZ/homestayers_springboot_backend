package com.developer.homestayersbackend.entity;

import lombok.Getter;

@Getter
public enum AttachmentType {
    BATHROOM("Bathroom", "A private or shared bathroom attached to the room."),
    BALCONY("Balcony", "An outdoor space accessible from the room."),
    KITCHENETTE("Kitchenette", "A small kitchen area with basic appliances."),
    LIVING_ROOM("Living Room", "A separate seating area within the room or suite."),
    CLOSET("Closet", "A built-in wardrobe or storage space."),
    OFFICE_SPACE("Office Space", "An area designated for work or study, with a desk and chair."),
    MINI_BAR("Mini Bar", "A small refrigerator stocked with drinks and snacks."),
    PATIO("Patio", "An outdoor space connected to the room, often furnished."),
    PRIVATE_ENTRANCE("Private Entrance", "A separate entrance to the room for added privacy."),
    SITTING_AREA("Sitting Area", "A small seating area within the room."),
    JACUZZI("Jacuzzi", "A hot tub or whirlpool bath."),
    STORAGE_ROOM("Storage Room", "A space for additional storage needs."),
    ADDITIONAL_BEDROOM("Additional Bedroom", "An extra bedroom attached to the main room or suite."),
    FIREPLACE("Fireplace", "An indoor or outdoor fireplace for warmth and ambiance."),
    GAME_ROOM("Game Room", "A space with games or entertainment options."),
    SOUNDPROOFING("Soundproofing", "Features that reduce noise from outside or between rooms.");
    private final String name;
    private final String description;

    private AttachmentType(String name,String description){
        this.name=name;
        this.description=description;
    }
}
