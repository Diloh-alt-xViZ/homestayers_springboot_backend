package com.developer.homestayersbackend.service.impl;

import com.developer.homestayersbackend.dto.ReviewRequest;
import com.developer.homestayersbackend.entity.Property;
import com.developer.homestayersbackend.entity.Review;
import com.developer.homestayersbackend.entity.ReviewReply;
import com.developer.homestayersbackend.entity.User;
import com.developer.homestayersbackend.exception.PropertyNotFoundException;
import com.developer.homestayersbackend.exception.ReviewNotFoundException;
import com.developer.homestayersbackend.exception.UserNotFoundException;
import com.developer.homestayersbackend.repository.PropertyRepository;
import com.developer.homestayersbackend.repository.ReviewReplyRepository;
import com.developer.homestayersbackend.repository.ReviewRepository;
import com.developer.homestayersbackend.repository.UserRepository;
import com.developer.homestayersbackend.service.api.ReviewService;
import com.developer.homestayersbackend.util.ReviewStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ReviewReplyRepository reviewReplyRepository;
    private final PropertyRepository propertyRepository;

    @Override
    public List<Review> getAllReviewReplies(Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);

        if (review.isPresent()) {
            return reviewReplyRepository.findByReplyReviewId(reviewId).stream().map(ReviewReply::getReview).toList();
        }
        else throw new ReviewNotFoundException();
    }

    @Override
    public List<Review> getAllUserReviews(Long userId) {
        System.out.println("In there");
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new UserNotFoundException();
        }
        System.out.println("Found our user:"+user.get().getId());
        List<Review> userReviews = reviewRepository.findReviewsByUserId(userId);
        return userReviews;
    }

    @Override
    public List<Review> getAllReviews(Long propertyId) {
        Optional<Property> property = propertyRepository.findById(propertyId);
        List<Review> reviewList;
        if (property.isPresent()) {

            reviewList = reviewRepository.findReviewsByPropertyId(property.get().getId());
            reviewList = reviewList.stream().filter(ob->ob.getReviewStatus().equals(ReviewStatus.ACTIVE)).collect(Collectors.toList());
            return reviewList;
        }
        else throw new PropertyNotFoundException();
    }

    @Override
    public Review createReview(ReviewRequest review) {
        Review reviewEntity = new Review();
        reviewEntity.setCreatedAt(new Date(System.currentTimeMillis()));

        Optional<User> user = userRepository.findById(review.getUserId());
        Optional<Property> property = propertyRepository.findById(review.getPropertyId());
        if (user.isPresent() && property.isPresent()) {
            reviewEntity.setUser(user.get());
            reviewEntity.setReviewStatus(ReviewStatus.ACTIVE);
            reviewEntity.setProperty(property.get());
            setReviewData(review,reviewEntity);
            return reviewRepository.save(reviewEntity);
        }
        return null;
    }

    private void setReviewData(ReviewRequest request,Review review){
        review.setCreatedAt(new Date(System.currentTimeMillis()));
        review.setRating(request.getRating());
        review.setComment(request.getComment());
    }

    @Override
    public Review changeReviewStatus(Long reviewId) {
        Optional<Review> reviewEntity = reviewRepository.findById(reviewId);

        if (reviewEntity.isEmpty()){
            throw new ReviewNotFoundException();
        }
        Review review = reviewEntity.get();
        review.setReviewStatus(ReviewStatus.DELETED);
        return reviewRepository.save(review);
    }

    @Override
    public ReviewReply createReviewReply(Long reviewId, ReviewRequest request) {

        //Create new reply and save
        //Create new ReviewReply and save



        Optional<Review> review = reviewRepository.findById(reviewId);
        Optional<User> user = userRepository.findById(request.getUserId());
        Optional<Property> property = propertyRepository.findById(request.getPropertyId());

        Review reply = new Review();
        ReviewReply reviewReply = new ReviewReply();

        if (review.isPresent() && user.isPresent() && property.isPresent()){
                setReviewData(request,reply);
                reply.setUser(user.get());
                reply.setProperty(property.get());
                reviewReply.setReplyReview(review.get());
                reviewReply.setCreatedAt(new Date(System.currentTimeMillis()));
                reviewReply.setReview(reviewRepository.save(reply))
                ;
                reviewReplyRepository.save(reviewReply);
        }
        return null;
    }
}
