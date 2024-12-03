package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.PricingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingHistoryRepository extends JpaRepository<PricingHistory, Long> {
}
