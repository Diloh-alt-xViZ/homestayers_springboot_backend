package com.developer.homestayersbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardPaymentRequest {

    private String cardType;
    private String holder;
    private String ccv;
    private String number;
    private String expiry;
}
