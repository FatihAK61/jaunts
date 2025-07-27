package com.travelapp.services.impl.configurations.mappers;

import com.travelapp.dto.configurations.SystemSettingDto;
import com.travelapp.models.configurations.SystemSetting;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SystemSettingMapperService {

    private final ModelMapper modelMapper;

    public SystemSettingDto toDto(SystemSetting systemSetting) {
        if (systemSetting == null) {
            return null;
        }

        return modelMapper.map(systemSetting, SystemSettingDto.class);
    }

    public SystemSetting toEntity(SystemSettingDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, SystemSetting.class);
    }

    public List<SystemSettingDto> toDtoList(List<SystemSetting> systemSettings) {
        if (systemSettings == null) {
            return null;
        }

        return systemSettings.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<SystemSettingDto> toDtoSet(Set<SystemSetting> systemSettings) {
        if (systemSettings == null) {
            return null;
        }

        return systemSettings.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<SystemSetting> toEntityList(List<SystemSettingDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(SystemSettingDto dto, SystemSetting existingSystemSetting) {
        if (dto == null || existingSystemSetting == null) {
            return;
        }

        modelMapper.map(dto, existingSystemSetting);
    }
}
