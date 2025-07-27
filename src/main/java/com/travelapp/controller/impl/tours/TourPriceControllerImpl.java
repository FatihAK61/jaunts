package com.travelapp.controller.impl.tours;

import com.travelapp.controller.tours.ITourPriceController;
import com.travelapp.dto.tours.TourPriceDto;
import com.travelapp.services.tours.ITourPriceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/tour-price")
@RequiredArgsConstructor
public class TourPriceControllerImpl implements ITourPriceController {

    private final ITourPriceService tourPriceService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<TourPriceDto> createTourPrice(@RequestBody @Valid @NotNull TourPriceDto tourPriceDto) {
        TourPriceDto createdTourPrice = tourPriceService.createTourPrice(tourPriceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTourPrice);
    }

    @Override
    public TourPriceDto updateTourPrice(Long id, TourPriceDto tourPriceDto) {
        return null;
    }

    @Override
    public Set<TourPriceDto> getAllTourPrices() {
        return Set.of();
    }

    @Override
    public TourPriceDto getTourPriceById(Long id) {
        return null;
    }

    @Override
    public void deleteTourPrice(Long id) {

    }
}
