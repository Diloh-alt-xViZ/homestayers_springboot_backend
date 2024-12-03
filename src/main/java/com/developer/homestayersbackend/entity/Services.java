package com.developer.homestayersbackend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Services {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String iconUrl;
    private String description;
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ServiceCategory category;
}
