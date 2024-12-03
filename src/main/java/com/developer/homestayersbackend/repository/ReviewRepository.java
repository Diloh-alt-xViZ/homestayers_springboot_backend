package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.Review;
import com.developer.homestayersbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewsByPropertyId(Long propertyId);
    List<Review> findReviewsByUserId(Long userId);
}
