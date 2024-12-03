package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {

    ServiceCategory findByName(String name);
}
