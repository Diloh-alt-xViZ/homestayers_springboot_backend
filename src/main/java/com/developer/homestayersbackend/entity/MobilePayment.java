package com.developer.homestayersbackend.entity;


import com.developer.homestayersbackend.dto.PhoneNumber;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
public class MobilePayment extends Payments {

    private PhoneNumber phoneNumber;
    @Enumerated(EnumType.STRING)
    private MobileProvider provider;
}
