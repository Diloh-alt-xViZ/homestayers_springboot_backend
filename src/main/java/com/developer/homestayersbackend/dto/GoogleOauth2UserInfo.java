package com.developer.homestayersbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GoogleOauth2UserInfo {
    private String email;
    private String id;
    private String imageUrl;
    private String name;
}
