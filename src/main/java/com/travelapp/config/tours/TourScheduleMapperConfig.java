package com.travelapp.config.tours;

import com.travelapp.dto.tours.TourScheduleDto;
import com.travelapp.models.tours.TourSchedule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TourScheduleMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(TourSchedule.class, TourScheduleDto.class)
                .addMapping(src -> src.getTour() != null ? src.getTour().getId() : null, TourScheduleDto::setTourId)
                .addMappings(mapping -> {
                    mapping.skip(TourScheduleDto::setTourId);
                });

        modelMapper.createTypeMap(TourScheduleDto.class, TourSchedule.class)
                .addMappings(mapping -> {
                    mapping.skip(TourSchedule::setId);
                    mapping.skip(TourSchedule::setTour);
                    mapping.skip(TourSchedule::setBookings);
                });
    }
}
