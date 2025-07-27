package com.travelapp.controller.impl.users;

import com.travelapp.controller.users.IPermissionController;
import com.travelapp.dto.users.PermissionDto;
import com.travelapp.services.users.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/permission")
@RequiredArgsConstructor
public class PermissionControllerImpl implements IPermissionController {

    private final IPermissionService permissionService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<PermissionDto> createPermission(@RequestBody PermissionDto permissionDto) {
        PermissionDto createdPermission = permissionService.createPermission(permissionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPermission);
    }

    @Override
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<PermissionDto> updatePermission(@PathVariable(name = "id") Long id, @RequestBody PermissionDto permissionDto) {
        PermissionDto updatedPermission = permissionService.updatePermission(id, permissionDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPermission);
    }

    @Override
    @GetMapping(path = "/list")
    public ResponseEntity<List<PermissionDto>> getAllPermissions() {
        return ResponseEntity.status(HttpStatus.OK).body(permissionService.getAllPermissions());
    }

    @Override
    public Page<PermissionDto> getAllPermissions(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<PermissionDto> getPermissionOptById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<PermissionDto> getPermissionByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<PermissionDto> getPermissionsByResource(String resource) {
        return List.of();
    }

    @Override
    public List<PermissionDto> getPermissionsByAction(String action) {
        return List.of();
    }

    @Override
    public List<PermissionDto> getPermissionsByResourceAndAction(String resource, String action) {
        return List.of();
    }

    @Override
    public PermissionDto getPermissionById(Long id) {
        return null;
    }

    @Override
    public void deletePermission(Long id) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }

    @Override
    public long countPermissions() {
        return 0;
    }
}
