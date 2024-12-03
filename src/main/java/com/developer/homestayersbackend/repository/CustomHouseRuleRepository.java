package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.CustomHouseRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomHouseRuleRepository extends JpaRepository<CustomHouseRule, Long> {
}
