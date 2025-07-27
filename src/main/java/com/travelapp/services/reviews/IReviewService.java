package com.travelapp.services.reviews;

import com.travelapp.dto.reviews.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IReviewService {

    ReviewDto createReview(ReviewDto reviewDto);

    ReviewDto updateReview(Long id, ReviewDto reviewDto);

    Optional<ReviewDto> getReviewById(Long id);

    void deleteReview(Long id);

    List<ReviewDto> getAllReviews();

    Page<ReviewDto> getAllReviews(Pageable pageable);

    Optional<ReviewDto> getReviewByBookingId(Long bookingId);

    List<ReviewDto> getReviewsByUserId(Long userId);

    Page<ReviewDto> getReviewsByUserId(Long userId, Pageable pageable);

    List<ReviewDto> getReviewsByTourId(Long tourId);

    Page<ReviewDto> getReviewsByTourId(Long tourId, Pageable pageable);

    List<ReviewDto> getApprovedReviews();

    Page<ReviewDto> getApprovedReviews(Pageable pageable);

    List<ReviewDto> getApprovedReviewsByTourId(Long tourId);

    Page<ReviewDto> getApprovedReviewsByTourId(Long tourId, Pageable pageable);

    List<ReviewDto> getFeaturedReviews();

    Page<ReviewDto> getFeaturedReviews(Pageable pageable);

    List<ReviewDto> getPendingReviews();

    Page<ReviewDto> getPendingReviews(Pageable pageable);

    ReviewDto approveReview(Long id, String moderatorNotes);

    ReviewDto rejectReview(Long id, String moderatorNotes);

    ReviewDto setFeaturedStatus(Long id, boolean featured);

    ReviewDto incrementHelpfulCount(Long id);

    ReviewDto decrementHelpfulCount(Long id);

    List<ReviewDto> getReviewsByRating(Integer rating);

    Page<ReviewDto> getReviewsByRating(Integer rating, Pageable pageable);

    List<ReviewDto> getReviewsByTourIdAndRating(Long tourId, Integer rating);

    List<ReviewDto> getMostHelpfulReviews(Integer minCount);

    Page<ReviewDto> getMostHelpfulReviews(Integer minCount, Pageable pageable);

    Map<String, Double> getTourAverageRatings(Long tourId);

    Double getTourAverageRating(Long tourId);

    Long getTourReviewCount(Long tourId);

    Long getUserReviewCount(Long userId);

    Long getPendingReviewCount();

    boolean hasUserReviewedBooking(Long bookingId);

    boolean hasUserReviewedTour(Long userId, Long tourId);

    boolean canUserReviewBooking(Long userId, Long bookingId);

    List<ReviewDto> approveMultipleReviews(List<Long> reviewIds, String moderatorNotes);

    List<ReviewDto> rejectMultipleReviews(List<Long> reviewIds, String moderatorNotes);

}
