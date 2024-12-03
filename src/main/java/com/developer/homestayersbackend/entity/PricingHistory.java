package com.developer.homestayersbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class PricingHistory {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Price price;
    private Date dateDisabled;
}
