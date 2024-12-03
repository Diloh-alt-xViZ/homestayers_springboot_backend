package com.developer.homestayersbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditCommonDataDto {
    private String title;
    private String description;
    private String neighbourhoodDetails;
    private String gettingAround;
    private PriceDto price;
}
