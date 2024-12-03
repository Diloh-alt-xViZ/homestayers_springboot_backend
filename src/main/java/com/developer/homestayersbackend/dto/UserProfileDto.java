package com.developer.homestayersbackend.dto;


import com.developer.homestayersbackend.entity.Address;
import com.developer.homestayersbackend.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDto {

    private String address;
    private String street;
    private String city;
    private String state;
    private String country;
    private String bio;
    private Date dob;
    private String idNumber;
    private String firstName;
    private String lastName;
    private String intro;
    private String school;
    private String work;
    private PhotoDto profilePhoto;
    private String gender;

}
