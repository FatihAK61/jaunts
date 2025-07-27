package com.travelapp.services.tours;

import com.travelapp.dto.tours.TourScheduleDto;

import java.util.Set;

public interface ITourScheduleService {

    TourScheduleDto createTour(TourScheduleDto tourScheduleDto);

    TourScheduleDto updateTourSchedule(Long id, TourScheduleDto tourScheduleDto);

    Set<TourScheduleDto> getAllTourSchedules();

    TourScheduleDto getTourScheduleById(Long id);

    void deleteTourSchedule(Long id);
}
