package com.travelapp.controller.reviews;

import com.travelapp.dto.reviews.ReviewDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IReviewController {

    ResponseEntity<ReviewDto> createReview(@Valid @NotNull ReviewDto reviewDto);

    ResponseEntity<ReviewDto> updateReview(Long id, @Valid @NotNull ReviewDto reviewDto);

    ResponseEntity<ReviewDto> getReviewById(Long id);

    ResponseEntity<Void> deleteReview(Long id);

    ResponseEntity<List<ReviewDto>> getAllReviews();

    ResponseEntity<Page<ReviewDto>> getAllReviewsWithPagination(Pageable pageable);

    ResponseEntity<ReviewDto> getReviewByBookingId(Long bookingId);

    ResponseEntity<List<ReviewDto>> getReviewsByUserId(Long userId);

    ResponseEntity<Page<ReviewDto>> getReviewsByUserIdWithPagination(Long userId, Pageable pageable);

    ResponseEntity<List<ReviewDto>> getReviewsByTourId(Long tourId);

    ResponseEntity<Page<ReviewDto>> getReviewsByTourIdWithPagination(Long tourId, Pageable pageable);

    ResponseEntity<List<ReviewDto>> getApprovedReviews();

    ResponseEntity<Page<ReviewDto>> getApprovedReviewsWithPagination(Pageable pageable);

    ResponseEntity<List<ReviewDto>> getApprovedReviewsByTourId(Long tourId);

    ResponseEntity<Page<ReviewDto>> getApprovedReviewsByTourIdWithPagination(Long tourId, Pageable pageable);

    ResponseEntity<List<ReviewDto>> getFeaturedReviews();

    ResponseEntity<Page<ReviewDto>> getFeaturedReviewsWithPagination(Pageable pageable);

    ResponseEntity<List<ReviewDto>> getPendingReviews();

    ResponseEntity<Page<ReviewDto>> getPendingReviewsWithPagination(Pageable pageable);

    ResponseEntity<ReviewDto> approveReview(Long id, String moderatorNotes);

    ResponseEntity<ReviewDto> rejectReview(Long id, String moderatorNotes);

    ResponseEntity<ReviewDto> setFeaturedStatus(Long id, boolean featured);

    ResponseEntity<ReviewDto> incrementHelpfulCount(Long id);

    ResponseEntity<ReviewDto> decrementHelpfulCount(Long id);

    ResponseEntity<List<ReviewDto>> getReviewsByRating(Integer rating);

    ResponseEntity<Page<ReviewDto>> getReviewsByRatingWithPagination(Integer rating, Pageable pageable);

    ResponseEntity<List<ReviewDto>> getReviewsByTourIdAndRating(Long tourId, Integer rating);

    ResponseEntity<List<ReviewDto>> getMostHelpfulReviews(Integer minCount);

    ResponseEntity<Page<ReviewDto>> getMostHelpfulReviewsWithPagination(Integer minCount, Pageable pageable);

    ResponseEntity<Map<String, Double>> getTourAverageRatings(Long tourId);

    ResponseEntity<Double> getTourAverageRating(Long tourId);

    ResponseEntity<Long> getTourReviewCount(Long tourId);

    ResponseEntity<Long> getUserReviewCount(Long userId);

    ResponseEntity<Long> getPendingReviewCount();

    ResponseEntity<Boolean> hasUserReviewedBooking(Long bookingId);

    ResponseEntity<Boolean> hasUserReviewedTour(Long userId, Long tourId);

    ResponseEntity<Boolean> canUserReviewBooking(Long userId, Long bookingId);

    ResponseEntity<List<ReviewDto>> approveMultipleReviews(List<Long> reviewIds, String moderatorNotes);

    ResponseEntity<List<ReviewDto>> rejectMultipleReviews(List<Long> reviewIds, String moderatorNotes);

}
