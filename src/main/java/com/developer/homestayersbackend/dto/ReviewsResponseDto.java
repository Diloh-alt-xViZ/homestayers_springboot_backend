package com.developer.homestayersbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsResponseDto {

    private Long id;
    private String profilePhoto;
    private String reviewerName;
    private String createdAt;
    private String comment;
    private int rating;
}
