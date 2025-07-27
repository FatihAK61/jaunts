package com.travelapp.services.book;

import com.travelapp.dto.book.BookingParticipantDto;

import java.util.List;
import java.util.Optional;

public interface IBookingParticipantService {

    BookingParticipantDto createParticipant(BookingParticipantDto participantDto);

    Optional<BookingParticipantDto> getParticipantById(Long id);

    List<BookingParticipantDto> getAllParticipants();

    List<BookingParticipantDto> getParticipantsByBookingId(Long bookingId);

    BookingParticipantDto updateParticipant(Long id, BookingParticipantDto participantDto);

    void deleteParticipant(Long id);

    void deleteParticipantsByBookingId(Long bookingId);

    Optional<BookingParticipantDto> getParticipantByBookingIdAndParticipantId(Long bookingId, Long participantId);

    List<BookingParticipantDto> getParticipantsByEmail(String email);

    Optional<BookingParticipantDto> getParticipantByPassportNumber(String passportNumber);

    Long countParticipantsByBookingId(Long bookingId);

    boolean existsById(Long id);
}
