package com.travelapp.services.impl.configurations.mappers;

import com.travelapp.dto.configurations.SearchLogDto;
import com.travelapp.models.configurations.SearchLog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchLogMapperService {

    private final ModelMapper modelMapper;

    public SearchLogDto toDto(SearchLog searchLog) {
        if (searchLog == null) {
            return null;
        }

        SearchLogDto dto = modelMapper.map(searchLog, SearchLogDto.class);

        if (searchLog.getUser() != null) {
            dto.setUserId(searchLog.getUser().getId());
        }

        return dto;
    }

    public SearchLog toEntity(SearchLogDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, SearchLog.class);
    }

    public List<SearchLogDto> toDtoList(List<SearchLog> searchLogs) {
        if (searchLogs == null) {
            return null;
        }

        return searchLogs.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<SearchLogDto> toDtoSet(Set<SearchLog> searchLogs) {
        if (searchLogs == null) {
            return null;
        }

        return searchLogs.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<SearchLog> toEntityList(List<SearchLogDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(SearchLogDto dto, SearchLog existingSearchLog) {
        if (dto == null || existingSearchLog == null) {
            return;
        }

        modelMapper.map(dto, existingSearchLog);
    }
}
