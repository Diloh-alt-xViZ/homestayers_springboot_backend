package com.developer.homestayersbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {

    private String comment;
    private int rating;
    private Long propertyId;
    private Long userId;

}
