package com.travelapp.controller.impl.book;

import com.travelapp.controller.book.IBookingParticipantController;
import com.travelapp.dto.book.BookingParticipantDto;
import com.travelapp.services.book.IBookingParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking-participant")
@RequiredArgsConstructor
public class BookingParticipantControllerImpl implements IBookingParticipantController {

    private final IBookingParticipantService bookingParticipantService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<BookingParticipantDto> createParticipant(@RequestBody BookingParticipantDto participantDto) {
        BookingParticipantDto createdParticipant = bookingParticipantService.createParticipant(participantDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParticipant);
    }

    @Override
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<BookingParticipantDto> updateParticipant(@PathVariable(name = "id") Long id, @RequestBody BookingParticipantDto participantDto) {
        BookingParticipantDto updatedParticipant = bookingParticipantService.updateParticipant(id, participantDto);
        return ResponseEntity.ok(updatedParticipant);
    }

    @Override
    @GetMapping(path = "/all")
    public ResponseEntity<List<BookingParticipantDto>> getAllParticipants() {
        List<BookingParticipantDto> participants = bookingParticipantService.getAllParticipants();
        return ResponseEntity.ok(participants);
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<BookingParticipantDto> getParticipantById(@PathVariable(name = "id") Long id) {
        return bookingParticipantService.getParticipantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping(path = "/booking/{bookingId}")
    public ResponseEntity<List<BookingParticipantDto>> getParticipantsByBookingId(@PathVariable(name = "bookingId") Long bookingId) {
        List<BookingParticipantDto> participants = bookingParticipantService.getParticipantsByBookingId(bookingId);
        return ResponseEntity.ok(participants);
    }

    @Override
    @GetMapping(path = "/booking/{bookingId}/participant/{participantId}")
    public ResponseEntity<BookingParticipantDto> getParticipantByBookingIdAndParticipantId(
            @PathVariable(name = "bookingId") Long bookingId,
            @PathVariable(name = "participantId") Long participantId) {
        return bookingParticipantService.getParticipantByBookingIdAndParticipantId(bookingId, participantId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping(path = "/email/{email}")
    public ResponseEntity<List<BookingParticipantDto>> getParticipantsByEmail(@PathVariable(name = "email") String email) {
        List<BookingParticipantDto> participants = bookingParticipantService.getParticipantsByEmail(email);
        return ResponseEntity.ok(participants);
    }

    @Override
    @GetMapping(path = "/passport/{passportNumber}")
    public ResponseEntity<BookingParticipantDto> getParticipantByPassportNumber(@PathVariable(name = "passportNumber") String passportNumber) {
        return bookingParticipantService.getParticipantByPassportNumber(passportNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping(path = "/count/booking/{bookingId}")
    public ResponseEntity<Long> countParticipantsByBookingId(@PathVariable(name = "bookingId") Long bookingId) {
        Long count = bookingParticipantService.countParticipantsByBookingId(bookingId);
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping(path = "/exists/{id}")
    public ResponseEntity<Boolean> existsParticipantById(@PathVariable(name = "id") Long id) {
        Boolean exists = bookingParticipantService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable(name = "id") Long id) {
        bookingParticipantService.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping(path = "/booking/{bookingId}")
    public ResponseEntity<Void> deleteParticipantsByBookingId(@PathVariable(name = "bookingId") Long bookingId) {
        bookingParticipantService.deleteParticipantsByBookingId(bookingId);
        return ResponseEntity.noContent().build();
    }
}
