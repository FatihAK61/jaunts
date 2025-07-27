package com.travelapp.controller.users;

import com.travelapp.dto.users.RoleDto;
import com.travelapp.helper.enums.RoleName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IRoleController {

    ResponseEntity<RoleDto> createRole(@Valid @NotNull RoleDto roleDto);

    ResponseEntity<RoleDto> updateRole(@NotNull Long id, @Valid @NotNull RoleDto roleDto);

    ResponseEntity<List<RoleDto>> getAllRoles();

    Page<RoleDto> getAllRoles(Pageable pageable);

    ResponseEntity<Optional<RoleDto>> getRoleById(Long id);

    ResponseEntity<Optional<RoleDto>> getRoleByName(RoleName name);

    ResponseEntity<List<RoleDto>> getRolesByKeyword(String keyword);

    ResponseEntity<List<RoleDto>> getRolesByNames(List<RoleName> names);

    ResponseEntity<List<RoleDto>> getRolesByPermissionId(Long permissionId);

    ResponseEntity<List<RoleDto>> getRolesByPermissionName(String permissionName);

    ResponseEntity<List<RoleDto>> getActiveRolesByUserId(Long userId);

    void deleteRole(Long id);

    boolean existsByName(RoleName name);

    long countRoles();

    long countActiveUsersByRoleId(Long roleId);

    boolean existsById(Long id);
}
