package com.travelapp.services.hotels;

import com.travelapp.dto.hotels.HotelDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IHotelService {

    HotelDto create(HotelDto hotelDto);

    Optional<HotelDto> findById(Long id);

    HotelDto update(Long id, HotelDto hotelDto);

    void delete(Long id);

    void softDelete(Long id);

    List<HotelDto> findAll();

    Page<HotelDto> findAll(Pageable pageable);

    List<HotelDto> findAllActive();

    Page<HotelDto> findAllActive(Pageable pageable);

    List<HotelDto> findByDestination(Long destinationId);

    Page<HotelDto> findByDestination(Long destinationId, Pageable pageable);

    List<HotelDto> searchByName(String name);

    Page<HotelDto> searchByName(String name, Pageable pageable);

    List<HotelDto> findByMinStarRating(Integer minStarRating);

    Page<HotelDto> findByMinStarRating(Integer minStarRating, Pageable pageable);

    List<HotelDto> findByRatingRange(BigDecimal minRating, BigDecimal maxRating);

    Page<HotelDto> findByRatingRange(BigDecimal minRating, BigDecimal maxRating, Pageable pageable);

    Page<HotelDto> findWithFilters(Long destinationId, Integer minStarRating,
                                   BigDecimal minRating, BigDecimal maxRating,
                                   String name, Pageable pageable);

    List<HotelDto> findNearLocation(BigDecimal latitude, BigDecimal longitude, Double distanceKm);

    List<HotelDto> findTopRated(int limit);

    Long countHotelsByDestination(Long destinationId);

    HotelDto toggleActiveStatus(Long id);

    HotelDto updateRating(Long id, BigDecimal newRating, Integer reviewCount);

}
