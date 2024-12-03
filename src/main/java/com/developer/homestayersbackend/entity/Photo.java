package com.developer.homestayersbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photo {

    public Photo(String url){
        this.url = url;
    }
    @Id
    @GeneratedValue
    @Column(name = "photo_id")
    private Long id;
    private String url;

}
