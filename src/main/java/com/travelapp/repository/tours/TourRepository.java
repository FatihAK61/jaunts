package com.travelapp.repository.tours;

import com.travelapp.helper.enums.DifficultyLevel;
import com.travelapp.helper.enums.TourType;
import com.travelapp.models.tours.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long>, JpaSpecificationExecutor<Tour> {

    @Query("SELECT t FROM Tour t WHERE t.id = :id")
    Optional<Tour> findTourById(Long id);

    @Query("SELECT d FROM Tour d " +
            "LEFT JOIN FETCH d.category " +
            "LEFT JOIN FETCH d.destination " +
            "LEFT JOIN FETCH d.images " +
            "WHERE d.id = :id")
    Optional<Tour> getTourWithAll(Long id);

    @Query("SELECT t FROM Tour t WHERE t.active = true")
    Set<Tour> findByActiveTrue();

    Optional<Tour> findBySlug(String slug);

    Optional<Tour> findBySlugAndActiveTrue(String slug);

    Page<Tour> findByActiveTrue(Pageable pageable);
    
    List<Tour> findByCategoryIdAndActiveTrue(Long categoryId);

    Page<Tour> findByCategoryIdAndActiveTrue(Long categoryId, Pageable pageable);

    List<Tour> findByDestinationIdAndActiveTrue(Long destinationId);

    Page<Tour> findByDestinationIdAndActiveTrue(Long destinationId, Pageable pageable);

    List<Tour> findByFeaturedTrueAndActiveTrue();

    Page<Tour> findByFeaturedTrueAndActiveTrue(Pageable pageable);

    List<Tour> findByTitleContainingIgnoreCaseAndActiveTrue(String title);

    @Query("SELECT t FROM Tour t WHERE " +
            "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.highlights) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "t.active = true")
    List<Tour> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT t FROM Tour t WHERE " +
            "(LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.highlights) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "t.active = true")
    Page<Tour> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    List<Tour> findByBasePriceBetweenAndActiveTrue(BigDecimal minPrice, BigDecimal maxPrice);

    Page<Tour> findByBasePriceBetweenAndActiveTrue(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    List<Tour> findByBasePriceLessThanEqualAndActiveTrue(BigDecimal maxPrice);

    List<Tour> findByBasePriceGreaterThanEqualAndActiveTrue(BigDecimal minPrice);

    List<Tour> findByDurationAndActiveTrue(Integer duration);

    List<Tour> findByDurationBetweenAndActiveTrue(Integer minDuration, Integer maxDuration);

    List<Tour> findByDurationLessThanEqualAndActiveTrue(Integer maxDuration);

    List<Tour> findByDifficultyLevelAndActiveTrue(DifficultyLevel difficultyLevel);

    List<Tour> findByTourTypeAndActiveTrue(TourType tourType);

    List<Tour> findByDifficultyLevelAndTourTypeAndActiveTrue(DifficultyLevel difficultyLevel, TourType tourType);

    List<Tour> findByRatingGreaterThanEqualAndActiveTrue(BigDecimal minRating);

    @Query("SELECT t FROM Tour t WHERE t.rating >= :minRating AND t.reviewCount >= :minReviewCount AND t.active = true ORDER BY t.rating DESC")
    List<Tour> findHighRatedTours(@Param("minRating") BigDecimal minRating, @Param("minReviewCount") Integer minReviewCount);

    List<Tour> findByMaxGroupSizeGreaterThanEqualAndActiveTrue(Integer minGroupSize);

    List<Tour> findByMaxGroupSizeLessThanEqualAndActiveTrue(Integer maxGroupSize);

    List<Tour> findByMinAgeLessThanEqualAndMaxAgeGreaterThanEqualAndActiveTrue(Integer age, Integer age2);

    @Query("SELECT t FROM Tour t WHERE " +
            "(:age >= t.minAge) AND " +
            "(t.maxAge IS NULL OR :age <= t.maxAge) AND " +
            "t.active = true")
    List<Tour> findToursForAge(@Param("age") Integer age);

    @Query("SELECT t FROM Tour t WHERE t.active = true ORDER BY t.viewCount DESC")
    List<Tour> findMostPopularTours(Pageable pageable);

    @Query("SELECT t FROM Tour t WHERE t.active = true ORDER BY t.reviewCount DESC")
    List<Tour> findMostReviewedTours(Pageable pageable);

    @Query("SELECT t FROM Tour t WHERE t.active = true ORDER BY t.createdAt DESC")
    List<Tour> findRecentlyAddedTours(Pageable pageable);

    @Query("SELECT DISTINCT t FROM Tour t " +
            "LEFT JOIN t.destination d " +
            "LEFT JOIN t.category c " +
            "WHERE (:categoryId IS NULL OR c.id = :categoryId) " +
            "AND (:destinationId IS NULL OR d.id = :destinationId) " +
            "AND (:minPrice IS NULL OR t.basePrice >= :minPrice) " +
            "AND (:maxPrice IS NULL OR t.basePrice <= :maxPrice) " +
            "AND (:minDuration IS NULL OR t.duration >= :minDuration) " +
            "AND (:maxDuration IS NULL OR t.duration <= :maxDuration) " +
            "AND (:difficultyLevel IS NULL OR t.difficultyLevel = :difficultyLevel) " +
            "AND (:tourType IS NULL OR t.tourType = :tourType) " +
            "AND (:minRating IS NULL OR t.rating >= :minRating) " +
            "AND (:maxGroupSize IS NULL OR t.maxGroupSize >= :maxGroupSize) " +
            "AND t.active = true")
    Page<Tour> findToursWithFilters(
            @Param("categoryId") Long categoryId,
            @Param("destinationId") Long destinationId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minDuration") Integer minDuration,
            @Param("maxDuration") Integer maxDuration,
            @Param("difficultyLevel") DifficultyLevel difficultyLevel,
            @Param("tourType") TourType tourType,
            @Param("minRating") BigDecimal minRating,
            @Param("maxGroupSize") Integer maxGroupSize,
            Pageable pageable
    );

    @Query("SELECT COUNT(t) FROM Tour t WHERE t.active = true")
    Long countActiveTours();

    @Query("SELECT COUNT(t) FROM Tour t WHERE t.category.id = :categoryId AND t.active = true")
    Long countToursByCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT COUNT(t) FROM Tour t WHERE t.destination.id = :destinationId AND t.active = true")
    Long countToursByDestination(@Param("destinationId") Long destinationId);

    @Query("SELECT AVG(t.basePrice) FROM Tour t WHERE t.active = true")
    BigDecimal getAveragePrice();

    @Query("SELECT MIN(t.basePrice) FROM Tour t WHERE t.active = true")
    BigDecimal getMinPrice();

    @Query("SELECT MAX(t.basePrice) FROM Tour t WHERE t.active = true")
    BigDecimal getMaxPrice();

    @Query("SELECT t FROM Tour t WHERE t.discountedPrice IS NOT NULL AND t.discountedPrice < t.basePrice AND t.active = true")
    List<Tour> findDiscountedTours();

    @Query("SELECT t FROM Tour t WHERE t.discountedPrice IS NOT NULL AND t.discountedPrice < t.basePrice AND t.active = true")
    Page<Tour> findDiscountedTours(Pageable pageable);

    @Query("SELECT t FROM Tour t JOIN t.languages l WHERE l = :language AND t.active = true")
    List<Tour> findToursByLanguage(@Param("language") com.travelapp.helper.enums.Language language);

    boolean existsBySlug(String slug);

    boolean existsBySlugAndIdNot(String slug, Long id);

    @Modifying
    @Query("UPDATE Tour t SET t.viewCount = t.viewCount + 1 WHERE t.id = :id")
    void incrementViewCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Tour t SET t.rating = :rating, t.reviewCount = :reviewCount WHERE t.id = :id")
    void updateRatingAndReviewCount(@Param("id") Long id, @Param("rating") BigDecimal rating, @Param("reviewCount") Integer reviewCount);
}
