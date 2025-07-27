package com.travelapp.services.impl.users.mappers;

import com.travelapp.dto.users.RoleDto;
import com.travelapp.models.users.Role;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleMapperService {

    private final ModelMapper modelMapper;

    public RoleDto toDto(Role role) {
        if (role == null) {
            return null;
        }

        return modelMapper.map(role, RoleDto.class);
    }

    public Role toEntity(RoleDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, Role.class);
    }

    public List<RoleDto> toDtoList(List<Role> roles) {
        if (roles == null) {
            return null;
        }

        return roles.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<RoleDto> toDtoSet(Set<Role> roles) {
        if (roles == null) {
            return null;
        }

        return roles.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<Role> toEntityList(List<RoleDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public Set<Role> toEntitySet(Set<RoleDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }

    public void updateEntityFromDto(RoleDto dto, Role existingRole) {
        if (dto == null || existingRole == null) {
            return;
        }

        if (dto.getName() != null) {
            existingRole.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            existingRole.setDescription(dto.getDescription());
        }
    }
}
