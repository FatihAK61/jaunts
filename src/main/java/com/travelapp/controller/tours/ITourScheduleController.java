package com.travelapp.controller.tours;

import com.travelapp.dto.tours.TourScheduleDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface ITourScheduleController {

    ResponseEntity<TourScheduleDto> createTour(@Valid @NotNull TourScheduleDto tourScheduleDto);

    TourScheduleDto updateTourSchedule(Long id, TourScheduleDto tourScheduleDto);

    Set<TourScheduleDto> getAllTourSchedules();

    TourScheduleDto getTourScheduleById(Long id);

    void deleteTourSchedule(Long id);
}
