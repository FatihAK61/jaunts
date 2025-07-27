package com.travelapp.services.impl.tours.mappers;

import com.travelapp.dto.tours.TourScheduleDto;
import com.travelapp.models.tours.TourSchedule;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourScheduleMapperService {

    private final ModelMapper modelMapper;

    public TourScheduleDto toDto(TourSchedule tourSchedule) {
        if (tourSchedule == null) {
            return null;
        }

        TourScheduleDto dto = modelMapper.map(tourSchedule, TourScheduleDto.class);

        if (tourSchedule.getTour() != null) {
            dto.setTourId(tourSchedule.getTour().getId());
        }

        return dto;
    }

    public TourSchedule toEntity(TourScheduleDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, TourSchedule.class);
    }

    public List<TourScheduleDto> toDtoList(List<TourSchedule> tourSchedules) {
        if (tourSchedules == null) {
            return null;
        }

        return tourSchedules.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<TourScheduleDto> toDtoSet(Set<TourSchedule> tourSchedules) {
        if (tourSchedules == null) {
            return null;
        }

        return tourSchedules.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<TourSchedule> toEntityList(List<TourScheduleDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public Set<TourSchedule> toEntitySet(Set<TourScheduleDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }

    public void updateEntityFromDto(TourScheduleDto dto, TourSchedule existingTourSchedule) {
        if (dto == null || existingTourSchedule == null) {
            return;
        }

        modelMapper.map(dto, existingTourSchedule);
    }
}
