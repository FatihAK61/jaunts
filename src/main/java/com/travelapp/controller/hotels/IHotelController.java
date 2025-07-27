package com.travelapp.controller.hotels;

import com.travelapp.dto.hotels.HotelDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface IHotelController {

    ResponseEntity<HotelDto> createHotel(HotelDto hotelDto);

    ResponseEntity<HotelDto> updateHotel(Long id, HotelDto hotelDto);

    ResponseEntity<List<HotelDto>> getAllHotels();

    ResponseEntity<Page<HotelDto>> getAllHotelsWithPagination(Pageable pageable);

    ResponseEntity<HotelDto> getHotelById(Long id);

    ResponseEntity<List<HotelDto>> getHotelsByDestination(Long destinationId);

    ResponseEntity<Page<HotelDto>> getHotelsByDestinationWithPagination(Long destinationId, Pageable pageable);

    ResponseEntity<List<HotelDto>> searchHotelsByName(String name);

    ResponseEntity<Page<HotelDto>> searchHotelsByNameWithPagination(String name, Pageable pageable);

    ResponseEntity<List<HotelDto>> getHotelsByMinStarRating(Integer minStarRating);

    ResponseEntity<List<HotelDto>> getHotelsByRatingRange(BigDecimal minRating, BigDecimal maxRating);

    ResponseEntity<Page<HotelDto>> getHotelsWithFilters(Long destinationId, Integer minStarRating,
                                                        BigDecimal minRating, BigDecimal maxRating,
                                                        String name, Pageable pageable);

    ResponseEntity<List<HotelDto>> getHotelsNearLocation(BigDecimal latitude, BigDecimal longitude, Double distanceKm);

    ResponseEntity<List<HotelDto>> getTopRatedHotels(Integer limit);

    ResponseEntity<Long> getHotelCountByDestination(Long destinationId);

    ResponseEntity<HotelDto> toggleHotelActiveStatus(Long id);

    ResponseEntity<HotelDto> updateHotelRating(Long id, BigDecimal newRating, Integer reviewCount);

    void deleteHotel(Long id);

    ResponseEntity<Void> softDeleteHotel(Long id);

}
