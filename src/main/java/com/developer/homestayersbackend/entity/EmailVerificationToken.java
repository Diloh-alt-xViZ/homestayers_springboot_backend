package com.developer.homestayersbackend.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmailVerificationToken {

    @Id
    @GeneratedValue
    private Long id;
    private String token;
    @OneToOne
    private User user;
    private LocalDateTime expiryDate;
}
