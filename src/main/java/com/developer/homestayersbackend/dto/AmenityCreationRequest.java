package com.developer.homestayersbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmenityCreationRequest {
    private String name;
    private String description;
    private String iconUrl;
    private int categoryId;
}