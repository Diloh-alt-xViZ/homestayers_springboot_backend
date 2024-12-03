package com.developer.homestayersbackend.service.api;

import com.developer.homestayersbackend.dto.ReviewRequest;
import com.developer.homestayersbackend.entity.Review;
import com.developer.homestayersbackend.entity.ReviewReply;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviewReplies(Long reviewId);
    List<Review> getAllReviews(Long propertyId);
    Review createReview(ReviewRequest review);
    ReviewReply createReviewReply(Long reviewId, ReviewRequest request);
    Review changeReviewStatus(Long reviewId);

    List<Review> getAllUserReviews(Long userId);
}
