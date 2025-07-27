package com.travelapp.services.impl.users;

import com.travelapp.dto.users.UserRoleDto;
import com.travelapp.helper.errorhandler.ResourceNotFoundException;
import com.travelapp.models.users.Role;
import com.travelapp.models.users.UserBase;
import com.travelapp.models.users.UserRole;
import com.travelapp.repository.users.RoleRepository;
import com.travelapp.repository.users.UserBaseRepository;
import com.travelapp.repository.users.UserRoleRepository;
import com.travelapp.services.impl.users.mappers.UserRoleMapperService;
import com.travelapp.services.users.IUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements IUserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserBaseRepository userBaseRepository;
    private final RoleRepository roleRepository;
    private final UserRoleMapperService userRoleMapperService;

    @Override
    @Transactional
    public UserRoleDto createUserRole(UserRoleDto userRoleDto) {
        UserBase userBase = userBaseRepository.findUserById(userRoleDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found.", userRoleDto.getUserId().toString()));

        Role role = roleRepository.findRoleById(userRoleDto.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role Not Found.", userRoleDto.getRoleId().toString()));

        UserRole userRole = userRoleMapperService.toEntity(userRoleDto);
        userRole.setUser(userBase);
        userRole.setRole(role);
        UserRole savedUserRole = userRoleRepository.save(userRole);

        return userRoleMapperService.toDto(savedUserRole);
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
