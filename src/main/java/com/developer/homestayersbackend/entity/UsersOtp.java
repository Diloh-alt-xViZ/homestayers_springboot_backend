package com.developer.homestayersbackend.entity;

import com.developer.homestayersbackend.dto.PhoneNumber;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_otp")
public class UsersOtp {

    @Id
    @GeneratedValue
    private Long id;
    private String otp;
    private PhoneNumber phoneNumber;
}
