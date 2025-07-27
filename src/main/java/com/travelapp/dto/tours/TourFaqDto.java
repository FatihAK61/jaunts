package com.travelapp.dto.tours;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TourFaqDto {
    
    private Long id;
    private Boolean active;
    private Integer sortOrder;

    private String answer;
    private String question;

    private Long tourId;
}
