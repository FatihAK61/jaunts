package com.travelapp.config.configurations;

import com.travelapp.dto.configurations.SystemSettingDto;
import com.travelapp.models.configurations.SystemSetting;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SystemSettingMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(SystemSetting.class, SystemSettingDto.class);

        modelMapper.createTypeMap(SystemSettingDto.class, SystemSetting.class)
                .addMappings(mapping -> {
                    mapping.skip(SystemSetting::setId);
                    mapping.skip(SystemSetting::setUpdatedAt);
                });
    }
}
