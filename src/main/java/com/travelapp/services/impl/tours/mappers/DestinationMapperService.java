package com.travelapp.services.impl.tours.mappers;

import com.travelapp.dto.tours.DestinationDto;
import com.travelapp.models.tours.Destination;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DestinationMapperService {

    private final ModelMapper modelMapper;

    public DestinationDto toDto(Destination destination) {
        if (destination == null) {
            return null;
        }

        return modelMapper.map(destination, DestinationDto.class);
    }

    public Destination toEntity(DestinationDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, Destination.class);
    }

    public List<DestinationDto> toDtoList(List<Destination> destinations) {
        if (destinations == null) {
            return null;
        }

        return destinations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<DestinationDto> toDtoSet(Set<Destination> destinations) {
        if (destinations == null) {
            return null;
        }

        return destinations.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<Destination> toEntityList(List<DestinationDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(DestinationDto dto, Destination existingDestination) {
        if (dto == null || existingDestination == null) {
            return;
        }

        // ModelMapper ile temel alanları güncelle
        modelMapper.map(dto, existingDestination);

        // ID'yi koru
        // Tours ve Hotels collections'ları service katmanında ayrıca handle edilmeli
    }
}
