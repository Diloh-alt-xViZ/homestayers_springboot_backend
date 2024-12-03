package com.developer.homestayersbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewResponseDto {

    private Date reviewDate;
    private Long userId;
    private int rating;
    private Long id;
    private String comment;
}
