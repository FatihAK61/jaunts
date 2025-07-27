package com.travelapp.services.impl.users;

import com.travelapp.dto.users.RolePermissionDto;
import com.travelapp.models.users.RolePermission;
import com.travelapp.repository.users.RolePermissionRepository;
import com.travelapp.services.impl.users.mappers.RolePermissionMapperService;
import com.travelapp.services.users.IRolePermissionService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RolePermissionServiceImpl implements IRolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;
    private final RolePermissionMapperService rolePermissionMapperService;

    @Override
    public RolePermissionDto createRolePermission(RolePermissionDto rolePermissionDto) {
        if (existsByRoleIdAndPermissionId(rolePermissionDto.getRoleId(), rolePermissionDto.getPermissionId())) {
            throw new EntityExistsException("Role permission already exists for role ID: " +
                    rolePermissionDto.getRoleId() + " and permission ID: " + rolePermissionDto.getPermissionId());
        }

        RolePermission rolePermission = rolePermissionMapperService.toEntity(rolePermissionDto);
        RolePermission savedRolePermission = rolePermissionRepository.save(rolePermission);

        return rolePermissionMapperService.toDto(savedRolePermission);
    }

    @Override
    public RolePermissionDto updateRolePermission(Long id, RolePermissionDto rolePermissionDto) {
        RolePermission existingRolePermission = findEntityById(id);

        if (!existingRolePermission.getRole().getId().equals(rolePermissionDto.getRoleId()) ||
                !existingRolePermission.getPermission().getId().equals(rolePermissionDto.getPermissionId())) {

            if (existsByRoleIdAndPermissionId(rolePermissionDto.getRoleId(), rolePermissionDto.getPermissionId())) {
                throw new EntityExistsException("Role permission already exists for role ID: " +
                        rolePermissionDto.getRoleId() + " and permission ID: " + rolePermissionDto.getPermissionId());
            }
        }

        rolePermissionMapperService.updateEntityFromDto(rolePermissionDto, existingRolePermission);
        RolePermission updatedRolePermission = rolePermissionRepository.save(existingRolePermission);

        return rolePermissionMapperService.toDto(updatedRolePermission);
    }

    @Override
    @Transactional(readOnly = true)
    public RolePermissionDto getRolePermissionById(Long id) {
        RolePermission rolePermission = findEntityById(id);
        return rolePermissionMapperService.toDto(rolePermission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolePermissionDto> getAllRolePermissions() {
        List<RolePermission> rolePermissions = rolePermissionRepository.findAll();
        return rolePermissionMapperService.toDtoList(rolePermissions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolePermissionDto> findByRoleId(Long roleId) {
        List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(roleId);
        return rolePermissionMapperService.toDtoList(rolePermissions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolePermissionDto> findByPermissionId(Long permissionId) {
        List<RolePermission> rolePermissions = rolePermissionRepository.findByPermissionId(permissionId);
        return rolePermissionMapperService.toDtoList(rolePermissions);
    }

    @Override
    @Transactional(readOnly = true)
    public RolePermissionDto findByRoleIdAndPermissionId(Long roleId, Long permissionId) {
        return rolePermissionRepository.findByRoleIdAndPermissionId(roleId, permissionId)
                .map(rolePermissionMapperService::toDto)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId) {
        return rolePermissionRepository.existsByRoleIdAndPermissionId(roleId, permissionId);
    }

    @Override
    public void deleteRolePermission(Long id) {
        if (!rolePermissionRepository.existsById(id)) {
            throw new EntityNotFoundException("Role permission not found with ID: " + id);
        }

        rolePermissionRepository.deleteById(id);
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        rolePermissionRepository.deleteByRoleId(roleId);
    }

    @Override
    public void deleteByPermissionId(Long permissionId) {
        rolePermissionRepository.deleteByPermissionId(permissionId);
    }

    @Override
    public void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId) {
        rolePermissionRepository.findByRoleIdAndPermissionId(roleId, permissionId)
                .ifPresent(rolePermissionRepository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public RolePermission findEntityById(Long id) {
        return rolePermissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role permission not found with ID: " + id));
    }
}
