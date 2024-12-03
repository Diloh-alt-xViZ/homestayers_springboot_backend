package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    Optional<Property> findByTitle(String title);


    List<Property> findByHostId(Long hostId);
}
