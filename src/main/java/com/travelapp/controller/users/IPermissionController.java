package com.travelapp.controller.users;

import com.travelapp.dto.users.PermissionDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IPermissionController {

    ResponseEntity<PermissionDto> createPermission(@Valid @NotNull PermissionDto permissionDto);

    ResponseEntity<PermissionDto> updatePermission(@NotNull Long id, @Valid @NotNull PermissionDto permissionDto);

    ResponseEntity<List<PermissionDto>> getAllPermissions();

    Page<PermissionDto> getAllPermissions(Pageable pageable);

    Optional<PermissionDto> getPermissionOptById(Long id);

    Optional<PermissionDto> getPermissionByName(String name);

    List<PermissionDto> getPermissionsByResource(String resource);

    List<PermissionDto> getPermissionsByAction(String action);

    List<PermissionDto> getPermissionsByResourceAndAction(String resource, String action);

    PermissionDto getPermissionById(Long id);

    void deletePermission(Long id);

    boolean existsById(Long id);

    boolean existsByName(String name);

    long countPermissions();
}
