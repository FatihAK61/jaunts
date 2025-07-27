package com.travelapp.services.impl.preferences.mappers;

import com.travelapp.dto.preferences.UserPreferenceDto;
import com.travelapp.models.preferences.UserPreference;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPreferenceMapperService {

    private final ModelMapper modelMapper;

    public UserPreferenceDto toDto(UserPreference userPreference) {
        if (userPreference == null)
            return null;

        UserPreferenceDto dto = modelMapper.map(userPreference, UserPreferenceDto.class);

        if (userPreference.getUser() != null)
            dto.setUserId(userPreference.getUser().getId());

        return dto;
    }

    public UserPreference toEntity(UserPreferenceDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, UserPreference.class);
    }

    public List<UserPreferenceDto> toDtoList(List<UserPreference> userPreferences) {
        if (userPreferences == null) {
            return null;
        }

        return userPreferences.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<UserPreferenceDto> toDtoSet(Set<UserPreference> userPreferences) {
        if (userPreferences == null) {
            return null;
        }

        return userPreferences.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<UserPreference> toEntityList(List<UserPreferenceDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(UserPreferenceDto dto, UserPreference existingUserPreference) {
        if (dto == null || existingUserPreference == null) {
            return;
        }

        modelMapper.map(dto, existingUserPreference);
    }
}
