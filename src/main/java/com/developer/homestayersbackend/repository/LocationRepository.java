package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
