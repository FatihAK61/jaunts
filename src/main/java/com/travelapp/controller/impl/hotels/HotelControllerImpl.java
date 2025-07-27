package com.travelapp.controller.impl.hotels;

import com.travelapp.controller.hotels.IHotelController;
import com.travelapp.dto.hotels.HotelDto;
import com.travelapp.services.hotels.IHotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
public class HotelControllerImpl implements IHotelController {

    private final IHotelService hotelService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<HotelDto> createHotel(@RequestBody @Valid HotelDto hotelDto) {
        HotelDto createdHotel = hotelService.create(hotelDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
    }

    @Override
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable(name = "id") Long id, @RequestBody @Valid HotelDto hotelDto) {
        HotelDto updatedHotel = hotelService.update(id, hotelDto);
        return ResponseEntity.ok(updatedHotel);
    }

    @Override
    @GetMapping(path = "/all")
    public ResponseEntity<List<HotelDto>> getAllHotels() {
        List<HotelDto> hotels = hotelService.findAllActive();
        return ResponseEntity.ok(hotels);
    }

    @Override
    @GetMapping(path = "/all/paginated")
    public ResponseEntity<Page<HotelDto>> getAllHotelsWithPagination(Pageable pageable) {
        Page<HotelDto> hotels = hotelService.findAllActive(pageable);
        return ResponseEntity.ok(hotels);
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable(name = "id") Long id) {
        return hotelService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping(path = "/destination/{destinationId}")
    public ResponseEntity<List<HotelDto>> getHotelsByDestination(@PathVariable(name = "destinationId") Long destinationId) {
        List<HotelDto> hotels = hotelService.findByDestination(destinationId);
        return ResponseEntity.ok(hotels);
    }

    @Override
    @GetMapping(path = "/destination/{destinationId}/paginated")
    public ResponseEntity<Page<HotelDto>> getHotelsByDestinationWithPagination(
            @PathVariable(name = "destinationId") Long destinationId, Pageable pageable) {
        Page<HotelDto> hotels = hotelService.findByDestination(destinationId, pageable);
        return ResponseEntity.ok(hotels);
    }

    @Override
    @GetMapping(path = "/search")
    public ResponseEntity<List<HotelDto>> searchHotelsByName(@RequestParam(name = "name") String name) {
        List<HotelDto> hotels = hotelService.searchByName(name);
        return ResponseEntity.ok(hotels);
    }

    @Override
    @GetMapping(path = "/search/paginated")
    public ResponseEntity<Page<HotelDto>> searchHotelsByNameWithPagination(
            @RequestParam(name = "name") String name, Pageable pageable) {
        Page<HotelDto> hotels = hotelService.searchByName(name, pageable);
        return ResponseEntity.ok(hotels);
    }

    @Override
    @GetMapping(path = "/star-rating/{minStarRating}")
    public ResponseEntity<List<HotelDto>> getHotelsByMinStarRating(@PathVariable(name = "minStarRating") Integer minStarRating) {
        List<HotelDto> hotels = hotelService.findByMinStarRating(minStarRating);
        return ResponseEntity.ok(hotels);
    }

    @Override
    @GetMapping(path = "/rating-range")
    public ResponseEntity<List<HotelDto>> getHotelsByRatingRange(
            @RequestParam(name = "minRating") BigDecimal minRating,
            @RequestParam(name = "maxRating") BigDecimal maxRating) {
        List<HotelDto> hotels = hotelService.findByRatingRange(minRating, maxRating);
        return ResponseEntity.ok(hotels);
    }

    @Override
    @GetMapping(path = "/filter")
    public ResponseEntity<Page<HotelDto>> getHotelsWithFilters(
            @RequestParam(name = "destinationId", required = false) Long destinationId,
            @RequestParam(name = "minStarRating", required = false) Integer minStarRating,
            @RequestParam(name = "minRating", required = false) BigDecimal minRating,
            @RequestParam(name = "maxRating", required = false) BigDecimal maxRating,
            @RequestParam(name = "name", required = false) String name,
            Pageable pageable) {
        Page<HotelDto> hotels = hotelService.findWithFilters(destinationId, minStarRating, minRating, maxRating, name, pageable);
        return ResponseEntity.ok(hotels);
    }

    @Override
    @GetMapping(path = "/near-location")
    public ResponseEntity<List<HotelDto>> getHotelsNearLocation(
            @RequestParam(name = "latitude") BigDecimal latitude,
            @RequestParam(name = "longitude") BigDecimal longitude,
            @RequestParam(name = "distance") Double distanceKm) {
        List<HotelDto> hotels = hotelService.findNearLocation(latitude, longitude, distanceKm);
        return ResponseEntity.ok(hotels);
    }

    @Override
    @GetMapping(path = "/top-rated")
    public ResponseEntity<List<HotelDto>> getTopRatedHotels(@RequestParam(name = "limit", defaultValue = "10") Integer limit) {
        List<HotelDto> hotels = hotelService.findTopRated(limit);
        return ResponseEntity.ok(hotels);
    }

    @Override
    @GetMapping(path = "/count/destination/{destinationId}")
    public ResponseEntity<Long> getHotelCountByDestination(@PathVariable(name = "destinationId") Long destinationId) {
        Long count = hotelService.countHotelsByDestination(destinationId);
        return ResponseEntity.ok(count);
    }

    @Override
    @PatchMapping(path = "/toggle-status/{id}")
    public ResponseEntity<HotelDto> toggleHotelActiveStatus(@PathVariable(name = "id") Long id) {
        HotelDto hotel = hotelService.toggleActiveStatus(id);
        return ResponseEntity.ok(hotel);
    }

    @Override
    @PatchMapping(path = "/update-rating/{id}")
    public ResponseEntity<HotelDto> updateHotelRating(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "rating") BigDecimal newRating,
            @RequestParam(name = "reviewCount") Integer reviewCount) {
        HotelDto hotel = hotelService.updateRating(id, newRating, reviewCount);
        return ResponseEntity.ok(hotel);
    }

    @Override
    @DeleteMapping(path = "/delete/{id}")
    public void deleteHotel(@PathVariable(name = "id") Long id) {
        hotelService.delete(id);
    }

    @Override
    @DeleteMapping(path = "/soft-delete/{id}")
    public ResponseEntity<Void> softDeleteHotel(@PathVariable(name = "id") Long id) {
        hotelService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}
