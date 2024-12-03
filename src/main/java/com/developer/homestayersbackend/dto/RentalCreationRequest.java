package com.developer.homestayersbackend.dto;


import com.developer.homestayersbackend.entity.Rental;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalCreationRequest {
    private String country;
    private Double latitude;
    private Double longitude;
    private List<SharedSpaceDto> sharedSpaces;
    private List<RoomTypeDto> rooms;
    private int roomCount;
    private PriceDto price;
    private String rentalType;
    private int guestCount;
    private String neighbourhoodDetails;
    private String gettingAround;
    private String listingType;
    private String title;
    private List<PhotoDto> photos;
    private List<HouseRuleRequest> policies;
    private String description;
    private String address;
    private String street;
    private String city;
    private String province;
    private List<String> amenities;
    private List<String> services;
    private Long hostId;

}
