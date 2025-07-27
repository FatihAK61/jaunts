package com.travelapp.services.users;

import com.travelapp.dto.users.UserRoleDto;

import java.util.Set;

public interface IUserRoleService {

    UserRoleDto createUserRole(UserRoleDto userRoleDto);

    UserRoleDto updateUserRole(Long id, UserRoleDto userRoleDto);

    Set<UserRoleDto> getAllUserRoles();

    UserRoleDto getUserRoleById(Long id);

    void deleteUserRole(Long id);
}
