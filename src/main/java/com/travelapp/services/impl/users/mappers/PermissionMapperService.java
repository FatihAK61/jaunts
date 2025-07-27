package com.travelapp.services.impl.users.mappers;

import com.travelapp.dto.users.PermissionDto;
import com.travelapp.models.users.Permission;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionMapperService {

    private final ModelMapper modelMapper;

    public PermissionDto toDto(Permission permission) {
        if (permission == null) {
            return null;
        }

        return modelMapper.map(permission, PermissionDto.class);
    }

    public Permission toEntity(PermissionDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, Permission.class);
    }

    public List<PermissionDto> toDtoList(List<Permission> permissions) {
        if (permissions == null) {
            return null;
        }

        return permissions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<PermissionDto> toDtoSet(Set<Permission> permissions) {
        if (permissions == null) {
            return null;
        }

        return permissions.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<Permission> toEntityList(List<PermissionDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public Set<Permission> toEntitySet(Set<PermissionDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }

    public void updateEntityFromDto(PermissionDto dto, Permission existingPermission) {
        if (dto == null || existingPermission == null) {
            return;
        }

        if (dto.getName() != null) {
            existingPermission.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            existingPermission.setDescription(dto.getDescription());
        }
        if (dto.getResource() != null) {
            existingPermission.setResource(dto.getResource());
        }
        if (dto.getAction() != null) {
            existingPermission.setAction(dto.getAction());
        }
    }
}
