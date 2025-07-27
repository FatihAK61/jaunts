package com.travelapp.config.users;

import com.travelapp.dto.users.RoleDto;
import com.travelapp.models.users.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RoleMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(Role.class, RoleDto.class)
                .addMappings(mapping -> {
                });

        modelMapper.createTypeMap(RoleDto.class, Role.class)
                .addMappings(mapping -> {
                    mapping.skip(Role::setId);
                    mapping.skip(Role::setUserRoles);
                    mapping.skip(Role::setRolePermissions);
                });
    }
}
