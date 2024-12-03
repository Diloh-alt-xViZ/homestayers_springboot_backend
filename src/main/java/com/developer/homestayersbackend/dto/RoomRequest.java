package com.developer.homestayersbackend.dto;

import com.developer.homestayersbackend.entity.Amenity;
import com.developer.homestayersbackend.entity.Pricing;
import com.developer.homestayersbackend.entity.Services;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {

    private Long propertyId;
    private String roomTitle;
    private String roomType;
    private List<PhotoDto> photoList;
    private List<Long> amenities;
    private List<Long> servicesList;
    private List<Long> pricingList;
    
}
