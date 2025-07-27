package com.travelapp.services.impl.book.mappers;

import com.travelapp.dto.book.BookingParticipantDto;
import com.travelapp.models.book.BookingParticipant;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingParticipantMapperService {

    private final ModelMapper modelMapper;

    public BookingParticipantDto toDto(BookingParticipant bookingParticipant) {
        if (bookingParticipant == null) {
            return null;
        }

        BookingParticipantDto dto = modelMapper.map(bookingParticipant, BookingParticipantDto.class);

        if (bookingParticipant.getBooking() != null) {
            dto.setBookingId(bookingParticipant.getBooking().getId());
        }

        return dto;
    }

    public BookingParticipant toEntity(BookingParticipantDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, BookingParticipant.class);
    }

    public List<BookingParticipantDto> toDtoList(List<BookingParticipant> bookingParticipants) {
        if (bookingParticipants == null) {
            return null;
        }

        return bookingParticipants.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<BookingParticipantDto> toDtoSet(Set<BookingParticipant> bookingParticipants) {
        if (bookingParticipants == null) {
            return null;
        }

        return bookingParticipants.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<BookingParticipant> toEntityList(List<BookingParticipantDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(BookingParticipantDto dto, BookingParticipant existingBookingParticipant) {
        if (dto == null || existingBookingParticipant == null) {
            return;
        }

        modelMapper.map(dto, existingBookingParticipant);
    }
}
