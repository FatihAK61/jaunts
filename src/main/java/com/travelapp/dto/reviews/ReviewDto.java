package com.travelapp.dto.reviews;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewDto {

    private Long id;

    private Integer cleanlinessRating;
    private Integer helpfulCount;
    private Integer rating;
    private Integer serviceRating;
    private Integer valueRating;

    private Boolean featured;
    private Boolean approved;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private String comment;
    private String moderatorNotes;
    private String title;

    private Long userId;
    private Long bookingId;
    private Long tourId;

    private Set<String> images = new HashSet<>();
}
