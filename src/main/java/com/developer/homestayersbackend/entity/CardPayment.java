package com.developer.homestayersbackend.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
public class CardPayment extends Payments{

    private String cardNumber;
    private String cardHolderName;
    private String cardExpiryDate;
    private String cardCVV;

}
