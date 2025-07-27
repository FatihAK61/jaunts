package com.travelapp.services.impl.tours.mappers;

import com.travelapp.dto.tours.TourDto;
import com.travelapp.models.tours.Tour;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourMapperService {

    private final ModelMapper modelMapper;

    public TourDto toDto(Tour tour) {
        if (tour == null) {
            return null;
        }

        TourDto dto = modelMapper.map(tour, TourDto.class);

        // Manuel mapping gereken alanlar
        if (tour.getDestination() != null) {
            dto.setDestinationId(tour.getDestination().getId());
        }

        if (tour.getCategory() != null) {
            dto.setCategoryId(tour.getCategory().getId());
        }

        return dto;
    }

    public Tour toEntity(TourDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, Tour.class);
    }

    public List<TourDto> toDtoList(List<Tour> tours) {
        if (tours == null) {
            return null;
        }

        return tours.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<TourDto> toDtoSet(Set<Tour> tours) {
        if (tours == null) {
            return null;
        }

        return tours.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<Tour> toEntityList(List<TourDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(TourDto dto, Tour existingTour) {
        if (dto == null || existingTour == null) {
            return;
        }

        // ModelMapper ile temel alanları güncelle
        modelMapper.map(dto, existingTour);

        // ID'yi koru
        // Destination ve Category entity'leri service katmanında ayrıca set edilmeli
    }
}
