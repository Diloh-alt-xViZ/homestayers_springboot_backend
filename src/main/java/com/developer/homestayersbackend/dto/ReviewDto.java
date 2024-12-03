package com.developer.homestayersbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Long userId;
    private Long id;
    private String comment;
    private Date createdAt;
    private int rating;
    private String username;

}
