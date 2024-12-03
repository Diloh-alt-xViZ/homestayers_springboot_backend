package com.developer.homestayersbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "amenities")
public class Amenity {

    @Id
    @GeneratedValue
    @Column(name = "amenity_id")
    private Long id;
    private String name;
    private String description;
    private String iconUrl;
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private AmenityCategory category;
}
