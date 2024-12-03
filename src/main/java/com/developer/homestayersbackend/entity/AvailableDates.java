package com.developer.homestayersbackend.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AvailableDates {

    @Id
    @GeneratedValue
    private Long id;
    private Date availableDate;

    @ManyToOne
    private Property property;
    @ManyToOne
    private Room room;
}
