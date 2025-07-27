package com.travelapp.dto.tours;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travelapp.dto.hotels.HotelDto;
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
public class DestinationDto {

    private Long id;

    private Boolean active;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer popularity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private String bestTimeToVisit;
    private String city;
    private String climate;
    private String country;
    private String currency;
    private String description;
    private String imageUrl;
    private String language;
    private String name;
    private String region;
    private String timezone;

    private Set<String> images = new HashSet<>();

    private Set<TourDto> tours = new HashSet<>();

    private Set<HotelDto> hotels = new HashSet<>();
}
