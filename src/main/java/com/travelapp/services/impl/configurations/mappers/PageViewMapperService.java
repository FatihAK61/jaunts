package com.travelapp.services.impl.configurations.mappers;

import com.travelapp.dto.configurations.PageViewDto;
import com.travelapp.models.configurations.PageView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PageViewMapperService {

    private final ModelMapper modelMapper;

    public PageViewDto toDto(PageView pageView) {
        if (pageView == null) {
            return null;
        }

        PageViewDto dto = modelMapper.map(pageView, PageViewDto.class);

        if (pageView.getUser() != null) {
            dto.setUserId(pageView.getUser().getId());
        }

        return dto;
    }

    public PageView toEntity(PageViewDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, PageView.class);
    }

    public List<PageViewDto> toDtoList(List<PageView> pageViews) {
        if (pageViews == null) {
            return null;
        }

        return pageViews.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<PageViewDto> toDtoSet(Set<PageView> pageViews) {
        if (pageViews == null) {
            return null;
        }

        return pageViews.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<PageView> toEntityList(List<PageViewDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(PageViewDto dto, PageView existingPageView) {
        if (dto == null || existingPageView == null) {
            return;
        }

        modelMapper.map(dto, existingPageView);
    }
}
