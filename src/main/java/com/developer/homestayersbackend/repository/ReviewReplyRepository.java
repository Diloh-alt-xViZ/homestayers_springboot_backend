package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.Review;
import com.developer.homestayersbackend.entity.ReviewReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewReplyRepository extends JpaRepository<ReviewReply, Long> {
    List<ReviewReply> findByReplyReviewId(Long reviewId);
}
