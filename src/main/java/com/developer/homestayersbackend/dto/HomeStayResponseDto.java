package com.developer.homestayersbackend.dto;


import com.developer.homestayersbackend.entity.Amenity;
import com.developer.homestayersbackend.entity.Location;
import com.developer.homestayersbackend.entity.Services;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeStayResponseDto {

    private List<String> sharedSpaces;
    private List<RuleDto> rules;
    private PriceDto price;
    private Long id;
    private String title;
    private String description;
    private List<PhotoDto> photos;
    private List<Amenity> amenities;
    private List<Services> services;
    private String stayType;
    private Location location;
    private String neighbourhoodDetails;
    private String gettingAround;
    private HostDto hostDto;
    private List<ReviewDto> reviews;

}
