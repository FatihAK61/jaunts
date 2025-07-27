package com.travelapp.services.impl.book.mappers;

import com.travelapp.dto.book.BookingDto;
import com.travelapp.models.book.Booking;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingMapperService {

    private final ModelMapper modelMapper;

    public BookingDto toDto(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingDto dto = modelMapper.map(booking, BookingDto.class);

        if (booking.getUser() != null) {
            dto.setUserId(booking.getUser().getId());
        }

        if (booking.getTour() != null) {
            dto.setTourId(booking.getTour().getId());
        }

        if (booking.getSchedule() != null) {
            dto.setScheduleId(booking.getSchedule().getId());
        }

        return dto;
    }

    public Booking toEntity(BookingDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, Booking.class);
    }

    public List<BookingDto> toDtoList(List<Booking> bookings) {
        if (bookings == null) {
            return null;
        }

        return bookings.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<BookingDto> toDtoSet(Set<Booking> bookings) {
        if (bookings == null) {
            return null;
        }

        return bookings.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<Booking> toEntityList(List<BookingDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(BookingDto dto, Booking existingBooking) {
        if (dto == null || existingBooking == null) {
            return;
        }
        
        modelMapper.map(dto, existingBooking);
    }
}
