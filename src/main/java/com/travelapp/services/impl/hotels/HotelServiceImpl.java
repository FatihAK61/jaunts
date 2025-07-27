package com.travelapp.services.impl.hotels;

import com.travelapp.dto.hotels.HotelDto;
import com.travelapp.models.hotels.Hotel;
import com.travelapp.models.tours.Destination;
import com.travelapp.repository.hotels.HotelRepository;
import com.travelapp.repository.tours.DestinationRepository;
import com.travelapp.services.hotels.IHotelService;
import com.travelapp.services.impl.hotels.mappers.HotelMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelServiceImpl implements IHotelService {

    private final HotelRepository hotelRepository;
    private final DestinationRepository destinationRepository;
    private final HotelMapperService hotelMapperService;

    @Override
    public HotelDto create(HotelDto hotelDto) {
        Hotel hotel = hotelMapperService.toEntity(hotelDto);

        if (hotelDto.getDestinationId() != null) {
            Destination destination = destinationRepository.findById(hotelDto.getDestinationId())
                    .orElseThrow(() -> new RuntimeException("Destination not found with id: " + hotelDto.getDestinationId()));
            hotel.setDestination(destination);
        }

        Hotel savedHotel = hotelRepository.save(hotel);

        return hotelMapperService.toDto(savedHotel);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HotelDto> findById(Long id) {
        return hotelRepository.findByIdAndActiveTrue(id)
                .map(hotelMapperService::toDto);
    }

    @Override
    public HotelDto update(Long id, HotelDto hotelDto) {
        Hotel existingHotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));

        hotelMapperService.updateEntityFromDto(hotelDto, existingHotel);

        if (hotelDto.getDestinationId() != null &&
                !hotelDto.getDestinationId().equals(existingHotel.getDestination().getId())) {
            Destination destination = destinationRepository.findById(hotelDto.getDestinationId())
                    .orElseThrow(() -> new RuntimeException("Destination not found with id: " + hotelDto.getDestinationId()));
            existingHotel.setDestination(destination);
        }

        Hotel updatedHotel = hotelRepository.save(existingHotel);

        return hotelMapperService.toDto(updatedHotel);
    }

    @Override
    public void delete(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new RuntimeException("Hotel not found with id: " + id);
        }

        hotelRepository.deleteById(id);
    }

    @Override
    public void softDelete(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));

        hotel.setActive(false);
        hotelRepository.save(hotel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelDto> findAll() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotelMapperService.toDtoList(hotels);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HotelDto> findAll(Pageable pageable) {
        Page<Hotel> hotelPage = hotelRepository.findAll(pageable);
        return hotelPage.map(hotelMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelDto> findAllActive() {
        List<Hotel> hotels = hotelRepository.findByActiveTrue();
        return hotelMapperService.toDtoList(hotels);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HotelDto> findAllActive(Pageable pageable) {
        Page<Hotel> hotelPage = hotelRepository.findByActiveTrue(pageable);
        return hotelPage.map(hotelMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelDto> findByDestination(Long destinationId) {
        List<Hotel> hotels = hotelRepository.findByDestinationIdAndActiveTrue(destinationId);
        return hotelMapperService.toDtoList(hotels);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HotelDto> findByDestination(Long destinationId, Pageable pageable) {
        Page<Hotel> hotelPage = hotelRepository.findByDestinationIdAndActiveTrue(destinationId, pageable);
        return hotelPage.map(hotelMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelDto> searchByName(String name) {
        List<Hotel> hotels = hotelRepository.findByNameContainingIgnoreCaseAndActiveTrue(name);
        return hotelMapperService.toDtoList(hotels);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HotelDto> searchByName(String name, Pageable pageable) {
        Page<Hotel> hotelPage = hotelRepository.findByNameContainingIgnoreCaseAndActiveTrue(name, pageable);
        return hotelPage.map(hotelMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelDto> findByMinStarRating(Integer minStarRating) {
        List<Hotel> hotels = hotelRepository.findByStarRatingGreaterThanEqualAndActiveTrue(minStarRating);
        return hotelMapperService.toDtoList(hotels);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HotelDto> findByMinStarRating(Integer minStarRating, Pageable pageable) {
        Page<Hotel> hotelPage = hotelRepository.findByStarRatingGreaterThanEqualAndActiveTrue(minStarRating, pageable);
        return hotelPage.map(hotelMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelDto> findByRatingRange(BigDecimal minRating, BigDecimal maxRating) {
        List<Hotel> hotels = hotelRepository.findByRatingBetweenAndActiveTrue(minRating, maxRating);
        return hotelMapperService.toDtoList(hotels);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HotelDto> findByRatingRange(BigDecimal minRating, BigDecimal maxRating, Pageable pageable) {
        Page<Hotel> hotelPage = hotelRepository.findByRatingBetweenAndActiveTrue(minRating, maxRating, pageable);
        return hotelPage.map(hotelMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HotelDto> findWithFilters(Long destinationId, Integer minStarRating, BigDecimal minRating, BigDecimal maxRating, String name, Pageable pageable) {
        Page<Hotel> hotelPage = hotelRepository.findHotelsWithFilters(
                destinationId, minStarRating, minRating, maxRating, name, pageable);
        return hotelPage.map(hotelMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelDto> findNearLocation(BigDecimal latitude, BigDecimal longitude, Double distanceKm) {
        List<Hotel> hotels = hotelRepository.findHotelsNearLocation(latitude, longitude, distanceKm);
        return hotelMapperService.toDtoList(hotels);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelDto> findTopRated(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Hotel> hotels = hotelRepository.findTopRatedHotels(pageable);
        return hotelMapperService.toDtoList(hotels);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countHotelsByDestination(Long destinationId) {
        return hotelRepository.countHotelsByDestinationId(destinationId);
    }

    @Override
    public HotelDto toggleActiveStatus(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));

        hotel.setActive(!hotel.getActive());
        Hotel updatedHotel = hotelRepository.save(hotel);

        return hotelMapperService.toDto(updatedHotel);
    }

    @Override
    public HotelDto updateRating(Long id, BigDecimal newRating, Integer reviewCount) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));

        hotel.setRating(newRating);
        hotel.setReviewCount(reviewCount);

        Hotel updatedHotel = hotelRepository.save(hotel);

        return hotelMapperService.toDto(updatedHotel);
    }
}
