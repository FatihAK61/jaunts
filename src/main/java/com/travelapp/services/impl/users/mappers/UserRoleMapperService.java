package com.travelapp.services.impl.users.mappers;

import com.travelapp.dto.users.UserRoleDto;
import com.travelapp.models.users.UserRole;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRoleMapperService {

    private final ModelMapper modelMapper;

    public UserRoleDto toDto(UserRole userRole) {
        if (userRole == null) {
            return null;
        }

        UserRoleDto dto = modelMapper.map(userRole, UserRoleDto.class);

        if (userRole.getUser() != null) {
            dto.setUserId(userRole.getUser().getId());
        }

        if (userRole.getRole() != null) {
            dto.setRoleId(userRole.getRole().getId());
        }

        return dto;
    }

    public UserRole toEntity(UserRoleDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, UserRole.class);
    }

    public List<UserRoleDto> toDtoList(List<UserRole> userRoles) {
        if (userRoles == null) {
            return null;
        }

        return userRoles.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<UserRoleDto> toDtoSet(Set<UserRole> userRoles) {
        if (userRoles == null) {
            return null;
        }

        return userRoles.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<UserRole> toEntityList(List<UserRoleDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public Set<UserRole> toEntitySet(Set<UserRoleDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }

    public void updateEntityFromDto(UserRoleDto dto, UserRole existingUserRole) {
        if (dto == null || existingUserRole == null) {
            return;
        }

        modelMapper.map(dto, existingUserRole);
    }
}
