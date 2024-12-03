package com.developer.homestayersbackend.dto;



import com.developer.homestayersbackend.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class PropertyResponseDto {
    private String stayType;
    private String rentalType;
    private String approvalStatus;
    private Date startDate;
    private String listingType;
    private Long id;
    private PriceDto price;
    private List<RuleDto> rules;
    private String neighbourhoodDetails;
    private String gettingAround;
    private String title;
    private Location location;
    private List<PhotoDto> photos;
    private String description;
    private List<RoomResponseDto> rooms;
    private List<Amenity> amenities;
    private List<Services> services;
    private HostDto hostDto;
    private List<ReviewDto> reviews;
}
