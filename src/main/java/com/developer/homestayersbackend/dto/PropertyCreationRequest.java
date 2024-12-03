package com.developer.homestayersbackend.dto;

import com.developer.homestayersbackend.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyCreationRequest {
    private Double longitude;
    private Double latitude;
    private String country;
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
    private List<RoomDto> rooms;
    private String street;
    private String city;
    private String province;
    private List<String> amenities;
    private List<String> services;
    private Long hostId;
    @JsonIgnore
    private String availableDate;
}
