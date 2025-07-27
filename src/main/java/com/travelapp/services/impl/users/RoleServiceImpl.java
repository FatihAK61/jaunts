package com.travelapp.services.impl.users;

import com.travelapp.dto.users.RoleDto;
import com.travelapp.helper.enums.RoleName;
import com.travelapp.models.users.Role;
import com.travelapp.repository.users.RoleRepository;
import com.travelapp.services.impl.users.mappers.RoleMapperService;
import com.travelapp.services.users.IRoleService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements IRoleService {

    private final RoleRepository roleRepository;
    private final RoleMapperService roleMapperService;

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        if (roleRepository.existsByName(roleDto.getName())) {
            throw new EntityExistsException("Role with name '" + roleDto.getName() + "' already exists");
        }

        Role role = roleMapperService.toEntity(roleDto);
        Role savedRole = roleRepository.save(role);

        return roleMapperService.toDto(savedRole);
    }

    @Override
    public RoleDto updateRole(Long id, RoleDto roleDto) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));

        if (!existingRole.getName().equals(roleDto.getName()) &&
                roleRepository.existsByName(roleDto.getName())) {
            throw new EntityExistsException("Role with name '" + roleDto.getName() + "' already exists");
        }

        roleMapperService.updateEntityFromDto(roleDto, existingRole);
        Role updatedRole = roleRepository.save(existingRole);

        return roleMapperService.toDto(updatedRole);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getAllRoles() {
        return roleMapperService.toDtoList(roleRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleDto> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable)
                .map(roleMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleDto> getRoleById(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException("Role not found with id: " + id);
        }

        return roleRepository.findById(id)
                .map(roleMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getEntityById(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException("Role not found with id: " + id);
        }
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw new EntityNotFoundException("Role not found with id: " + id);
        }

        return role.get();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleDto> getRoleByName(RoleName name) {
        if (!roleRepository.existsByName(name)) {
            throw new EntityNotFoundException("Role not found with name: " + name);
        }

        return roleRepository.findByName(name)
                .map(roleMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getRolesByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword cannot be null or blank.");
        }

        return roleMapperService.toDtoList(roleRepository.findByKeyword(keyword));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getRolesByNames(List<RoleName> names) {
        if (names == null || names.isEmpty()) {
            throw new IllegalArgumentException("List of names cannot be null or empty.");
        }

        return roleMapperService.toDtoList(roleRepository.findByNameIn(names));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getRolesByPermissionId(Long permissionId) {
        return roleMapperService.toDtoList(roleRepository.findByPermissionId(permissionId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getRolesByPermissionName(String permissionName) {
        return roleMapperService.toDtoList(roleRepository.findByPermissionName(permissionName));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getActiveRolesByUserId(Long userId) {
        return roleMapperService.toDtoList(roleRepository.findActiveRolesByUserId(userId));
    }

    @Override
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException("Role not found with id: " + id);
        }

        long activeUsersCount = roleRepository.countActiveUsersByRoleId(id);
        if (activeUsersCount > 0) {
            throw new IllegalStateException("Cannot delete role with id: " + id +
                    ". Role has " + activeUsersCount + " active users assigned.");
        }

        roleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(RoleName name) {
        return roleRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public long countRoles() {
        return roleRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long countActiveUsersByRoleId(Long roleId) {
        return roleRepository.countActiveUsersByRoleId(roleId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return roleRepository.existsById(id);
    }
}
