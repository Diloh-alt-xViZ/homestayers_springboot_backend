package com.developer.homestayersbackend.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneUserProfileDetailsDto {

    private String idNumber;
    private String firstName;
    private String lastName;
    private Date dob;
    private String gender;
    private String phoneNumber;
}
