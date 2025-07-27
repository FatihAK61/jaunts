package com.travelapp.config.tours;

import com.travelapp.dto.tours.CategoryDto;
import com.travelapp.models.tours.Category;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CategoryMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        // Category to CategoryDto mapping
        modelMapper.createTypeMap(Category.class, CategoryDto.class)
                .addMapping(src -> src.getParent() != null ? src.getParent().getId() : null, CategoryDto::setParentId)
                .addMappings(mapping -> {
                    mapping.skip(CategoryDto::setTours);
                });

        // CategoryRequestDto to Category mapping
        modelMapper.createTypeMap(CategoryDto.class, Category.class)
                .addMapping(CategoryDto::getParentId, Category::setParent)
                .addMappings(mapping -> {
                    mapping.skip(Category::setId);
                    mapping.skip(Category::setTours);
                });
    }
}
