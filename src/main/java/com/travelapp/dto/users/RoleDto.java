package com.travelapp.dto.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travelapp.helper.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto {

    private Long id;

    private RoleName name;
    private String description;

    private Set<UserRoleDto> userRoles = new HashSet<>();
    private Set<PermissionDto> permissions = new HashSet<>();
}
