package com.travelapp.services.impl.users.mappers;

import com.travelapp.dto.users.RolePermissionDto;
import com.travelapp.models.users.Permission;
import com.travelapp.models.users.Role;
import com.travelapp.models.users.RolePermission;
import com.travelapp.services.users.IPermissionService;
import com.travelapp.services.users.IRoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolePermissionMapperService {

    private final ModelMapper modelMapper;
    private final IRoleService roleService;
    private final IPermissionService permissionService;

    public RolePermissionDto toDto(RolePermission rolePermission) {
        if (rolePermission == null) {
            return null;
        }

        return modelMapper.map(rolePermission, RolePermissionDto.class);
    }

    public RolePermission toEntity(RolePermissionDto dto) {
        if (dto == null) {
            return null;
        }

        RolePermission rolePermission = modelMapper.map(dto, RolePermission.class);

        if (dto.getRoleId() != null) {
            Role role = roleService.getEntityById(dto.getRoleId());
            rolePermission.setRole(role);
        }

        if (dto.getPermissionId() != null) {
            Permission permission = permissionService.getEntityById(dto.getPermissionId());
            rolePermission.setPermission(permission);
        }

        return rolePermission;
    }

    public List<RolePermissionDto> toDtoList(List<RolePermission> rolePermissions) {
        if (rolePermissions == null) {
            return null;
        }

        return rolePermissions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<RolePermissionDto> toDtoSet(Set<RolePermission> rolePermissions) {
        if (rolePermissions == null) {
            return null;
        }

        return rolePermissions.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<RolePermission> toEntityList(List<RolePermissionDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public Set<RolePermission> toEntitySet(Set<RolePermissionDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }

    public void updateEntityFromDto(RolePermissionDto dto, RolePermission existingRolePermission) {
        if (dto == null || existingRolePermission == null) {
            return;
        }

        if (dto.getRoleId() != null) {
            Role role = roleService.getEntityById(dto.getRoleId());
            existingRolePermission.setRole(role);
        }

        if (dto.getPermissionId() != null) {
            Permission permission = permissionService.getEntityById(dto.getPermissionId());
            existingRolePermission.setPermission(permission);
        }
    }
}
