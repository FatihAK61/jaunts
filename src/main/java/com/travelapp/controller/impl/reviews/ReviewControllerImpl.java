package com.travelapp.controller.impl.reviews;

import com.travelapp.controller.reviews.IReviewController;
import com.travelapp.dto.reviews.ReviewDto;
import com.travelapp.services.reviews.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewControllerImpl implements IReviewController {

    private final IReviewService reviewService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        ReviewDto createdReview = reviewService.createReview(reviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @Override
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(name = "id") Long id, @RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReview = reviewService.updateReview(id, reviewDto);
        return ResponseEntity.ok(updatedReview);
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(name = "id") Long id) {
        Optional<ReviewDto> review = reviewService.getReviewById(id);
        return review.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable(name = "id") Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping(path = "/all")
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<ReviewDto> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/all/paginated")
    public ResponseEntity<Page<ReviewDto>> getAllReviewsWithPagination(Pageable pageable) {
        Page<ReviewDto> reviews = reviewService.getAllReviews(pageable);
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/booking/{bookingId}")
    public ResponseEntity<ReviewDto> getReviewByBookingId(@PathVariable(name = "bookingId") Long bookingId) {
        Optional<ReviewDto> review = reviewService.getReviewByBookingId(bookingId);
        return review.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByUserId(@PathVariable(name = "userId") Long userId) {
        List<ReviewDto> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/user/{userId}/paginated")
    public ResponseEntity<Page<ReviewDto>> getReviewsByUserIdWithPagination(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        Page<ReviewDto> reviews = reviewService.getReviewsByUserId(userId, pageable);
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/tour/{tourId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByTourId(@PathVariable(name = "tourId") Long tourId) {
        List<ReviewDto> reviews = reviewService.getReviewsByTourId(tourId);
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/tour/{tourId}/paginated")
    public ResponseEntity<Page<ReviewDto>> getReviewsByTourIdWithPagination(@PathVariable(name = "tourId") Long tourId, Pageable pageable) {
        Page<ReviewDto> reviews = reviewService.getReviewsByTourId(tourId, pageable);
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/approved")
    public ResponseEntity<List<ReviewDto>> getApprovedReviews() {
        List<ReviewDto> reviews = reviewService.getApprovedReviews();
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/approved/paginated")
    public ResponseEntity<Page<ReviewDto>> getApprovedReviewsWithPagination(Pageable pageable) {
        Page<ReviewDto> reviews = reviewService.getApprovedReviews(pageable);
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/approved/tour/{tourId}")
    public ResponseEntity<List<ReviewDto>> getApprovedReviewsByTourId(@PathVariable(name = "tourId") Long tourId) {
        List<ReviewDto> reviews = reviewService.getApprovedReviewsByTourId(tourId);
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/approved/tour/{tourId}/paginated")
    public ResponseEntity<Page<ReviewDto>> getApprovedReviewsByTourIdWithPagination(@PathVariable(name = "tourId") Long tourId, Pageable pageable) {
        Page<ReviewDto> reviews = reviewService.getApprovedReviewsByTourId(tourId, pageable);
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/featured")
    public ResponseEntity<List<ReviewDto>> getFeaturedReviews() {
        List<ReviewDto> reviews = reviewService.getFeaturedReviews();
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/featured/paginated")
    public ResponseEntity<Page<ReviewDto>> getFeaturedReviewsWithPagination(Pageable pageable) {
        Page<ReviewDto> reviews = reviewService.getFeaturedReviews(pageable);
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/pending")
    public ResponseEntity<List<ReviewDto>> getPendingReviews() {
        List<ReviewDto> reviews = reviewService.getPendingReviews();
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/pending/paginated")
    public ResponseEntity<Page<ReviewDto>> getPendingReviewsWithPagination(Pageable pageable) {
        Page<ReviewDto> reviews = reviewService.getPendingReviews(pageable);
        return ResponseEntity.ok(reviews);
    }

    @Override
    @PatchMapping(path = "/{id}/approve")
    public ResponseEntity<ReviewDto> approveReview(@PathVariable(name = "id") Long id, @RequestParam(required = false) String moderatorNotes) {
        ReviewDto review = reviewService.approveReview(id, moderatorNotes);
        return ResponseEntity.ok(review);
    }

    @Override
    @PatchMapping(path = "/{id}/reject")
    public ResponseEntity<ReviewDto> rejectReview(@PathVariable(name = "id") Long id, @RequestParam(required = false) String moderatorNotes) {
        ReviewDto review = reviewService.rejectReview(id, moderatorNotes);
        return ResponseEntity.ok(review);
    }

    @Override
    @PatchMapping(path = "/{id}/featured")
    public ResponseEntity<ReviewDto> setFeaturedStatus(@PathVariable(name = "id") Long id, @RequestParam boolean featured) {
        ReviewDto review = reviewService.setFeaturedStatus(id, featured);
        return ResponseEntity.ok(review);
    }

    @Override
    @PatchMapping(path = "/{id}/helpful/increment")
    public ResponseEntity<ReviewDto> incrementHelpfulCount(@PathVariable(name = "id") Long id) {
        ReviewDto review = reviewService.incrementHelpfulCount(id);
        return ResponseEntity.ok(review);
    }

    @Override
    @PatchMapping(path = "/{id}/helpful/decrement")
    public ResponseEntity<ReviewDto> decrementHelpfulCount(@PathVariable(name = "id") Long id) {
        ReviewDto review = reviewService.decrementHelpfulCount(id);
        return ResponseEntity.ok(review);
    }

    @Override
    @GetMapping(path = "/rating/{rating}")
    public ResponseEntity<List<ReviewDto>> getReviewsByRating(@PathVariable(name = "rating") Integer rating) {
        List<ReviewDto> reviews = reviewService.getReviewsByRating(rating);
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/rating/{rating}/paginated")
    public ResponseEntity<Page<ReviewDto>> getReviewsByRatingWithPagination(@PathVariable(name = "rating") Integer rating, Pageable pageable) {
        Page<ReviewDto> reviews = reviewService.getReviewsByRating(rating, pageable);
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/tour/{tourId}/rating/{rating}")
    public ResponseEntity<List<ReviewDto>> getReviewsByTourIdAndRating(@PathVariable(name = "tourId") Long tourId, @PathVariable(name = "rating") Integer rating) {
        List<ReviewDto> reviews = reviewService.getReviewsByTourIdAndRating(tourId, rating);
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/helpful")
    public ResponseEntity<List<ReviewDto>> getMostHelpfulReviews(@RequestParam Integer minCount) {
        List<ReviewDto> reviews = reviewService.getMostHelpfulReviews(minCount);
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/helpful/paginated")
    public ResponseEntity<Page<ReviewDto>> getMostHelpfulReviewsWithPagination(@RequestParam Integer minCount, Pageable pageable) {
        Page<ReviewDto> reviews = reviewService.getMostHelpfulReviews(minCount, pageable);
        return ResponseEntity.ok(reviews);
    }

    @Override
    @GetMapping(path = "/tour/{tourId}/ratings/average")
    public ResponseEntity<Map<String, Double>> getTourAverageRatings(@PathVariable(name = "tourId") Long tourId) {
        Map<String, Double> ratings = reviewService.getTourAverageRatings(tourId);
        return ResponseEntity.ok(ratings);
    }

    @Override
    @GetMapping(path = "/tour/{tourId}/rating/average")
    public ResponseEntity<Double> getTourAverageRating(@PathVariable(name = "tourId") Long tourId) {
        Double averageRating = reviewService.getTourAverageRating(tourId);
        return ResponseEntity.ok(averageRating);
    }

    @Override
    @GetMapping(path = "/tour/{tourId}/count")
    public ResponseEntity<Long> getTourReviewCount(@PathVariable(name = "tourId") Long tourId) {
        Long count = reviewService.getTourReviewCount(tourId);
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping(path = "/user/{userId}/count")
    public ResponseEntity<Long> getUserReviewCount(@PathVariable(name = "userId") Long userId) {
        Long count = reviewService.getUserReviewCount(userId);
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping(path = "/pending/count")
    public ResponseEntity<Long> getPendingReviewCount() {
        Long count = reviewService.getPendingReviewCount();
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping(path = "/booking/{bookingId}/exists")
    public ResponseEntity<Boolean> hasUserReviewedBooking(@PathVariable(name = "bookingId") Long bookingId) {
        boolean exists = reviewService.hasUserReviewedBooking(bookingId);
        return ResponseEntity.ok(exists);
    }

    @Override
    @GetMapping(path = "/user/{userId}/tour/{tourId}/exists")
    public ResponseEntity<Boolean> hasUserReviewedTour(@PathVariable(name = "userId") Long userId, @PathVariable(name = "tourId") Long tourId) {
        boolean exists = reviewService.hasUserReviewedTour(userId, tourId);
        return ResponseEntity.ok(exists);
    }

    @Override
    @GetMapping(path = "/user/{userId}/booking/{bookingId}/can-review")
    public ResponseEntity<Boolean> canUserReviewBooking(@PathVariable(name = "userId") Long userId, @PathVariable(name = "bookingId") Long bookingId) {
        boolean canReview = reviewService.canUserReviewBooking(userId, bookingId);
        return ResponseEntity.ok(canReview);
    }

    @Override
    @PatchMapping(path = "/bulk/approve")
    public ResponseEntity<List<ReviewDto>> approveMultipleReviews(@RequestBody List<Long> reviewIds, @RequestParam(required = false) String moderatorNotes) {
        List<ReviewDto> reviews = reviewService.approveMultipleReviews(reviewIds, moderatorNotes);
        return ResponseEntity.ok(reviews);
    }

    @Override
    @PatchMapping(path = "/bulk/reject")
    public ResponseEntity<List<ReviewDto>> rejectMultipleReviews(@RequestBody List<Long> reviewIds, @RequestParam(required = false) String moderatorNotes) {
        List<ReviewDto> reviews = reviewService.rejectMultipleReviews(reviewIds, moderatorNotes);
        return ResponseEntity.ok(reviews);
    }
}
