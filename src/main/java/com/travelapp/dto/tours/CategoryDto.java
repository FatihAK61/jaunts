package com.travelapp.dto.tours;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDto {

    private Long id;

    private Boolean active;

    private String description;
    private String iconUrl;
    private String name;

    private Long parentId;

    private List<CategoryDto> children;

    private Set<TourDto> tours = new HashSet<>();
}
