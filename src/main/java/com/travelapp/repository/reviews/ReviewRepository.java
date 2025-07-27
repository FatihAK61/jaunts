package com.travelapp.repository.reviews;

import com.travelapp.models.reviews.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    Optional<Review> findByBookingId(Long bookingId);

    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);

    Page<Review> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    List<Review> findByTourIdOrderByCreatedAtDesc(Long tourId);

    Page<Review> findByTourIdOrderByCreatedAtDesc(Long tourId, Pageable pageable);

    List<Review> findByApprovedTrueOrderByCreatedAtDesc();

    Page<Review> findByApprovedTrueOrderByCreatedAtDesc(Pageable pageable);

    List<Review> findByTourIdAndApprovedTrueOrderByCreatedAtDesc(Long tourId);

    Page<Review> findByTourIdAndApprovedTrueOrderByCreatedAtDesc(Long tourId, Pageable pageable);

    List<Review> findByFeaturedTrueAndApprovedTrueOrderByCreatedAtDesc();

    Page<Review> findByFeaturedTrueAndApprovedTrueOrderByCreatedAtDesc(Pageable pageable);

    List<Review> findByApprovedFalseOrderByCreatedAtAsc();

    Page<Review> findByApprovedFalseOrderByCreatedAtAsc(Pageable pageable);

    List<Review> findByRatingAndApprovedTrueOrderByCreatedAtDesc(Integer rating);

    Page<Review> findByRatingAndApprovedTrueOrderByCreatedAtDesc(Integer rating, Pageable pageable);

    List<Review> findByTourIdAndRatingAndApprovedTrueOrderByCreatedAtDesc(Long tourId, Integer rating);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.tour.id = :tourId AND r.approved = true")
    Double findAverageRatingByTourId(@Param("tourId") Long tourId);

    @Query("SELECT AVG(r.serviceRating) FROM Review r WHERE r.tour.id = :tourId AND r.approved = true")
    Double findAverageServiceRatingByTourId(@Param("tourId") Long tourId);

    @Query("SELECT AVG(r.valueRating) FROM Review r WHERE r.tour.id = :tourId AND r.approved = true")
    Double findAverageValueRatingByTourId(@Param("tourId") Long tourId);

    @Query("SELECT AVG(r.cleanlinessRating) FROM Review r WHERE r.tour.id = :tourId AND r.approved = true")
    Double findAverageCleanlinessRatingByTourId(@Param("tourId") Long tourId);

    Long countByTourIdAndApprovedTrue(Long tourId);

    Long countByUserIdAndApprovedTrue(Long userId);

    Long countByApprovedFalse();

    boolean existsByBookingId(Long bookingId);

    boolean existsByUserIdAndTourId(Long userId, Long tourId);

    @Query("SELECT r FROM Review r WHERE r.approved = true AND r.helpfulCount >= :minCount ORDER BY r.helpfulCount DESC")
    List<Review> findMostHelpfulReviews(@Param("minCount") Integer minCount);

    @Query("SELECT r FROM Review r WHERE r.approved = true AND r.helpfulCount >= :minCount ORDER BY r.helpfulCount DESC")
    Page<Review> findMostHelpfulReviews(@Param("minCount") Integer minCount, Pageable pageable);

}
