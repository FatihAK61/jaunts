package com.travelapp.config.hotels;

import com.travelapp.dto.hotels.RoomDto;
import com.travelapp.models.hotels.Room;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RoomMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(Room.class, RoomDto.class)
                .addMapping(src -> src.getHotel().getId(), RoomDto::setHotelId);

        modelMapper.createTypeMap(RoomDto.class, Room.class)
                .addMappings(mapping -> {
                    mapping.skip(Room::setId);
                    mapping.skip(Room::setHotel);
                });
    }
}
