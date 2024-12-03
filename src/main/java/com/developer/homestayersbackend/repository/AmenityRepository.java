package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {

    List<Amenity> getAmenitiesByCategoryId(int categoryId);

    Optional<Amenity> findByName(String name);

    List<Amenity> findAllByName(String title);
}
