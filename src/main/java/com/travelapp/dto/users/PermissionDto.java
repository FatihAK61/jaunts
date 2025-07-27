package com.travelapp.dto.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionDto {

    private Long id;

    private String name;
    private String description;
    private String resource;
    private String action;

    private Set<RolePermissionDto> rolePermissions = new HashSet<>();
}
