package com.travelapp.config.tours;

import com.travelapp.dto.tours.TourPriceDto;
import com.travelapp.models.tours.TourPrice;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TourPriceMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(TourPrice.class, TourPriceDto.class)
                .addMapping(src -> src.getTour() != null ? src.getTour().getId() : null, TourPriceDto::setTourId)
                .addMappings(mapping -> {
                    mapping.skip(TourPriceDto::setTourId);
                });

        modelMapper.createTypeMap(TourPriceDto.class, TourPrice.class)
                .addMappings(mapping -> {
                    mapping.skip(TourPrice::setId);
                    mapping.skip(TourPrice::setTour);
                });
    }
}
