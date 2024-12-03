package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.Price;
import com.developer.homestayersbackend.entity.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingRepository extends JpaRepository<Price, Long> {
}
