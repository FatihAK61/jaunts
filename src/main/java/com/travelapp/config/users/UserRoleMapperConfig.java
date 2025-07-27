package com.travelapp.config.users;

import com.travelapp.dto.users.UserRoleDto;
import com.travelapp.models.users.UserRole;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserRoleMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(UserRole.class, UserRoleDto.class)
                .addMapping(src -> src.getUser() != null ? src.getUser().getId() : null, UserRoleDto::setUserId)
                .addMapping(src -> src.getRole() != null ? src.getRole().getId() : null, UserRoleDto::setRoleId)
                .addMappings(mapping -> {
                    mapping.skip(UserRoleDto::setUserId);
                    mapping.skip(UserRoleDto::setRoleId);
                });

        modelMapper.createTypeMap(UserRoleDto.class, UserRole.class)
                .addMappings(mapping -> {
                    mapping.skip(UserRole::setId);
                    mapping.skip(UserRole::setUser);
                    mapping.skip(UserRole::setRole);
                    mapping.skip(UserRole::setAssignedAt);
                });
    }
}
