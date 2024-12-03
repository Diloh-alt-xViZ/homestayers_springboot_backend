package com.developer.homestayersbackend.entity;

import com.developer.homestayersbackend.util.RentalType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Rental extends Property {
    @Enumerated(EnumType.STRING)
    private RentalType rentalType;
    @OneToMany
    private List<RentalRoom> rentalRooms;

    @ElementCollection
    @CollectionTable(name = "rental_shared_spaces")
    private List<SharedSpace> sharedSpaces;
}
