package com.travelapp.services.impl.tours.mappers;

import com.travelapp.dto.tours.TourPriceDto;
import com.travelapp.models.tours.TourPrice;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourPriceMapperService {

    private final ModelMapper modelMapper;

    public TourPriceDto toDto(TourPrice tourPrice) {
        if (tourPrice == null) {
            return null;
        }

        TourPriceDto dto = modelMapper.map(tourPrice, TourPriceDto.class);

        if (tourPrice.getTour() != null) {
            dto.setTourId(tourPrice.getTour().getId());
        }

        return dto;
    }

    public TourPrice toEntity(TourPriceDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, TourPrice.class);
    }

    public List<TourPriceDto> toDtoList(List<TourPrice> tourPrices) {
        if (tourPrices == null) {
            return null;
        }

        return tourPrices.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<TourPriceDto> toDtoSet(Set<TourPrice> tourPrices) {
        if (tourPrices == null) {
            return null;
        }

        return tourPrices.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<TourPrice> toEntityList(List<TourPriceDto> tourPriceDtos) {
        if (tourPriceDtos == null) {
            return null;
        }

        return tourPriceDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(TourPriceDto dto, TourPrice existingTourPrice) {
        if (dto == null || existingTourPrice == null) {
            return;
        }

        modelMapper.map(dto, existingTourPrice);
    }
}
