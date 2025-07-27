package com.travelapp.config.tours;

import com.travelapp.dto.tours.TourDto;
import com.travelapp.models.tours.Tour;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TourMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(Tour.class, TourDto.class)
                .addMapping(src -> src.getDestination().getId(), TourDto::setDestinationId)
                .addMapping(src -> src.getCategory().getId(), TourDto::setCategoryId)
                .addMappings(mapping -> {
                    mapping.skip(TourDto::setBookings);
                    mapping.skip(TourDto::setReviews);
                    mapping.skip(TourDto::setWishlists);
                    mapping.skip(TourDto::setSchedules);
                    mapping.skip(TourDto::setFaqs);
                    mapping.skip(TourDto::setPrices);
                });

        modelMapper.createTypeMap(TourDto.class, Tour.class)
                .addMappings(mapping -> {

                    mapping.skip(Tour::setId);
                    mapping.skip(Tour::setDestination);
                    mapping.skip(Tour::setCategory);

                    mapping.skip(Tour::setBookings);
                    mapping.skip(Tour::setReviews);
                    mapping.skip(Tour::setWishlists);
                    mapping.skip(Tour::setSchedules);
                    mapping.skip(Tour::setFaqs);
                    mapping.skip(Tour::setPrices);
                });
    }
}
