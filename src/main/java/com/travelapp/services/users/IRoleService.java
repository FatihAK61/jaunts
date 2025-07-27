package com.travelapp.services.users;

import com.travelapp.dto.users.RoleDto;
import com.travelapp.helper.enums.RoleName;
import com.travelapp.models.users.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    RoleDto createRole(RoleDto roleDto);

    RoleDto updateRole(Long id, RoleDto roleDto);

    List<RoleDto> getAllRoles();

    Page<RoleDto> getAllRoles(Pageable pageable);

    Optional<RoleDto> getRoleById(Long id);

    Optional<RoleDto> getRoleByName(RoleName name);

    List<RoleDto> getRolesByKeyword(String keyword);

    List<RoleDto> getRolesByNames(List<RoleName> names);

    List<RoleDto> getRolesByPermissionId(Long permissionId);

    List<RoleDto> getRolesByPermissionName(String permissionName);

    List<RoleDto> getActiveRolesByUserId(Long userId);

    Role getEntityById(Long id);

    void deleteRole(Long id);

    boolean existsByName(RoleName name);

    long countRoles();

    long countActiveUsersByRoleId(Long roleId);

    boolean existsById(Long id);
}
