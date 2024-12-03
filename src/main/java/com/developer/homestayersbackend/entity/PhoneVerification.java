package com.developer.homestayersbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PhoneVerification {

    @Id
    @GeneratedValue
    private Long id;
    private String phoneNumber;
    private String verificationCode;
    private LocalDateTime expirationDate;

}
