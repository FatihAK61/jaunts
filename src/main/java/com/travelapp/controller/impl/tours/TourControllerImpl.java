package com.travelapp.controller.impl.tours;

import com.travelapp.controller.tours.ITourController;
import com.travelapp.dto.tours.TourDto;
import com.travelapp.services.tours.ITourService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/tour")
@RequiredArgsConstructor
public class TourControllerImpl implements ITourController {

    private final ITourService tourService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<TourDto> createTour(@RequestBody @Valid TourDto tourDto) {
        return ResponseEntity.ok(tourService.createTour(tourDto));
    }

    @Override
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<TourDto> updateTour(@PathVariable(name = "id") Long id, @RequestBody TourDto tourDto) {
        return ResponseEntity.ok(tourService.updateTour(id, tourDto));
    }

    @Override
    public ResponseEntity<Set<TourDto>> getAllTours() {
        return ResponseEntity.ok(Set.of());
    }

    @Override
    public ResponseEntity<TourDto> getTourById(Long id) {
        return null;
    }

    @Override
    public void deleteTour(Long id) {

    }
}
