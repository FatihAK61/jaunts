package com.travelapp.controller.tours;

import com.travelapp.dto.tours.TourPriceDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface ITourPriceController {

    ResponseEntity<TourPriceDto> createTourPrice(@Valid @NotNull TourPriceDto tourPriceDto);

    TourPriceDto updateTourPrice(Long id, TourPriceDto tourPriceDto);

    Set<TourPriceDto> getAllTourPrices();

    TourPriceDto getTourPriceById(Long id);

    void deleteTourPrice(Long id);
}
