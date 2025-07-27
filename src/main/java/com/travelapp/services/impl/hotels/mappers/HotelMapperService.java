package com.travelapp.services.impl.hotels.mappers;

import com.travelapp.dto.hotels.HotelDto;
import com.travelapp.models.hotels.Hotel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelMapperService {

    private final ModelMapper modelMapper;

    public HotelDto toDto(Hotel hotel) {
        if (hotel == null) {
            return null;
        }

        HotelDto dto = modelMapper.map(hotel, HotelDto.class);

        if (hotel.getDestination() != null) {
            dto.setDestinationId(hotel.getDestination().getId());
        }

        return dto;
    }

    public Hotel toEntity(HotelDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, Hotel.class);
    }

    public List<HotelDto> toDtoList(List<Hotel> hotels) {
        if (hotels == null) {
            return null;
        }

        return hotels.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<HotelDto> toDtoSet(Set<Hotel> hotels) {
        if (hotels == null) {
            return null;
        }

        return hotels.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<Hotel> toEntityList(List<HotelDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(HotelDto dto, Hotel existingHotel) {
        if (dto == null || existingHotel == null) {
            return;
        }

        modelMapper.map(dto, existingHotel);
    }
}
