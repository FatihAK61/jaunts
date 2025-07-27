package com.travelapp.config.tours;

import com.travelapp.dto.tours.TourFaqDto;
import com.travelapp.models.tours.TourFAQ;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TourFaqMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        // TourFAQ to TourFaqDto mapping
        modelMapper.createTypeMap(TourFAQ.class, TourFaqDto.class)
                .addMapping(src -> src.getTour() != null ? src.getTour().getId() : null, TourFaqDto::setTourId)
                .addMappings(mapping -> {
                    mapping.skip(TourFaqDto::setTourId);
                });

        // TourFaqDto to TourFAQ mapping
        modelMapper.createTypeMap(TourFaqDto.class, TourFAQ.class)
                .addMappings(mapping -> {
                    mapping.skip(TourFAQ::setId);
                    mapping.skip(TourFAQ::setTour);
                });
    }
}
