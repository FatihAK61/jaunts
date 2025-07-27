package com.travelapp.config.hotels;

import com.travelapp.dto.hotels.HotelDto;
import com.travelapp.models.hotels.Hotel;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HotelMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(Hotel.class, HotelDto.class)
                .addMapping(src -> src.getDestination().getId(), HotelDto::setDestinationId)
                .addMappings(mapping -> {
                    mapping.skip(HotelDto::setRooms);
                });

        modelMapper.createTypeMap(HotelDto.class, Hotel.class)
                .addMappings(mapping -> {
                    mapping.skip(Hotel::setId);
                    mapping.skip(Hotel::setDestination);
                    mapping.skip(Hotel::setRooms);
                    mapping.skip(Hotel::setCreatedAt);
                    mapping.skip(Hotel::setUpdatedAt);
                });
    }
}
