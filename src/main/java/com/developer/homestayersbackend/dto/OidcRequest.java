package com.developer.homestayersbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OidcRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private PhotoDto profilePicture;
}
