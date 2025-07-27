package com.travelapp.config.configurations;

import com.travelapp.dto.configurations.SearchLogDto;
import com.travelapp.models.configurations.SearchLog;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SearchLogMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(SearchLog.class, SearchLogDto.class)
                .addMapping(src -> src.getUser().getId(), SearchLogDto::setUserId)
                .addMappings(mapping -> {
                    mapping.skip(SearchLogDto::setUserId);
                });

        modelMapper.createTypeMap(SearchLogDto.class, SearchLog.class)
                .addMappings(mapping -> {
                    mapping.skip(SearchLog::setId);
                    mapping.skip(SearchLog::setUser);
                    mapping.skip(SearchLog::setSearchedAt);
                });
    }
}
