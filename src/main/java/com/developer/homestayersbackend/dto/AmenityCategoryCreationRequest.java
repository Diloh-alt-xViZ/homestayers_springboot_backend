package com.developer.homestayersbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmenityCategoryCreationRequest {
    private String iconUrl;
    private String categoryName;
    private String description;
}
