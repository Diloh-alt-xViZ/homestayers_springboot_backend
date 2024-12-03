package com.developer.homestayersbackend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Pricing {
    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private SystemCurrency currency;
    private BigDecimal total;
    @OneToMany
    private List<Discount> discount;
}
