package com.travelapp.config.book;

import com.travelapp.dto.book.BookingDto;
import com.travelapp.models.book.Booking;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BookingMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(Booking.class, BookingDto.class)
                .addMapping(src -> src.getUser().getId(), BookingDto::setUserId)
                .addMapping(src -> src.getTour().getId(), BookingDto::setTourId)
                .addMapping(src -> src.getSchedule().getId(), BookingDto::setScheduleId)
                .addMappings(mapping -> {
                    mapping.skip(BookingDto::setParticipants);
                    mapping.skip(BookingDto::setPayments);
                    mapping.skip(BookingDto::setSupportTickets);
                    mapping.skip(BookingDto::setReviews);
                    mapping.skip(BookingDto::setCouponUsages);
                });

        modelMapper.createTypeMap(BookingDto.class, Booking.class)
                .addMappings(mapping -> {
                    mapping.skip(Booking::setId);
                    mapping.skip(Booking::setUser);
                    mapping.skip(Booking::setTour);
                    mapping.skip(Booking::setSchedule);

                    mapping.skip(Booking::setParticipants);
                    mapping.skip(Booking::setPayments);
                    mapping.skip(Booking::setReview);
                });
    }
}
