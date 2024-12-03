package com.developer.homestayersbackend.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomHouseRule {

    @Id
    @GeneratedValue
    private Long id;
    private String ruleText;
    @ManyToOne
    private Property propertyId;
}
