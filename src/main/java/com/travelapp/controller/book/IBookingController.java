package com.travelapp.controller.book;

import com.travelapp.dto.book.BookingDto;
import com.travelapp.helper.enums.BookingStatus;
import com.travelapp.helper.enums.PaymentStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IBookingController {

    ResponseEntity<BookingDto> createBooking(@Valid @NotNull BookingDto bookingDto);

    ResponseEntity<BookingDto> updateBooking(Long id, @Valid @NotNull BookingDto bookingDto);

    ResponseEntity<List<BookingDto>> getAllBookings();

    ResponseEntity<Page<BookingDto>> getAllBookingsWithPagination(Pageable pageable);

    ResponseEntity<BookingDto> getBookingById(Long id);

    ResponseEntity<BookingDto> getBookingByBookingNumber(String bookingNumber);

    ResponseEntity<List<BookingDto>> getBookingsByUserId(Long userId);

    ResponseEntity<Page<BookingDto>> getBookingsByUserIdWithPagination(Long userId, Pageable pageable);

    ResponseEntity<List<BookingDto>> getBookingsByTourId(Long tourId);

    ResponseEntity<Page<BookingDto>> getBookingsByTourIdWithPagination(Long tourId, Pageable pageable);

    ResponseEntity<List<BookingDto>> getBookingsByScheduleId(Long scheduleId);

    ResponseEntity<Page<BookingDto>> getBookingsByScheduleIdWithPagination(Long scheduleId, Pageable pageable);

    ResponseEntity<List<BookingDto>> getBookingsByStatus(BookingStatus status);

    ResponseEntity<Page<BookingDto>> getBookingsByStatusWithPagination(BookingStatus status, Pageable pageable);

    ResponseEntity<List<BookingDto>> getBookingsByPaymentStatus(PaymentStatus paymentStatus);

    ResponseEntity<Page<BookingDto>> getBookingsByPaymentStatusWithPagination(PaymentStatus paymentStatus, Pageable pageable);

    ResponseEntity<List<BookingDto>> getBookingsByUserIdAndStatus(Long userId, BookingStatus status);

    ResponseEntity<Page<BookingDto>> getBookingsByUserIdAndStatusWithPagination(Long userId, BookingStatus status, Pageable pageable);

    ResponseEntity<List<BookingDto>> getBookingsByUserIdAndTourId(Long userId, Long tourId);

    ResponseEntity<List<BookingDto>> getBookingsByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    ResponseEntity<List<BookingDto>> getBookingsByConfirmedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    ResponseEntity<List<BookingDto>> getExpiredBookings();

    ResponseEntity<BookingDto> confirmBooking(Long id);

    ResponseEntity<BookingDto> cancelBooking(Long id, String cancellationReason);

    ResponseEntity<BookingDto> updateBookingStatus(Long id, BookingStatus status);

    ResponseEntity<BookingDto> updatePaymentStatus(Long id, PaymentStatus paymentStatus);

    ResponseEntity<BookingDto> updatePaidAmount(Long id, BigDecimal paidAmount);

    ResponseEntity<BookingDto> updateRefundAmount(Long id, BigDecimal refundAmount);

    ResponseEntity<BookingDto> addAdminNotes(Long id, String adminNotes);

    ResponseEntity<BookingDto> addCustomerNotes(Long id, String customerNotes);

    ResponseEntity<Void> deleteBooking(Long id);

    ResponseEntity<Long> countBookingsByTourId(Long tourId);

    ResponseEntity<Integer> sumParticipantsByScheduleId(Long scheduleId);

    ResponseEntity<String> generateBookingNumber();

    ResponseEntity<Void> processExpiredBookings();
}
