package com.travelapp.controller.users;

import com.travelapp.dto.users.UserRoleDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface IUserRoleController {

    ResponseEntity<UserRoleDto> createUserRole(@Valid @NotNull UserRoleDto userRoleDto);

    UserRoleDto updateUserRole(Long id, UserRoleDto userRoleDto);

    Set<UserRoleDto> getAllUserRoles();

    UserRoleDto getUserRoleById(Long id);

    void deleteUserRole(Long id);
}
