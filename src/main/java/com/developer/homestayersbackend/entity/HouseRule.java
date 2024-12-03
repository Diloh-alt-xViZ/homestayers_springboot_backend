package com.developer.homestayersbackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "house_rules")
public class HouseRule {

    @Id
    @GeneratedValue
    @Column(name = "house_rule_id")
    private Long id;
    private String ruleText;
}
