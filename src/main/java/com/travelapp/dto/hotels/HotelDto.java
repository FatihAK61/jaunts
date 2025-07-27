package com.travelapp.dto.hotels;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travelapp.helper.enums.HotelAmenity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelDto {

    private Long id;

    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer starRating;
    private BigDecimal rating;
    private Integer reviewCount;

    private Boolean active;

    private String name;
    private String description;
    private String address;
    private String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private Long destinationId;

    private Set<HotelAmenity> amenities = new HashSet<>();
    private Set<String> images = new HashSet<>();
    private Set<RoomDto> rooms = new HashSet<>();
}
