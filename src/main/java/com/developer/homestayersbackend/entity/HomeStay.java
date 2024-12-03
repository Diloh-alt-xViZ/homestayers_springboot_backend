package com.developer.homestayersbackend.entity;

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
public class HomeStay extends  Property{

    @ElementCollection
    @CollectionTable(name = "homestay_shared_spaces")
    private List<SharedSpace> sharedSpaces;
    @Enumerated(EnumType.STRING)
    private StayType stayType;
}
