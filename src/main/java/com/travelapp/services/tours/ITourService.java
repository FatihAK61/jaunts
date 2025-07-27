package com.travelapp.services.tours;

import com.travelapp.dto.tours.TourDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ITourService {

    TourDto createTour(TourDto tourDto);

    TourDto updateTour(Long id, TourDto tourDto);

    Set<TourDto> getAllTours();

    TourDto getTourById(Long id);

    TourDto getTourBySlug(String slug);

    Page<TourDto> getTours(Pageable pageable);

    List<TourDto> getToursByCategory(Long categoryId);

    List<TourDto> getToursByDestination(Long destinationId);

    List<TourDto> getFeaturedTours();

    List<TourDto> searchTours(String keyword);

    void deleteTour(Long id);
}
