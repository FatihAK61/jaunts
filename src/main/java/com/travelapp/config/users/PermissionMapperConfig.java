package com.travelapp.config.users;

import com.travelapp.dto.users.PermissionDto;
import com.travelapp.models.users.Permission;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PermissionMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(Permission.class, PermissionDto.class)
                .addMappings(mapping -> {
                });

        modelMapper.createTypeMap(PermissionDto.class, Permission.class)
                .addMappings(mapping -> {
                    mapping.skip(Permission::setId);
                    mapping.skip(Permission::setRolePermissions);
                });
    }
}
