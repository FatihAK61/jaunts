package com.travelapp.services.users;

import com.travelapp.dto.users.RolePermissionDto;
import com.travelapp.models.users.RolePermission;

import java.util.List;

public interface IRolePermissionService {

    RolePermissionDto createRolePermission(RolePermissionDto rolePermissionDto);

    RolePermissionDto updateRolePermission(Long id, RolePermissionDto rolePermissionDto);

    RolePermissionDto getRolePermissionById(Long id);

    List<RolePermissionDto> getAllRolePermissions();

    List<RolePermissionDto> findByRoleId(Long roleId);

    List<RolePermissionDto> findByPermissionId(Long permissionId);

    RolePermissionDto findByRoleIdAndPermissionId(Long roleId, Long permissionId);

    boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);

    void deleteRolePermission(Long id);

    void deleteByRoleId(Long roleId);

    void deleteByPermissionId(Long permissionId);

    void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId);

    RolePermission findEntityById(Long id);
}
