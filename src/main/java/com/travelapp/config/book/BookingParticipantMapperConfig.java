package com.travelapp.config.book;

import com.travelapp.dto.book.BookingParticipantDto;
import com.travelapp.models.book.BookingParticipant;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BookingParticipantMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(BookingParticipant.class, BookingParticipantDto.class)
                .addMapping(src -> src.getBooking().getId(), BookingParticipantDto::setBookingId);

        modelMapper.createTypeMap(BookingParticipantDto.class, BookingParticipant.class)
                .addMappings(mapping -> {
                    mapping.skip(BookingParticipant::setId);
                    mapping.skip(BookingParticipant::setBooking);
                });
    }
}
