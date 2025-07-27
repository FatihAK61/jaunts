package com.travelapp.services.impl.reviews;

import com.travelapp.dto.reviews.ReviewDto;
import com.travelapp.helper.enums.BookingStatus;
import com.travelapp.helper.errorhandler.InvalidOperationException;
import com.travelapp.models.book.Booking;
import com.travelapp.models.reviews.Review;
import com.travelapp.models.tours.Tour;
import com.travelapp.models.users.UserBase;
import com.travelapp.repository.book.BookingRepository;
import com.travelapp.repository.reviews.ReviewRepository;
import com.travelapp.repository.tours.TourRepository;
import com.travelapp.repository.users.UserBaseRepository;
import com.travelapp.services.impl.reviews.mappers.ReviewMapperService;
import com.travelapp.services.reviews.IReviewService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final TourRepository tourRepository;
    private final BookingRepository bookingRepository;
    private final UserBaseRepository userBaseRepository;
    private final ReviewMapperService reviewMapperService;

    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {
        if (!canUserReviewBooking(reviewDto.getUserId(), reviewDto.getBookingId()))
            throw new InvalidOperationException("User cannot review this booking.");

        if (hasUserReviewedBooking(reviewDto.getBookingId()))
            throw new EntityExistsException("Review already exists for this booking.");
        
        Review review = reviewMapperService.toEntity(reviewDto);

        setReviewRelationships(review, reviewDto);

        review.setApproved(false);
        review.setFeatured(false);
        review.setHelpfulCount(0);

        Review savedReview = reviewRepository.save(review);

        return reviewMapperService.toDto(savedReview);
    }

    @Override
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with ID: " + id));

        existingReview.setRating(reviewDto.getRating());
        existingReview.setServiceRating(reviewDto.getServiceRating());
        existingReview.setValueRating(reviewDto.getValueRating());
        existingReview.setCleanlinessRating(reviewDto.getCleanlinessRating());
        existingReview.setComment(reviewDto.getComment());
        existingReview.setTitle(reviewDto.getTitle());
        existingReview.setImages(reviewDto.getImages());

        Review savedReview = reviewRepository.save(existingReview);

        return reviewMapperService.toDto(savedReview);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewDto> getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(reviewMapperService::toDto);
    }

    @Override
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new EntityNotFoundException("Review not found with ID: " + id);
        }

        reviewRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getAllReviews() {
        return reviewMapperService.toDtoList(reviewRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDto> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable)
                .map(reviewMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewDto> getReviewByBookingId(Long bookingId) {
        return reviewRepository.findByBookingId(bookingId)
                .map(reviewMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewsByUserId(Long userId) {
        return reviewMapperService.toDtoList(
                reviewRepository.findByUserIdOrderByCreatedAtDesc(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDto> getReviewsByUserId(Long userId, Pageable pageable) {
        return reviewRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(reviewMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewsByTourId(Long tourId) {
        return reviewMapperService.toDtoList(
                reviewRepository.findByTourIdOrderByCreatedAtDesc(tourId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDto> getReviewsByTourId(Long tourId, Pageable pageable) {
        return reviewRepository.findByTourIdOrderByCreatedAtDesc(tourId, pageable)
                .map(reviewMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getApprovedReviews() {
        return reviewMapperService.toDtoList(
                reviewRepository.findByApprovedTrueOrderByCreatedAtDesc());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDto> getApprovedReviews(Pageable pageable) {
        return reviewRepository.findByApprovedTrueOrderByCreatedAtDesc(pageable)
                .map(reviewMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getApprovedReviewsByTourId(Long tourId) {
        return reviewMapperService.toDtoList(
                reviewRepository.findByTourIdAndApprovedTrueOrderByCreatedAtDesc(tourId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDto> getApprovedReviewsByTourId(Long tourId, Pageable pageable) {
        return reviewRepository.findByTourIdAndApprovedTrueOrderByCreatedAtDesc(tourId, pageable)
                .map(reviewMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getFeaturedReviews() {
        return reviewMapperService.toDtoList(
                reviewRepository.findByFeaturedTrueAndApprovedTrueOrderByCreatedAtDesc());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDto> getFeaturedReviews(Pageable pageable) {
        return reviewRepository.findByFeaturedTrueAndApprovedTrueOrderByCreatedAtDesc(pageable)
                .map(reviewMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getPendingReviews() {
        return reviewMapperService.toDtoList(
                reviewRepository.findByApprovedFalseOrderByCreatedAtAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDto> getPendingReviews(Pageable pageable) {
        return reviewRepository.findByApprovedFalseOrderByCreatedAtAsc(pageable)
                .map(reviewMapperService::toDto);
    }

    @Override
    public ReviewDto approveReview(Long id, String moderatorNotes) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with ID: " + id));

        review.setApproved(true);
        review.setApprovedAt(LocalDateTime.now());
        review.setModeratorNotes(moderatorNotes);

        Review savedReview = reviewRepository.save(review);

        return reviewMapperService.toDto(savedReview);
    }

    @Override
    public ReviewDto rejectReview(Long id, String moderatorNotes) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with ID: " + id));

        review.setApproved(false);
        review.setApprovedAt(null);
        review.setModeratorNotes(moderatorNotes);

        Review savedReview = reviewRepository.save(review);

        return reviewMapperService.toDto(savedReview);
    }

    @Override
    public ReviewDto setFeaturedStatus(Long id, boolean featured) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with ID: " + id));

        if (!review.getApproved()) {
            throw new InvalidOperationException("Cannot feature an unapproved review.");
        }

        review.setFeatured(featured);
        Review savedReview = reviewRepository.save(review);

        return reviewMapperService.toDto(savedReview);
    }

    @Override
    public ReviewDto incrementHelpfulCount(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with ID: " + id));

        review.setHelpfulCount(review.getHelpfulCount() + 1);
        Review savedReview = reviewRepository.save(review);

        return reviewMapperService.toDto(savedReview);
    }

    @Override
    public ReviewDto decrementHelpfulCount(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with ID: " + id));

        int newCount = Math.max(0, review.getHelpfulCount() - 1);
        review.setHelpfulCount(newCount);
        Review savedReview = reviewRepository.save(review);

        return reviewMapperService.toDto(savedReview);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewsByRating(Integer rating) {
        return reviewMapperService.toDtoList(
                reviewRepository.findByRatingAndApprovedTrueOrderByCreatedAtDesc(rating));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDto> getReviewsByRating(Integer rating, Pageable pageable) {
        return reviewRepository.findByRatingAndApprovedTrueOrderByCreatedAtDesc(rating, pageable)
                .map(reviewMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewsByTourIdAndRating(Long tourId, Integer rating) {
        return reviewMapperService.toDtoList(
                reviewRepository.findByTourIdAndRatingAndApprovedTrueOrderByCreatedAtDesc(tourId, rating));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getMostHelpfulReviews(Integer minCount) {
        return reviewMapperService.toDtoList(
                reviewRepository.findMostHelpfulReviews(minCount));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDto> getMostHelpfulReviews(Integer minCount, Pageable pageable) {
        return reviewRepository.findMostHelpfulReviews(minCount, pageable)
                .map(reviewMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Double> getTourAverageRatings(Long tourId) {
        Map<String, Double> ratings = new HashMap<>();

        ratings.put("overall", reviewRepository.findAverageRatingByTourId(tourId));
        ratings.put("service", reviewRepository.findAverageServiceRatingByTourId(tourId));
        ratings.put("value", reviewRepository.findAverageValueRatingByTourId(tourId));
        ratings.put("cleanliness", reviewRepository.findAverageCleanlinessRatingByTourId(tourId));

        return ratings;
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTourAverageRating(Long tourId) {
        return reviewRepository.findAverageRatingByTourId(tourId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getTourReviewCount(Long tourId) {
        return reviewRepository.countByTourIdAndApprovedTrue(tourId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getUserReviewCount(Long userId) {
        return reviewRepository.countByUserIdAndApprovedTrue(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getPendingReviewCount() {
        return reviewRepository.countByApprovedFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasUserReviewedBooking(Long bookingId) {
        return reviewRepository.existsByBookingId(bookingId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasUserReviewedTour(Long userId, Long tourId) {
        return reviewRepository.existsByUserIdAndTourId(userId, tourId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canUserReviewBooking(Long userId, Long bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty() || !booking.get().getUser().getId().equals(userId)) {
            return false;
        }

        BookingStatus status = booking.get().getStatus();
        return status == BookingStatus.COMPLETED;
    }

    @Override
    public List<ReviewDto> approveMultipleReviews(List<Long> reviewIds, String moderatorNotes) {
        List<Review> reviews = reviewRepository.findAllById(reviewIds);
        LocalDateTime now = LocalDateTime.now();

        reviews.forEach(review -> {
            review.setApproved(true);
            review.setApprovedAt(now);
            review.setModeratorNotes(moderatorNotes);
        });

        List<Review> savedReviews = reviewRepository.saveAll(reviews);

        return reviewMapperService.toDtoList(savedReviews);
    }

    @Override
    public List<ReviewDto> rejectMultipleReviews(List<Long> reviewIds, String moderatorNotes) {
        List<Review> reviews = reviewRepository.findAllById(reviewIds);

        reviews.forEach(review -> {
            review.setApproved(false);
            review.setApprovedAt(null);
            review.setModeratorNotes(moderatorNotes);
        });

        List<Review> savedReviews = reviewRepository.saveAll(reviews);

        return reviewMapperService.toDtoList(savedReviews);
    }

    private void setReviewRelationships(Review review, ReviewDto dto) {
        if (dto.getBookingId() != null) {
            Booking booking = bookingRepository.findById(dto.getBookingId())
                    .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + dto.getBookingId()));
            review.setBooking(booking);
        }

        if (dto.getUserId() != null) {
            UserBase user = userBaseRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + dto.getUserId()));
            review.setUser(user);
        }

        if (dto.getTourId() != null) {
            Tour tour = tourRepository.findById(dto.getTourId())
                    .orElseThrow(() -> new IllegalArgumentException("Tour not found with ID: " + dto.getTourId()));
            review.setTour(tour);
        }
    }
}
