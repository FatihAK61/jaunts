package com.travelapp.config.preferences;

import com.travelapp.dto.preferences.UserPreferenceDto;
import com.travelapp.models.preferences.UserPreference;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserPreferenceMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(UserPreference.class, UserPreferenceDto.class)
                .addMapping(src -> src.getUser().getId(), UserPreferenceDto::setUserId)
                .addMappings(mapping -> {
                    mapping.skip(UserPreferenceDto::setUserId);
                });

        modelMapper.createTypeMap(UserPreferenceDto.class, UserPreference.class)
                .addMappings(mapping -> {
                    mapping.skip(UserPreference::setId);
                    mapping.skip(UserPreference::setUser);
                    mapping.skip(UserPreference::setCreatedAt);
                    mapping.skip(UserPreference::setUpdatedAt);
                });
    }
}
