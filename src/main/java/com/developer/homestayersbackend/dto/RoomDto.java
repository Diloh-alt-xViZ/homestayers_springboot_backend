package com.developer.homestayersbackend.dto;

import com.developer.homestayersbackend.entity.Amenity;
import com.developer.homestayersbackend.entity.AttachmentType;
import com.developer.homestayersbackend.entity.Services;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoomDto {
    private String roomType;
    private String title;
    private PriceDto price;
    private List<AttachmentTypeDto> attachments;
    private String roomDescription;
    private List<PhotoDto> roomPhotos;
    private List<String> services;
    private List <String> amenities;
    @JsonIgnore(value = true)
    private List<String> bookedDates;
}
