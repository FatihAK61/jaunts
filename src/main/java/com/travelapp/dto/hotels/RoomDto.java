package com.travelapp.dto.hotels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travelapp.helper.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomDto {

    private Long id;
    
    private Integer capacity;
    private BigDecimal pricePerNight;

    private Boolean active;

    private RoomType roomType;

    private String roomNumber;
    private String description;

    private Long hotelId;

    private Set<String> images = new HashSet<>();
}
