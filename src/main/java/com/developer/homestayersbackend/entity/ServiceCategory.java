package com.developer.homestayersbackend.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ServiceCategory {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String iconUrl;

}
