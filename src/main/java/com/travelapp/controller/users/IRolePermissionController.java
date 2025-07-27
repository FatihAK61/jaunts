package com.travelapp.controller.users;

import com.travelapp.dto.users.RolePermissionDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRolePermissionController {

    ResponseEntity<RolePermissionDto> createRolePermission(@Valid @NotNull RolePermissionDto rolePermissionDto);

    ResponseEntity<RolePermissionDto> updateRolePermission(Long id, @Valid @NotNull RolePermissionDto rolePermissionDto);

    ResponseEntity<List<RolePermissionDto>> getAllRolePermissions();

    ResponseEntity<List<RolePermissionDto>> getRolePermissionsByRoleId(Long roleId);

    ResponseEntity<List<RolePermissionDto>> getRolePermissionsByPermissionId(Long permissionId);

    ResponseEntity<RolePermissionDto> getRolePermissionByRoleIdAndPermissionId(Long roleId, Long permissionId);

    ResponseEntity<Boolean> existsByRoleIdAndPermissionId(Long roleId, Long permissionId);

    ResponseEntity<Void> deleteRolePermission(Long id);

    ResponseEntity<Void> deleteRolePermissionsByRoleId(Long roleId);

    ResponseEntity<Void> deleteRolePermissionsByPermissionId(Long permissionId);

    ResponseEntity<Void> deleteRolePermissionByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
