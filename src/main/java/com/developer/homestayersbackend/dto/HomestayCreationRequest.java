package com.developer.homestayersbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomestayCreationRequest {
    private Double latitude;
    private Double longitude;
    private String country;
    private List<SharedSpaceDto> sharedSpaces;
    private PriceDto price;
    private String stayType;
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
