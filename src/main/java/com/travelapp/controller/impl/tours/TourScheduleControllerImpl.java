package com.travelapp.controller.impl.tours;

import com.travelapp.controller.tours.ITourScheduleController;
import com.travelapp.dto.tours.TourScheduleDto;
import com.travelapp.services.tours.ITourScheduleService;
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
@RequestMapping("/api/v1/tour-schedule")
@RequiredArgsConstructor
public class TourScheduleControllerImpl implements ITourScheduleController {

    private final ITourScheduleService tourScheduleService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<TourScheduleDto> createTour(@RequestBody @Valid @NotNull TourScheduleDto tourScheduleDto) {
        TourScheduleDto scheduleDto = tourScheduleService.createTour(tourScheduleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleDto);
    }

    @Override
    public TourScheduleDto updateTourSchedule(Long id, TourScheduleDto tourScheduleDto) {
        return null;
    }

    @Override
    public Set<TourScheduleDto> getAllTourSchedules() {
        return Set.of();
    }

    @Override
    public TourScheduleDto getTourScheduleById(Long id) {
        return null;
    }

    @Override
    public void deleteTourSchedule(Long id) {

    }
}
