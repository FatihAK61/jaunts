package com.travelapp.controller.impl.users;

import com.travelapp.controller.users.IRoleController;
import com.travelapp.dto.users.RoleDto;
import com.travelapp.helper.enums.RoleName;
import com.travelapp.services.users.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleControllerImpl implements IRoleController {

    private final IRoleService roleService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        RoleDto createdRole = roleService.createRole(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    @Override
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable(name = "id") Long id, @RequestBody RoleDto roleDto) {
        RoleDto updatedRole = roleService.updateRole(id, roleDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedRole);
    }

    @Override
    @GetMapping(path = "/list")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<RoleDto> roles = roleService.getAllRoles();
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    @Override
    @GetMapping("/paginated")
    public Page<RoleDto> getAllRoles(Pageable pageable) {
        return roleService.getAllRoles(pageable);
    }

    @Override
    @GetMapping(path = "/get/{id}")
    public ResponseEntity<Optional<RoleDto>> getRoleById(@PathVariable(name = "id") Long id) {
        Optional<RoleDto> role = roleService.getRoleById(id);
        return ResponseEntity.status(HttpStatus.OK).body(role);
    }

    @Override
    @GetMapping(path = "/get-by-name/{name}")
    public ResponseEntity<Optional<RoleDto>> getRoleByName(@PathVariable(name = "name") RoleName name) {
        Optional<RoleDto> role = roleService.getRoleByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(role);
    }

    @Override
    @GetMapping(path = "/get-by-keyword")
    public ResponseEntity<List<RoleDto>> getRolesByKeyword(@RequestParam String keyword) {
        List<RoleDto> roles = roleService.getRolesByKeyword(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    @Override
    @PostMapping(path = "/by-names")
    public ResponseEntity<List<RoleDto>> getRolesByNames(@RequestBody List<RoleName> names) {
        List<RoleDto> roles = roleService.getRolesByNames(names);
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    @Override
    @GetMapping("/by-permission/{permissionId}")
    public ResponseEntity<List<RoleDto>> getRolesByPermissionId(@PathVariable(name = "permissionId") Long permissionId) {
        List<RoleDto> roles = roleService.getRolesByPermissionId(permissionId);
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    @Override
    @GetMapping("/by-permission-name/{permissionName}")
    public ResponseEntity<List<RoleDto>> getRolesByPermissionName(@PathVariable(name = "permissionName") String permissionName) {
        List<RoleDto> roles = roleService.getRolesByPermissionName(permissionName);
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    @Override
    @GetMapping("/active-by-user/{userId}")
    public ResponseEntity<List<RoleDto>> getActiveRolesByUserId(@PathVariable(name = "userId") Long userId) {
        List<RoleDto> roles = roleService.getActiveRolesByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    @Override
    @DeleteMapping(path = "/delete/{id}")
    public void deleteRole(@PathVariable(name = "id") Long id) {
        roleService.deleteRole(id);
    }

    @Override
    @GetMapping("/exists-by-name/{name}")
    public boolean existsByName(@PathVariable(name = "name") RoleName name) {
        return roleService.existsByName(name);
    }

    @Override
    @GetMapping("/count")
    public long countRoles() {
        return roleService.countRoles();
    }

    @Override
    @GetMapping("/count-active-users/{roleId}")
    public long countActiveUsersByRoleId(@PathVariable(name = "roleId") Long roleId) {
        return roleService.countActiveUsersByRoleId(roleId);
    }

    @Override
    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable(name = "id") Long id) {
        return roleService.existsById(id);
    }
}
