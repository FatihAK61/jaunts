package com.travelapp.services.tours;

import com.travelapp.dto.tours.TourPriceDto;

import java.util.Set;

public interface ITourPriceService {

    TourPriceDto createTourPrice(TourPriceDto tourPriceDto);

    TourPriceDto updateTourPrice(Long id, TourPriceDto tourPriceDto);

    Set<TourPriceDto> getAllTourPrices();

    TourPriceDto getTourPriceById(Long id);

    void deleteTourPrice(Long id);
}
