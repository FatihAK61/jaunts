package com.travelapp.controller.impl.users;

import com.travelapp.controller.users.IUserRoleController;
import com.travelapp.dto.users.UserRoleDto;
import com.travelapp.services.users.IUserRoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/user-role")
@RequiredArgsConstructor
public class UserRoleControllerImpl implements IUserRoleController {

    private final IUserRoleService userRoleService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<UserRoleDto> createUserRole(@RequestBody @Valid @NotNull UserRoleDto userRoleDto) {
        UserRoleDto createdUserRole = userRoleService.createUserRole(userRoleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserRole);
    }

    @Override
    public UserRoleDto updateUserRole(Long id, UserRoleDto userRoleDto) {
        return null;
    }

    @Override
    public Set<UserRoleDto> getAllUserRoles() {
        return Set.of();
    }

    @Override
    public UserRoleDto getUserRoleById(Long id) {
        return null;
    }

    @Override
    public void deleteUserRole(Long id) {

    }
}
