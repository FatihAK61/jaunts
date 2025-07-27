package com.travelapp.services.impl.tours.mappers;

import com.travelapp.dto.tours.TourFaqDto;
import com.travelapp.models.tours.TourFAQ;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourFaqMapperService {

    private final ModelMapper modelMapper;

    public TourFaqDto toDto(TourFAQ tourFaq) {
        if (tourFaq == null) {
            return null;
        }

        TourFaqDto dto = modelMapper.map(tourFaq, TourFaqDto.class);

        if (tourFaq.getTour() != null) {
            dto.setTourId(tourFaq.getTour().getId());
        }

        return dto;
    }

    public TourFAQ toEntity(TourFaqDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, TourFAQ.class);
    }

    public List<TourFaqDto> toDtoList(List<TourFAQ> tourFaqs) {
        if (tourFaqs == null) {
            return null;
        }

        return tourFaqs.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<TourFaqDto> toDtoSet(Set<TourFAQ> tourFaqs) {
        if (tourFaqs == null) {
            return null;
        }

        return tourFaqs.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<TourFAQ> toEntityList(List<TourFaqDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(TourFaqDto dto, TourFAQ existingTourFaq) {
        if (dto == null || existingTourFaq == null) {
            return;
        }

        modelMapper.map(dto, existingTourFaq);
    }
}
