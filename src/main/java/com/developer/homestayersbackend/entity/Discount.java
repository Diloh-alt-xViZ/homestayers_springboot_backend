package com.developer.homestayersbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Discount {


    @Id
    @GeneratedValue
    private Long id;
    private int discountPercentage;
    @ManyToOne
    Property property;
    private int duration;

}
