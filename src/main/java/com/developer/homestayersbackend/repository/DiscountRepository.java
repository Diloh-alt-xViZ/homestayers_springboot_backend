package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
