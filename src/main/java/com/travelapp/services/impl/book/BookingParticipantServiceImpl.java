package com.travelapp.services.impl.book;

import com.travelapp.dto.book.BookingParticipantDto;
import com.travelapp.models.book.Booking;
import com.travelapp.models.book.BookingParticipant;
import com.travelapp.repository.book.BookingParticipantRepository;
import com.travelapp.repository.book.BookingRepository;
import com.travelapp.services.book.IBookingParticipantService;
import com.travelapp.services.impl.book.mappers.BookingParticipantMapperService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingParticipantServiceImpl implements IBookingParticipantService {

    private final BookingParticipantRepository bookingParticipantRepository;
    private final BookingRepository bookingRepository;
    private final BookingParticipantMapperService mapperService;

    @Override
    public BookingParticipantDto createParticipant(BookingParticipantDto participantDto) {

        BookingParticipant participant = mapperService.toEntity(participantDto);

        if (participantDto.getBookingId() != null) {
            Booking booking = bookingRepository.findById(participantDto.getBookingId())
                    .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + participantDto.getBookingId()));
            participant.setBooking(booking);
        }

        BookingParticipant savedParticipant = bookingParticipantRepository.save(participant);

        return mapperService.toDto(savedParticipant);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookingParticipantDto> getParticipantById(Long id) {
        return bookingParticipantRepository.findById(id)
                .map(mapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingParticipantDto> getAllParticipants() {
        List<BookingParticipant> participants = bookingParticipantRepository.findAll();
        return mapperService.toDtoList(participants);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingParticipantDto> getParticipantsByBookingId(Long bookingId) {
        List<BookingParticipant> participants = bookingParticipantRepository.findByBookingId(bookingId);
        return mapperService.toDtoList(participants);
    }

    @Override
    public BookingParticipantDto updateParticipant(Long id, BookingParticipantDto participantDto) {

        BookingParticipant existingParticipant = bookingParticipantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking participant not found with id: " + id));

        mapperService.updateEntityFromDto(participantDto, existingParticipant);

        if (participantDto.getBookingId() != null &&
                !participantDto.getBookingId().equals(existingParticipant.getBooking().getId())) {
            Booking booking = bookingRepository.findById(participantDto.getBookingId())
                    .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + participantDto.getBookingId()));
            existingParticipant.setBooking(booking);
        }

        BookingParticipant updatedParticipant = bookingParticipantRepository.save(existingParticipant);

        return mapperService.toDto(updatedParticipant);
    }

    @Override
    public void deleteParticipant(Long id) {

        if (!bookingParticipantRepository.existsById(id)) {
            throw new EntityNotFoundException("Booking participant not found with id: " + id);
        }

        bookingParticipantRepository.deleteById(id);
    }

    @Override
    public void deleteParticipantsByBookingId(Long bookingId) {
        bookingParticipantRepository.deleteByBookingId(bookingId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookingParticipantDto> getParticipantByBookingIdAndParticipantId(Long bookingId, Long participantId) {
        return bookingParticipantRepository.findByBookingIdAndId(bookingId, participantId)
                .map(mapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingParticipantDto> getParticipantsByEmail(String email) {
        List<BookingParticipant> participants = bookingParticipantRepository.findByEmail(email);
        return mapperService.toDtoList(participants);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookingParticipantDto> getParticipantByPassportNumber(String passportNumber) {
        return bookingParticipantRepository.findByPassportNumber(passportNumber)
                .map(mapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countParticipantsByBookingId(Long bookingId) {
        return bookingParticipantRepository.countByBookingId(bookingId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return bookingParticipantRepository.existsById(id);
    }
}
