package com.travelapp.repository.hotels;

import com.travelapp.models.hotels.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

    List<Hotel> findByActiveTrue();

    Page<Hotel> findByActiveTrue(Pageable pageable);

    List<Hotel> findByDestinationIdAndActiveTrue(Long destinationId);

    Page<Hotel> findByDestinationIdAndActiveTrue(Long destinationId, Pageable pageable);

    List<Hotel> findByNameContainingIgnoreCaseAndActiveTrue(String name);

    Page<Hotel> findByNameContainingIgnoreCaseAndActiveTrue(String name, Pageable pageable);

    List<Hotel> findByStarRatingGreaterThanEqualAndActiveTrue(Integer starRating);

    Page<Hotel> findByStarRatingGreaterThanEqualAndActiveTrue(Integer starRating, Pageable pageable);

    List<Hotel> findByRatingBetweenAndActiveTrue(BigDecimal minRating, BigDecimal maxRating);

    Page<Hotel> findByRatingBetweenAndActiveTrue(BigDecimal minRating, BigDecimal maxRating, Pageable pageable);

    @Query("SELECT h FROM Hotel h WHERE h.active = true ORDER BY h.rating DESC")
    List<Hotel> findTopRatedHotels(Pageable pageable);

    @Query("SELECT h FROM Hotel h WHERE h.active = true AND " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(h.latitude)) * " +
            "cos(radians(h.longitude) - radians(:lon)) + sin(radians(:lat)) * " +
            "sin(radians(h.latitude)))) <= :distance ORDER BY " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(h.latitude)) * " +
            "cos(radians(h.longitude) - radians(:lon)) + sin(radians(:lat)) * " +
            "sin(radians(h.latitude))))")
    List<Hotel> findHotelsNearLocation(@Param("lat") BigDecimal latitude,
                                       @Param("lon") BigDecimal longitude,
                                       @Param("distance") Double distanceKm);

    @Query("SELECT h FROM Hotel h WHERE h.active = true AND " +
            "(:destinationId IS NULL OR h.destination.id = :destinationId) AND " +
            "(:minStarRating IS NULL OR h.starRating >= :minStarRating) AND " +
            "(:minRating IS NULL OR h.rating >= :minRating) AND " +
            "(:maxRating IS NULL OR h.rating <= :maxRating) AND " +
            "(:name IS NULL OR LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Hotel> findHotelsWithFilters(@Param("destinationId") Long destinationId,
                                      @Param("minStarRating") Integer minStarRating,
                                      @Param("minRating") BigDecimal minRating,
                                      @Param("maxRating") BigDecimal maxRating,
                                      @Param("name") String name,
                                      Pageable pageable);

    Optional<Hotel> findByIdAndActiveTrue(Long id);

    @Query("SELECT COUNT(h) FROM Hotel h WHERE h.destination.id = :destinationId AND h.active = true")
    Long countHotelsByDestinationId(@Param("destinationId") Long destinationId);

}
