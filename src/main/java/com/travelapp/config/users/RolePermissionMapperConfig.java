package com.travelapp.config.users;

import com.travelapp.dto.users.RolePermissionDto;
import com.travelapp.models.users.RolePermission;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RolePermissionMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(RolePermission.class, RolePermissionDto.class)
                .addMappings(mapping -> {
                    mapping.map(src -> src.getRole().getId(), RolePermissionDto::setRoleId);
                    mapping.map(src -> src.getPermission().getId(), RolePermissionDto::setPermissionId);
                });

        modelMapper.createTypeMap(RolePermissionDto.class, RolePermission.class)
                .addMappings(mapping -> {
                    mapping.skip(RolePermission::setId);
                    mapping.skip(RolePermission::setRole);
                    mapping.skip(RolePermission::setPermission);
                });
    }
}
