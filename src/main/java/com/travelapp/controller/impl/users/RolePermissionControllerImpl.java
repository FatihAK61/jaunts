package com.travelapp.controller.impl.users;

import com.travelapp.controller.users.IRolePermissionController;
import com.travelapp.dto.users.RolePermissionDto;
import com.travelapp.services.users.IRolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role-permission")
@RequiredArgsConstructor
public class RolePermissionControllerImpl implements IRolePermissionController {

    private final IRolePermissionService rolePermissionService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<RolePermissionDto> createRolePermission(@RequestBody RolePermissionDto rolePermissionDto) {
        RolePermissionDto createdRolePermission = rolePermissionService.createRolePermission(rolePermissionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRolePermission);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<RolePermissionDto> updateRolePermission(@PathVariable(name = "id") Long id,
                                                                  @RequestBody RolePermissionDto rolePermissionDto) {
        RolePermissionDto updatedRolePermission = rolePermissionService.updateRolePermission(id, rolePermissionDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedRolePermission);
    }

    @Override
    @GetMapping("/list")
    public ResponseEntity<List<RolePermissionDto>> getAllRolePermissions() {
        List<RolePermissionDto> rolePermissions = rolePermissionService.getAllRolePermissions();
        return ResponseEntity.status(HttpStatus.OK).body(rolePermissions);
    }

    @Override
    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<RolePermissionDto>> getRolePermissionsByRoleId(@PathVariable(name = "roleId") Long roleId) {
        List<RolePermissionDto> rolePermissions = rolePermissionService.findByRoleId(roleId);
        return ResponseEntity.status(HttpStatus.OK).body(rolePermissions);
    }

    @Override
    @GetMapping("/permission/{permissionId}")
    public ResponseEntity<List<RolePermissionDto>> getRolePermissionsByPermissionId(@PathVariable(name = "permissionId") Long permissionId) {
        List<RolePermissionDto> rolePermissions = rolePermissionService.findByPermissionId(permissionId);
        return ResponseEntity.status(HttpStatus.OK).body(rolePermissions);
    }

    @Override
    @GetMapping("/role/{roleId}/permission/{permissionId}")
    public ResponseEntity<RolePermissionDto> getRolePermissionByRoleIdAndPermissionId(@PathVariable(name = "roleId") Long roleId,
                                                                                      @PathVariable(name = "permissionId") Long permissionId) {
        RolePermissionDto rolePermission = rolePermissionService.findByRoleIdAndPermissionId(roleId, permissionId);

        if (rolePermission != null) {
            return ResponseEntity.status(HttpStatus.OK).body(rolePermission);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    @GetMapping("/exists/role/{roleId}/permission/{permissionId}")
    public ResponseEntity<Boolean> existsByRoleIdAndPermissionId(@PathVariable(name = "roleId") Long roleId,
                                                                 @PathVariable(name = "permissionId") Long permissionId) {
        boolean exists = rolePermissionService.existsByRoleIdAndPermissionId(roleId, permissionId);
        return ResponseEntity.status(HttpStatus.OK).body(exists);
    }

    @Override
    @DeleteMapping("/delete-role-permission/{id}")
    public ResponseEntity<Void> deleteRolePermission(@PathVariable(name = "id") Long id) {
        rolePermissionService.deleteRolePermission(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @DeleteMapping("/role/{roleId}")
    public ResponseEntity<Void> deleteRolePermissionsByRoleId(@PathVariable(name = "roleId") Long roleId) {
        rolePermissionService.deleteByRoleId(roleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @DeleteMapping("/permission/{permissionId}")
    public ResponseEntity<Void> deleteRolePermissionsByPermissionId(@PathVariable(name = "permissionId") Long permissionId) {
        rolePermissionService.deleteByPermissionId(permissionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @DeleteMapping("/role/{roleId}/permission/{permissionId}")
    public ResponseEntity<Void> deleteRolePermissionByRoleIdAndPermissionId(@PathVariable(name = "roleId") Long roleId,
                                                                            @PathVariable(name = "permissionId") Long permissionId) {
        rolePermissionService.deleteByRoleIdAndPermissionId(roleId, permissionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
