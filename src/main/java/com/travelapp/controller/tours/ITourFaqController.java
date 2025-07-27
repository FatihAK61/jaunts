package com.travelapp.controller.tours;

import com.travelapp.dto.tours.TourFaqDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public interface ITourFaqController {

    TourFaqDto createTourFaq(@Valid @NotNull TourFaqDto tourFaqDto);

    TourFaqDto updateTourFaq(Long id, TourFaqDto tourFaqDto);

    Set<TourFaqDto> getAllTourFaqs();

    TourFaqDto getTourFaqById(Long id);

    void deleteTourFaq(Long id);
}
