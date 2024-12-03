package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.CancellationPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CancellationPolicyRepository extends JpaRepository<CancellationPolicy, Long> {

    Optional<CancellationPolicy> findByName(String name);
}
