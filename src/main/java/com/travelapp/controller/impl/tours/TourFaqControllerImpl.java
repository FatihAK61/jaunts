package com.travelapp.controller.impl.tours;

import com.travelapp.controller.tours.ITourFaqController;
import com.travelapp.dto.tours.TourFaqDto;
import com.travelapp.services.tours.ITourFaqService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/tour-faq")
@RequiredArgsConstructor
public class TourFaqControllerImpl implements ITourFaqController {

    private final ITourFaqService tourFaqService;

    @Override
    @PostMapping(path = "/save")
    public TourFaqDto createTourFaq(@RequestBody @Valid @NotNull TourFaqDto tourFaqDto) {
        return tourFaqService.createTourFaq(tourFaqDto);
    }

    @Override
    public TourFaqDto updateTourFaq(Long id, TourFaqDto tourFaqDto) {
        return null;
    }

    @Override
    public Set<TourFaqDto> getAllTourFaqs() {
        return Set.of();
    }

    @Override
    public TourFaqDto getTourFaqById(Long id) {
        return null;
    }

    @Override
    public void deleteTourFaq(Long id) {

    }
}
