package com.developer.homestayersbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailsUpdateDto {
    private String title;
    private String description;
    private List<PhotoDto> photos;
    private PriceDto price;
}
