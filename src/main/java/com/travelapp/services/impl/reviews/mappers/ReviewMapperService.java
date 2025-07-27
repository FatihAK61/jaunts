package com.travelapp.services.impl.reviews.mappers;

import com.travelapp.dto.reviews.ReviewDto;
import com.travelapp.models.reviews.Review;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewMapperService {

    private final ModelMapper modelMapper;

    public ReviewDto toDto(Review review) {
        if (review == null) {
            return null;
        }

        ReviewDto dto = modelMapper.map(review, ReviewDto.class);

        if (review.getUser() != null) {
            dto.setUserId(review.getUser().getId());
        }

        if (review.getBooking() != null) {
            dto.setBookingId(review.getBooking().getId());
        }

        if (review.getTour() != null) {
            dto.setTourId(review.getTour().getId());
        }

        return dto;
    }

    public Review toEntity(ReviewDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, Review.class);
    }

    public List<ReviewDto> toDtoList(List<Review> reviews) {
        if (reviews == null) {
            return null;
        }

        return reviews.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<ReviewDto> toDtoSet(Set<Review> reviews) {
        if (reviews == null) {
            return null;
        }

        return reviews.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<Review> toEntityList(List<ReviewDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(ReviewDto dto, Review existingReview) {
        if (dto == null || existingReview == null) {
            return;
        }

        modelMapper.map(dto, existingReview);
    }
}
