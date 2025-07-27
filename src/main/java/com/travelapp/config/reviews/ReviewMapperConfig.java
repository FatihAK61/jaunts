package com.travelapp.config.reviews;

import com.travelapp.dto.reviews.ReviewDto;
import com.travelapp.models.reviews.Review;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ReviewMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(Review.class, ReviewDto.class)
                .addMapping(src -> src.getUser().getId(), ReviewDto::setUserId)
                .addMapping(src -> src.getBooking().getId(), ReviewDto::setBookingId)
                .addMapping(src -> src.getTour().getId(), ReviewDto::setTourId);

        modelMapper.createTypeMap(ReviewDto.class, Review.class)
                .addMappings(mapping -> {
                    mapping.skip(Review::setId);
                    mapping.skip(Review::setUser);
                    mapping.skip(Review::setBooking);
                    mapping.skip(Review::setTour);
                    mapping.skip(Review::setCreatedAt);
                    mapping.skip(Review::setUpdatedAt);
                });
    }
}
