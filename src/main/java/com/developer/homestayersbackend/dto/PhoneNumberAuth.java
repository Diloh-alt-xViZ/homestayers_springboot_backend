package com.developer.homestayersbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberAuth {

    private String phoneNumber;
    private String otp;

}
