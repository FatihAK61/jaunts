package com.travelapp.services.users;

import com.travelapp.dto.users.PermissionDto;
import com.travelapp.models.users.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPermissionService {

    PermissionDto createPermission(PermissionDto permissionDto);

    PermissionDto updatePermission(Long id, PermissionDto permissionDto);

    List<PermissionDto> getAllPermissions();

    Page<PermissionDto> getAllPermissions(Pageable pageable);

    Optional<PermissionDto> getPermissionOptById(Long id);

    Optional<PermissionDto> getPermissionByName(String name);

    List<PermissionDto> getPermissionsByResource(String resource);

    List<PermissionDto> getPermissionsByAction(String action);

    List<PermissionDto> getPermissionsByResourceAndAction(String resource, String action);

    PermissionDto getPermissionById(Long id);

    Permission getEntityById(Long id);

    void deletePermission(Long id);

    boolean existsById(Long id);

    boolean existsByName(String name);

    long countPermissions();
}
