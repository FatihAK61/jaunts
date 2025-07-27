package com.travelapp.dto.tours;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travelapp.dto.book.BookingDto;
import com.travelapp.dto.reviews.ReviewDto;
import com.travelapp.dto.wishlists.WishlistDto;
import com.travelapp.helper.enums.DifficultyLevel;
import com.travelapp.helper.enums.Language;
import com.travelapp.helper.enums.TourType;
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
public class TourDto {
    private Long id;

    private BigDecimal basePrice;
    private BigDecimal discountedPrice;
    private Integer duration;
    private Integer maxAge;
    private Integer maxGroupSize;
    private Integer minAge;
    private BigDecimal rating;
    private Integer reviewCount;
    private Integer viewCount;

    private Boolean featured;
    private Boolean active;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private DifficultyLevel difficultyLevel;
    private TourType tourType;

    private String departureTime;
    private String returnTime;
    private String description;
    private String excludes;
    private String highlights;
    private String imageUrl;
    private String includes;
    private String itinerary;
    private String meetingPoint;
    private String returnInfo;
    private String slug;
    private String title;

    private Long categoryId;
    private Long destinationId;
    
    private Set<String> images = new HashSet<>();

    private Set<TourScheduleDto> schedules = new HashSet<>();
    private Set<BookingDto> bookings = new HashSet<>();
    private Set<ReviewDto> reviews = new HashSet<>();
    private Set<TourFaqDto> faqs = new HashSet<>();
    private Set<Language> languages = new HashSet<>();
    private Set<TourPriceDto> prices = new HashSet<>();
    private Set<WishlistDto> wishlists = new HashSet<>();
}
