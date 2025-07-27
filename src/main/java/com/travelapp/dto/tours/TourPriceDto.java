package com.travelapp.dto.tours;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travelapp.helper.enums.PriceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TourPriceDto {
    private Long id;

    private BigDecimal price;

    private Boolean active;

    private String description;
    private PriceType priceType;

    private Long tourId;
}
