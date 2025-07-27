package com.travelapp.controller.tours;

import com.travelapp.dto.tours.TourDto;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface ITourController {

    ResponseEntity<TourDto> createTour(TourDto tourDto);

    ResponseEntity<TourDto> updateTour(Long id, TourDto tourDto);

    ResponseEntity<Set<TourDto>> getAllTours();

    ResponseEntity<TourDto> getTourById(Long id);

    void deleteTour(Long id);
}
