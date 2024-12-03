package com.developer.homestayersbackend.dto;


import com.developer.homestayersbackend.entity.Amenity;
import com.developer.homestayersbackend.entity.AttachmentType;
import com.developer.homestayersbackend.entity.Room;
import com.developer.homestayersbackend.entity.Services;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomResponseDto {

    private Long listingId;
    private Long id;
    private String title;
    private String roomType;
    private String description;
    private List<PhotoDto> photos;
    private List<Amenity> amenities;
    private List<Services> services;
    private List<AttachmentTypeDto> attachments;
    private PriceDto price;
    private List<ReviewDto> reviews;
}
