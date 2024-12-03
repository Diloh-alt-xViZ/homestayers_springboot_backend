package com.developer.homestayersbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "amenities_categories")
public class AmenityCategory {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
    private String iconUrl;
}
