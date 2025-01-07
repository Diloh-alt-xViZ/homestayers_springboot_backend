package com.developer.homestayersbackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Payments {
    @Id
    @GeneratedValue
    private Long id;
    private Date dateOfPayment;
    private Date dateUpdated;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

}
