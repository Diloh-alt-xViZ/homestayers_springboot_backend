package com.developer.homestayersbackend.controller;

import com.developer.homestayersbackend.dto.ReviewDto;
import com.developer.homestayersbackend.dto.ReviewRequest;
import com.developer.homestayersbackend.dto.ReviewResponseDto;
import com.developer.homestayersbackend.entity.Review;
import com.developer.homestayersbackend.entity.ReviewReply;
import com.developer.homestayersbackend.service.api.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/property/reviews")
public class ReviewController {

    private final ReviewService reviewService;


    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<ReviewResponseDto> addReview(@RequestBody ReviewRequest review) {
        System.out.println("Review Data:"+review);
        Review reviewResponse = reviewService.createReview(review);
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto();
        if(reviewResponse==null){
            return ResponseEntity.ok().body(null);
        }
        reviewResponseDto.setComment(reviewResponse.getComment());
        reviewResponseDto.setRating(reviewResponse.getRating());
        reviewResponseDto.setId(reviewResponse.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponseDto);
    }


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{propertyId}")
    public ResponseEntity<List<Review>> getReviewById(@PathVariable("propertyId") Long propertyId) {

        return ResponseEntity.ok(reviewService.getAllReviews(propertyId));
    }


    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{reviewId}")
    public ResponseEntity<List<Review>> getReviewReplies(@PathVariable Long reviewId) {

        return ResponseEntity.ok(reviewService.getAllReviewReplies(reviewId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{reviewId}")
    public ResponseEntity<ReviewReply> createReviewReply(@PathVariable Long reviewId, @RequestBody ReviewRequest review) {

        return  ResponseEntity.ok(reviewService.createReviewReply(reviewId,review));
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> changeReviewStatus(@PathVariable("reviewId") Long reviewId) {

        return ResponseEntity.ok(reviewService.changeReviewStatus(reviewId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{userId}/allReviews")
    public ResponseEntity<List<ReviewDto>> getAllUserReviews(@PathVariable Long userId) {

        List<Review> reviews = reviewService.getAllUserReviews(userId);
        List<ReviewDto> reviewDtos =  reviews.stream().map((rev)->{
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setId(rev.getId());
            reviewDto.setComment(rev.getComment());
            reviewDto.setRating(rev.getRating());
            reviewDto.setCreatedAt(rev.getCreatedAt());
            reviewDto.setUserId(rev.getUser().getId());
            return reviewDto;
        }).toList();
        System.out.println("My Review List: " + reviews);
        return ResponseEntity.ok(reviewDtos);
    }
}
