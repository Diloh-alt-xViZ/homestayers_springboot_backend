package com.developer.homestayersbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmenityCategoryDto {

    private String categoryName;
    private String iconUrl;
    private String description;
}
