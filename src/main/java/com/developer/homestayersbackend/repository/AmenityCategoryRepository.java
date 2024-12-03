package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.AmenityCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmenityCategoryRepository extends JpaRepository<AmenityCategory, Integer> {
    Optional<AmenityCategory> findByName(String name);
}
