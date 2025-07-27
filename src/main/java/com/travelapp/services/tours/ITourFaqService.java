package com.travelapp.services.tours;

import com.travelapp.dto.tours.TourFaqDto;

import java.util.Set;

public interface ITourFaqService {

    TourFaqDto createTourFaq(TourFaqDto tourFaqDto);

    TourFaqDto updateTourFaq(Long id, TourFaqDto tourFaqDto);

    Set<TourFaqDto> getAllTourFaqs();

    TourFaqDto getTourFaqById(Long id);

    void deleteTourFaq(Long id);
}
