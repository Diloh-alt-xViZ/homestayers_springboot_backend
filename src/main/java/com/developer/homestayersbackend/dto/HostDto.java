package com.developer.homestayersbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HostDto {
    private Long id;
    private String firstName;
    private Date hostSince;
    private String profileUrl;
}
