package com.travelapp.config.configurations;

import com.travelapp.dto.configurations.PageViewDto;
import com.travelapp.models.configurations.PageView;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PageViewMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(PageView.class, PageViewDto.class)
                .addMapping(src -> src.getUser().getId(), PageViewDto::setUserId);

        modelMapper.createTypeMap(PageViewDto.class, PageView.class)
                .addMappings(mapping -> {
                    mapping.skip(PageView::setId);
                    mapping.skip(PageView::setUser);
                });
    }
}
