package com.travelapp.services.book;

import com.travelapp.dto.book.BookingDto;
import com.travelapp.helper.enums.BookingStatus;
import com.travelapp.helper.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IBookingService {

    BookingDto createBooking(BookingDto bookingDto);

    BookingDto updateBooking(Long id, BookingDto bookingDto);

    BookingDto getBookingById(Long id);

    BookingDto getBookingByBookingNumber(String bookingNumber);

    List<BookingDto> getAllBookings();

    Page<BookingDto> getAllBookings(Pageable pageable);

    List<BookingDto> getBookingsByUserId(Long userId);

    Page<BookingDto> getBookingsByUserId(Long userId, Pageable pageable);

    List<BookingDto> getBookingsByTourId(Long tourId);

    Page<BookingDto> getBookingsByTourId(Long tourId, Pageable pageable);

    List<BookingDto> getBookingsByScheduleId(Long scheduleId);

    Page<BookingDto> getBookingsByScheduleId(Long scheduleId, Pageable pageable);

    List<BookingDto> getBookingsByStatus(BookingStatus status);

    Page<BookingDto> getBookingsByStatus(BookingStatus status, Pageable pageable);

    List<BookingDto> getBookingsByPaymentStatus(PaymentStatus paymentStatus);

    Page<BookingDto> getBookingsByPaymentStatus(PaymentStatus paymentStatus, Pageable pageable);

    List<BookingDto> getBookingsByUserIdAndStatus(Long userId, BookingStatus status);

    Page<BookingDto> getBookingsByUserIdAndStatus(Long userId, BookingStatus status, Pageable pageable);

    List<BookingDto> getBookingsByUserIdAndTourId(Long userId, Long tourId);

    List<BookingDto> getBookingsByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<BookingDto> getBookingsByConfirmedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<BookingDto> getExpiredBookings();

    BookingDto confirmBooking(Long id);

    BookingDto cancelBooking(Long id, String cancellationReason);

    BookingDto updateBookingStatus(Long id, BookingStatus status);

    BookingDto updatePaymentStatus(Long id, PaymentStatus paymentStatus);

    BookingDto updatePaidAmount(Long id, BigDecimal paidAmount);

    BookingDto updateRefundAmount(Long id, BigDecimal refundAmount);

    BookingDto addAdminNotes(Long id, String adminNotes);

    BookingDto addCustomerNotes(Long id, String customerNotes);

    void deleteBooking(Long id);

    Long countBookingsByTourId(Long tourId);

    Long countBookingsByTourIdAndStatuses(Long tourId, List<BookingStatus> statuses);

    Integer sumParticipantsByScheduleId(Long scheduleId);

    Integer sumParticipantsByScheduleIdAndStatuses(Long scheduleId, List<BookingStatus> statuses);

    String generateBookingNumber();

    boolean isBookingNumberExists(String bookingNumber);

    void processExpiredBookings();
}
