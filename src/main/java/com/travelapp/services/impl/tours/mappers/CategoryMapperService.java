package com.travelapp.services.impl.tours.mappers;

import com.travelapp.dto.tours.CategoryDto;
import com.travelapp.models.tours.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryMapperService {

    private final ModelMapper modelMapper;

    public CategoryDto toDto(Category category) {
        if (category == null) {
            return null;
        }

        CategoryDto dto = modelMapper.map(category, CategoryDto.class);

        if (category.getParent() != null) {
            dto.setParentId(category.getParent().getId());
        }

        return dto;
    }

    public Category toEntity(CategoryDto dto) {
        if (dto == null) {
            return null;
        }
        
        return modelMapper.map(dto, Category.class);
    }
}
