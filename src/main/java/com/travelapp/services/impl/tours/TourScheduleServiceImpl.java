package com.travelapp.services.impl.tours;

import com.travelapp.dto.tours.TourScheduleDto;
import com.travelapp.helper.errorhandler.ResourceNotFoundException;
import com.travelapp.models.tours.Tour;
import com.travelapp.models.tours.TourSchedule;
import com.travelapp.repository.tours.TourRepository;
import com.travelapp.repository.tours.TourScheduleRepository;
import com.travelapp.services.impl.tours.mappers.TourScheduleMapperService;
import com.travelapp.services.tours.ITourScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TourScheduleServiceImpl implements ITourScheduleService {

    private final TourScheduleRepository tourScheduleRepository;
    private final TourRepository tourRepository;
    private final TourScheduleMapperService tourScheduleMapperService;

    @Override
    @Transactional
    public TourScheduleDto createTour(TourScheduleDto tourScheduleDto) {
        Tour tour = tourRepository.findTourById(tourScheduleDto.getTourId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour Not Found.", tourScheduleDto.getTourId().toString()));

        TourSchedule tourSchedule = tourScheduleMapperService.toEntity(tourScheduleDto);
        tourSchedule.setTour(tour);
        TourSchedule savedTourSchedule = tourScheduleRepository.save(tourSchedule);

        return tourScheduleMapperService.toDto(savedTourSchedule);
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
