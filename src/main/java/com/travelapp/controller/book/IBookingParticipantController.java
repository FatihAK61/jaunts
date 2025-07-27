package com.travelapp.controller.book;

import com.travelapp.dto.book.BookingParticipantDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBookingParticipantController {

    ResponseEntity<BookingParticipantDto> createParticipant(@Valid @NotNull BookingParticipantDto participantDto);

    ResponseEntity<BookingParticipantDto> updateParticipant(Long id, @Valid @NotNull BookingParticipantDto participantDto);

    ResponseEntity<List<BookingParticipantDto>> getAllParticipants();

    ResponseEntity<BookingParticipantDto> getParticipantById(Long id);

    ResponseEntity<List<BookingParticipantDto>> getParticipantsByBookingId(Long bookingId);

    ResponseEntity<BookingParticipantDto> getParticipantByBookingIdAndParticipantId(Long bookingId, Long participantId);

    ResponseEntity<List<BookingParticipantDto>> getParticipantsByEmail(String email);

    ResponseEntity<BookingParticipantDto> getParticipantByPassportNumber(String passportNumber);

    ResponseEntity<Long> countParticipantsByBookingId(Long bookingId);

    ResponseEntity<Boolean> existsParticipantById(Long id);

    ResponseEntity<Void> deleteParticipant(Long id);

    ResponseEntity<Void> deleteParticipantsByBookingId(Long bookingId);
}
