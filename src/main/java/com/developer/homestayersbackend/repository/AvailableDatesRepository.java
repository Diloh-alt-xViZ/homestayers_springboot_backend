package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.AvailableDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableDatesRepository extends JpaRepository<AvailableDates,Long> {

}
