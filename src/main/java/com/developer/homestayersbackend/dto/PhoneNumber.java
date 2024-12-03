package com.developer.homestayersbackend.dto;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumber {

    private String countryCode;
    private String number;


    public String getFullNumber(){

        return countryCode + number;
    }
}
