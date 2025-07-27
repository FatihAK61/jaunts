package com.travelapp.services.impl.book;

import com.travelapp.dto.book.BookingDto;
import com.travelapp.helper.enums.BookingStatus;
import com.travelapp.helper.enums.PaymentStatus;
import com.travelapp.models.book.Booking;
import com.travelapp.models.tours.Tour;
import com.travelapp.models.tours.TourSchedule;
import com.travelapp.models.users.UserBase;
import com.travelapp.repository.book.BookingRepository;
import com.travelapp.repository.tours.TourRepository;
import com.travelapp.repository.tours.TourScheduleRepository;
import com.travelapp.repository.users.UserBaseRepository;
import com.travelapp.services.book.IBookingService;
import com.travelapp.services.impl.book.mappers.BookingMapperService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository bookingRepository;
    private final UserBaseRepository userBaseRepository;
    private final TourRepository tourRepository;
    private final TourScheduleRepository tourScheduleRepository;
    private final BookingMapperService bookingMapperService;

    @Override
    public BookingDto createBooking(BookingDto bookingDto) {

        Booking booking = bookingMapperService.toEntity(bookingDto);

        if (bookingDto.getUserId() != null) {
            UserBase user = userBaseRepository.findById(bookingDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + bookingDto.getUserId()));
            booking.setUser(user);
        }

        if (bookingDto.getTourId() != null) {
            Tour tour = tourRepository.findById(bookingDto.getTourId())
                    .orElseThrow(() -> new EntityNotFoundException("Tour not found with id: " + bookingDto.getTourId()));
            booking.setTour(tour);
        }

        if (bookingDto.getScheduleId() != null) {
            TourSchedule schedule = tourScheduleRepository.findById(bookingDto.getScheduleId())
                    .orElseThrow(() -> new EntityNotFoundException("Tour schedule not found with id: " + bookingDto.getScheduleId()));
            booking.setSchedule(schedule);
        }

        if (booking.getBookingNumber() == null || booking.getBookingNumber().isEmpty()) {
            booking.setBookingNumber(generateBookingNumber());
        }

        if (booking.getStatus() == null) {
            booking.setStatus(BookingStatus.PENDING);
        }
        if (booking.getPaymentStatus() == null) {
            booking.setPaymentStatus(PaymentStatus.PENDING);
        }
        if (booking.getPaidAmount() == null) {
            booking.setPaidAmount(BigDecimal.ZERO);
        }
        if (booking.getRefundAmount() == null) {
            booking.setRefundAmount(BigDecimal.ZERO);
        }

        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapperService.toDto(savedBooking);
    }

    @Override
    public BookingDto updateBooking(Long id, BookingDto bookingDto) {

        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        bookingMapperService.updateEntityFromDto(bookingDto, existingBooking);

        if (bookingDto.getUserId() != null &&
                (existingBooking.getUser() == null || !existingBooking.getUser().getId().equals(bookingDto.getUserId()))) {
            UserBase user = userBaseRepository.findById(bookingDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + bookingDto.getUserId()));
            existingBooking.setUser(user);
        }

        if (bookingDto.getTourId() != null &&
                (existingBooking.getTour() == null || !existingBooking.getTour().getId().equals(bookingDto.getTourId()))) {
            Tour tour = tourRepository.findById(bookingDto.getTourId())
                    .orElseThrow(() -> new EntityNotFoundException("Tour not found with id: " + bookingDto.getTourId()));
            existingBooking.setTour(tour);
        }

        if (bookingDto.getScheduleId() != null &&
                (existingBooking.getSchedule() == null || !existingBooking.getSchedule().getId().equals(bookingDto.getScheduleId()))) {
            TourSchedule schedule = tourScheduleRepository.findById(bookingDto.getScheduleId())
                    .orElseThrow(() -> new EntityNotFoundException("Tour schedule not found with id: " + bookingDto.getScheduleId()));
            existingBooking.setSchedule(schedule);
        }

        Booking updatedBooking = bookingRepository.save(existingBooking);

        return bookingMapperService.toDto(updatedBooking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDto getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        return bookingMapperService.toDto(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDto getBookingByBookingNumber(String bookingNumber) {

        Booking booking = bookingRepository.findByBookingNumber(bookingNumber)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with booking number: " + bookingNumber));

        return bookingMapperService.toDto(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookingMapperService.toDtoList(bookings);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingDto> getAllBookings(Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findAll(pageable);
        return bookings.map(bookingMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getBookingsByUserId(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        return bookingMapperService.toDtoList(bookings);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingDto> getBookingsByUserId(Long userId, Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findByUserId(userId, pageable);
        return bookings.map(bookingMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getBookingsByTourId(Long tourId) {
        List<Booking> bookings = bookingRepository.findByTourId(tourId);
        return bookingMapperService.toDtoList(bookings);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingDto> getBookingsByTourId(Long tourId, Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findByTourId(tourId, pageable);
        return bookings.map(bookingMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getBookingsByScheduleId(Long scheduleId) {
        List<Booking> bookings = bookingRepository.findByScheduleId(scheduleId);
        return bookingMapperService.toDtoList(bookings);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingDto> getBookingsByScheduleId(Long scheduleId, Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findByScheduleId(scheduleId, pageable);
        return bookings.map(bookingMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getBookingsByStatus(BookingStatus status) {
        List<Booking> bookings = bookingRepository.findByStatus(status);
        return bookingMapperService.toDtoList(bookings);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingDto> getBookingsByStatus(BookingStatus status, Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findByStatus(status, pageable);
        return bookings.map(bookingMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getBookingsByPaymentStatus(PaymentStatus paymentStatus) {
        List<Booking> bookings = bookingRepository.findByPaymentStatus(paymentStatus);
        return bookingMapperService.toDtoList(bookings);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingDto> getBookingsByPaymentStatus(PaymentStatus paymentStatus, Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findByPaymentStatus(paymentStatus, pageable);
        return bookings.map(bookingMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getBookingsByUserIdAndStatus(Long userId, BookingStatus status) {
        List<Booking> bookings = bookingRepository.findByUserIdAndStatus(userId, status);
        return bookingMapperService.toDtoList(bookings);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingDto> getBookingsByUserIdAndStatus(Long userId, BookingStatus status, Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findByUserIdAndStatus(userId, status, pageable);
        return bookings.map(bookingMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getBookingsByUserIdAndTourId(Long userId, Long tourId) {
        List<Booking> bookings = bookingRepository.findByUserIdAndTourId(userId, tourId);
        return bookingMapperService.toDtoList(bookings);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getBookingsByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<Booking> bookings = bookingRepository.findByCreatedAtBetween(startDate, endDate);
        return bookingMapperService.toDtoList(bookings);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getBookingsByConfirmedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<Booking> bookings = bookingRepository.findByConfirmedAtBetween(startDate, endDate);
        return bookingMapperService.toDtoList(bookings);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getExpiredBookings() {
        List<Booking> bookings = bookingRepository.findExpiredBookings(LocalDateTime.now(), BookingStatus.PENDING);
        return bookingMapperService.toDtoList(bookings);
    }

    @Override
    public BookingDto confirmBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setConfirmedAt(LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapperService.toDto(savedBooking);
    }

    @Override
    public BookingDto cancelBooking(Long id, String cancellationReason) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setCancelledAt(LocalDateTime.now());
        booking.setCancellationReason(cancellationReason);

        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapperService.toDto(savedBooking);
    }

    @Override
    public BookingDto updateBookingStatus(Long id, BookingStatus status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        booking.setStatus(status);

        if (status == BookingStatus.CONFIRMED) {
            booking.setConfirmedAt(LocalDateTime.now());
        } else if (status == BookingStatus.CANCELLED) {
            booking.setCancelledAt(LocalDateTime.now());
        }

        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapperService.toDto(savedBooking);
    }

    @Override
    public BookingDto updatePaymentStatus(Long id, PaymentStatus paymentStatus) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        booking.setPaymentStatus(paymentStatus);

        if (paymentStatus == PaymentStatus.PAID || paymentStatus == PaymentStatus.PARTIALLY_PAID) {
            booking.setPaidAt(LocalDateTime.now());
        }

        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapperService.toDto(savedBooking);
    }

    @Override
    public BookingDto updatePaidAmount(Long id, BigDecimal paidAmount) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        booking.setPaidAmount(paidAmount);

        if (paidAmount != null && paidAmount.compareTo(BigDecimal.ZERO) > 0) {
            booking.setPaidAt(LocalDateTime.now());

            if (booking.getTotalAmount() != null) {
                if (paidAmount.compareTo(booking.getTotalAmount()) >= 0) {
                    booking.setPaymentStatus(PaymentStatus.PAID);
                } else {
                    booking.setPaymentStatus(PaymentStatus.PARTIALLY_PAID);
                }
            }
        }

        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapperService.toDto(savedBooking);
    }

    @Override
    public BookingDto updateRefundAmount(Long id, BigDecimal refundAmount) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        booking.setRefundAmount(refundAmount);

        if (refundAmount != null && refundAmount.compareTo(BigDecimal.ZERO) > 0) {
            booking.setRefundedAt(LocalDateTime.now());

            if (booking.getPaidAmount() != null) {
                if (refundAmount.compareTo(booking.getPaidAmount()) >= 0) {
                    booking.setPaymentStatus(PaymentStatus.REFUNDED);
                } else {
                    booking.setPaymentStatus(PaymentStatus.PARTIALLY_REFUNDED);
                }
            }
        }

        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapperService.toDto(savedBooking);
    }

    @Override
    public BookingDto addAdminNotes(Long id, String adminNotes) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        String existingNotes = booking.getAdminNotes();
        if (existingNotes != null && !existingNotes.isEmpty()) {
            booking.setAdminNotes(existingNotes + "\n" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ": " + adminNotes);
        } else {
            booking.setAdminNotes(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ": " + adminNotes);
        }

        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapperService.toDto(savedBooking);
    }

    @Override
    public BookingDto addCustomerNotes(Long id, String customerNotes) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        String existingNotes = booking.getCustomerNotes();
        if (existingNotes != null && !existingNotes.isEmpty()) {
            booking.setCustomerNotes(existingNotes + "\n" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ": " + customerNotes);
        } else {
            booking.setCustomerNotes(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ": " + customerNotes);
        }

        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapperService.toDto(savedBooking);
    }

    @Override
    public void deleteBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        if (booking.getStatus() == BookingStatus.PENDING || booking.getStatus() == BookingStatus.CANCELLED) {
            bookingRepository.delete(booking);
        } else {
            throw new IllegalStateException("Only PENDING or CANCELLED bookings can be deleted.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long countBookingsByTourId(Long tourId) {
        return bookingRepository.countByTourId(tourId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countBookingsByTourIdAndStatuses(Long tourId, List<BookingStatus> statuses) {
        return bookingRepository.countByTourIdAndStatusIn(tourId, statuses);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer sumParticipantsByScheduleId(Long scheduleId) {
        Integer sum = bookingRepository.sumParticipantsByScheduleId(scheduleId);
        return sum != null ? sum : 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer sumParticipantsByScheduleIdAndStatuses(Long scheduleId, List<BookingStatus> statuses) {
        Integer sum = bookingRepository.sumParticipantsByScheduleIdAndStatusIn(scheduleId, statuses);
        return sum != null ? sum : 0;
    }

    @Override
    public String generateBookingNumber() {

        String prefix = "BK";
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomSuffix = String.format("%04d", new Random().nextInt(10000));

        String bookingNumber = prefix + timestamp + randomSuffix;

        // Eğer booking number zaten varsa, yeni bir tane oluştur
        while (isBookingNumberExists(bookingNumber)) {
            randomSuffix = String.format("%04d", new Random().nextInt(10000));
            bookingNumber = prefix + timestamp + randomSuffix;
        }

        return bookingNumber;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isBookingNumberExists(String bookingNumber) {
        return bookingRepository.existsByBookingNumber(bookingNumber);
    }

    @Override
    public void processExpiredBookings() {

        List<Booking> expiredBookings = bookingRepository.findExpiredBookings(LocalDateTime.now(), BookingStatus.PENDING);

        for (Booking booking : expiredBookings) {
            booking.setStatus(BookingStatus.EXPIRED);
            booking.setCancelledAt(LocalDateTime.now());
            booking.setCancellationReason("Booking expired automatically");
            bookingRepository.save(booking);
        }
    }
}
