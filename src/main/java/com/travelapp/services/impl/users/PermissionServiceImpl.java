package com.travelapp.services.impl.users;

import com.travelapp.dto.users.PermissionDto;
import com.travelapp.models.users.Permission;
import com.travelapp.repository.users.PermissionRepository;
import com.travelapp.services.impl.users.mappers.PermissionMapperService;
import com.travelapp.services.users.IPermissionService;
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
public class PermissionServiceImpl implements IPermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapperService permissionMapperService;

    @Override
    public PermissionDto createPermission(PermissionDto permissionDto) {
        if (permissionRepository.existsByName(permissionDto.getName())) {
            throw new EntityExistsException("Permission with name '" + permissionDto.getName() + "' already exists");
        }

        Permission permission = permissionMapperService.toEntity(permissionDto);
        Permission savedPermission = permissionRepository.save(permission);
        return permissionMapperService.toDto(savedPermission);
    }

    @Override
    public PermissionDto updatePermission(Long id, PermissionDto permissionDto) {
        Permission existingPermission = permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));

        if (!existingPermission.getName().equals(permissionDto.getName()) &&
                permissionRepository.existsByName(permissionDto.getName())) {
            throw new EntityExistsException("Permission with name '" + permissionDto.getName() + "' already exists.");
        }

        permissionMapperService.updateEntityFromDto(permissionDto, existingPermission);
        Permission updatedPermission = permissionRepository.save(existingPermission);

        return permissionMapperService.toDto(updatedPermission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDto> getAllPermissions() {
        return permissionMapperService.toDtoList(permissionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermissionDto> getAllPermissions(Pageable pageable) {
        return permissionRepository.findAll(pageable)
                .map(permissionMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PermissionDto> getPermissionOptById(Long id) {
        return permissionRepository.findById(id)
                .map(permissionMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PermissionDto> getPermissionByName(String name) {
        return permissionRepository.findByName(name)
                .map(permissionMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDto> getPermissionsByResource(String resource) {
        return permissionMapperService.toDtoList(permissionRepository.findByResource(resource));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDto> getPermissionsByAction(String action) {
        return permissionMapperService.toDtoList(permissionRepository.findByAction(action));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDto> getPermissionsByResourceAndAction(String resource, String action) {
        return permissionMapperService.toDtoList(permissionRepository.findByResourceAndAction(resource, action));
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionDto getPermissionById(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new EntityNotFoundException("Permission not found with id: " + id);
        }

        return permissionRepository.findById(id)
                .map(permissionMapperService::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Permission getEntityById(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new IllegalArgumentException("Permission not found with id: " + id);
        }

        Optional<Permission> permission = permissionRepository.findById(id);
        if (permission.isEmpty()) {
            throw new IllegalArgumentException("Permission not found with id: " + id);
        }

        return permission.get();
    }

    @Override
    public void deletePermission(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new IllegalArgumentException("Permission not found with id: " + id);
        }

        permissionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return permissionRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return permissionRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public long countPermissions() {
        return permissionRepository.count();
    }
}
