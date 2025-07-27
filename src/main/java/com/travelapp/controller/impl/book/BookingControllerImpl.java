package com.travelapp.controller.impl.book;

import com.travelapp.controller.book.IBookingController;
import com.travelapp.dto.book.BookingDto;
import com.travelapp.helper.enums.BookingStatus;
import com.travelapp.helper.enums.PaymentStatus;
import com.travelapp.services.book.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingControllerImpl implements IBookingController {

    private final IBookingService bookingService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto bookingDto) {
        BookingDto createdBooking = bookingService.createBooking(bookingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

    @Override
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable(name = "id") Long id, @RequestBody BookingDto bookingDto) {
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDto));
    }

    @Override
    @GetMapping(path = "/all")
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @Override
    @GetMapping(path = "/all/paginated")
    public ResponseEntity<Page<BookingDto>> getAllBookingsWithPagination(Pageable pageable) {
        return ResponseEntity.ok(bookingService.getAllBookings(pageable));
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @Override
    @GetMapping(path = "/booking-number/{bookingNumber}")
    public ResponseEntity<BookingDto> getBookingByBookingNumber(@PathVariable(name = "bookingNumber") String bookingNumber) {
        return ResponseEntity.ok(bookingService.getBookingByBookingNumber(bookingNumber));
    }

    @Override
    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<BookingDto>> getBookingsByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
    }

    @Override
    @GetMapping(path = "/user/{userId}/paginated")
    public ResponseEntity<Page<BookingDto>> getBookingsByUserIdWithPagination(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        return ResponseEntity.ok(bookingService.getBookingsByUserId(userId, pageable));
    }

    @Override
    @GetMapping(path = "/tour/{tourId}")
    public ResponseEntity<List<BookingDto>> getBookingsByTourId(@PathVariable(name = "tourId") Long tourId) {
        return ResponseEntity.ok(bookingService.getBookingsByTourId(tourId));
    }

    @Override
    @GetMapping(path = "/tour/{tourId}/paginated")
    public ResponseEntity<Page<BookingDto>> getBookingsByTourIdWithPagination(@PathVariable(name = "tourId") Long tourId, Pageable pageable) {
        return ResponseEntity.ok(bookingService.getBookingsByTourId(tourId, pageable));
    }

    @Override
    @GetMapping(path = "/schedule/{scheduleId}")
    public ResponseEntity<List<BookingDto>> getBookingsByScheduleId(@PathVariable(name = "scheduleId") Long scheduleId) {
        return ResponseEntity.ok(bookingService.getBookingsByScheduleId(scheduleId));
    }

    @Override
    @GetMapping(path = "/schedule/{scheduleId}/paginated")
    public ResponseEntity<Page<BookingDto>> getBookingsByScheduleIdWithPagination(@PathVariable(name = "scheduleId") Long scheduleId, Pageable pageable) {
        return ResponseEntity.ok(bookingService.getBookingsByScheduleId(scheduleId, pageable));
    }

    @Override
    @GetMapping(path = "/status/{status}")
    public ResponseEntity<List<BookingDto>> getBookingsByStatus(@PathVariable(name = "status") BookingStatus status) {
        return ResponseEntity.ok(bookingService.getBookingsByStatus(status));
    }

    @Override
    @GetMapping(path = "/status/{status}/paginated")
    public ResponseEntity<Page<BookingDto>> getBookingsByStatusWithPagination(@PathVariable(name = "status") BookingStatus status, Pageable pageable) {
        return ResponseEntity.ok(bookingService.getBookingsByStatus(status, pageable));
    }

    @Override
    @GetMapping(path = "/payment-status/{paymentStatus}")
    public ResponseEntity<List<BookingDto>> getBookingsByPaymentStatus(@PathVariable(name = "paymentStatus") PaymentStatus paymentStatus) {
        return ResponseEntity.ok(bookingService.getBookingsByPaymentStatus(paymentStatus));
    }

    @Override
    @GetMapping(path = "/payment-status/{paymentStatus}/paginated")
    public ResponseEntity<Page<BookingDto>> getBookingsByPaymentStatusWithPagination(@PathVariable(name = "paymentStatus") PaymentStatus paymentStatus, Pageable pageable) {
        return ResponseEntity.ok(bookingService.getBookingsByPaymentStatus(paymentStatus, pageable));
    }

    @Override
    @GetMapping(path = "/user/{userId}/status/{status}")
    public ResponseEntity<List<BookingDto>> getBookingsByUserIdAndStatus(@PathVariable(name = "userId") Long userId, @PathVariable(name = "status") BookingStatus status) {
        return ResponseEntity.ok(bookingService.getBookingsByUserIdAndStatus(userId, status));
    }

    @Override
    @GetMapping(path = "/user/{userId}/status/{status}/paginated")
    public ResponseEntity<Page<BookingDto>> getBookingsByUserIdAndStatusWithPagination(@PathVariable(name = "userId") Long userId, @PathVariable(name = "status") BookingStatus status, Pageable pageable) {
        return ResponseEntity.ok(bookingService.getBookingsByUserIdAndStatus(userId, status, pageable));
    }

    @Override
    @GetMapping(path = "/user/{userId}/tour/{tourId}")
    public ResponseEntity<List<BookingDto>> getBookingsByUserIdAndTourId(@PathVariable(name = "userId") Long userId, @PathVariable(name = "tourId") Long tourId) {
        return ResponseEntity.ok(bookingService.getBookingsByUserIdAndTourId(userId, tourId));
    }

    @Override
    @GetMapping(path = "/created-between")
    public ResponseEntity<List<BookingDto>> getBookingsByCreatedAtBetween(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(bookingService.getBookingsByCreatedAtBetween(startDate, endDate));
    }

    @Override
    @GetMapping(path = "/confirmed-between")
    public ResponseEntity<List<BookingDto>> getBookingsByConfirmedAtBetween(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(bookingService.getBookingsByConfirmedAtBetween(startDate, endDate));
    }

    @Override
    @GetMapping(path = "/expired")
    public ResponseEntity<List<BookingDto>> getExpiredBookings() {
        return ResponseEntity.ok(bookingService.getExpiredBookings());
    }

    @Override
    @PutMapping(path = "/{id}/confirm")
    public ResponseEntity<BookingDto> confirmBooking(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(bookingService.confirmBooking(id));
    }

    @Override
    @PutMapping(path = "/{id}/cancel")
    public ResponseEntity<BookingDto> cancelBooking(@PathVariable(name = "id") Long id, @RequestParam String cancellationReason) {
        return ResponseEntity.ok(bookingService.cancelBooking(id, cancellationReason));
    }

    @Override
    @PutMapping(path = "/{id}/status/{status}")
    public ResponseEntity<BookingDto> updateBookingStatus(@PathVariable(name = "id") Long id, @PathVariable(name = "status") BookingStatus status) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, status));
    }

    @Override
    @PutMapping(path = "/{id}/payment-status/{paymentStatus}")
    public ResponseEntity<BookingDto> updatePaymentStatus(@PathVariable(name = "id") Long id, @PathVariable(name = "paymentStatus") PaymentStatus paymentStatus) {
        return ResponseEntity.ok(bookingService.updatePaymentStatus(id, paymentStatus));
    }

    @Override
    @PutMapping(path = "/{id}/paid-amount")
    public ResponseEntity<BookingDto> updatePaidAmount(@PathVariable(name = "id") Long id, @RequestParam BigDecimal paidAmount) {
        return ResponseEntity.ok(bookingService.updatePaidAmount(id, paidAmount));
    }

    @Override
    @PutMapping(path = "/{id}/refund-amount")
    public ResponseEntity<BookingDto> updateRefundAmount(@PathVariable(name = "id") Long id, @RequestParam BigDecimal refundAmount) {
        return ResponseEntity.ok(bookingService.updateRefundAmount(id, refundAmount));
    }

    @Override
    @PutMapping(path = "/{id}/admin-notes")
    public ResponseEntity<BookingDto> addAdminNotes(@PathVariable(name = "id") Long id, @RequestParam String adminNotes) {
        return ResponseEntity.ok(bookingService.addAdminNotes(id, adminNotes));
    }

    @Override
    @PutMapping(path = "/{id}/customer-notes")
    public ResponseEntity<BookingDto> addCustomerNotes(@PathVariable(name = "id") Long id, @RequestParam String customerNotes) {
        return ResponseEntity.ok(bookingService.addCustomerNotes(id, customerNotes));
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable(name = "id") Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping(path = "/count/tour/{tourId}")
    public ResponseEntity<Long> countBookingsByTourId(@PathVariable(name = "tourId") Long tourId) {
        return ResponseEntity.ok(bookingService.countBookingsByTourId(tourId));
    }

    @Override
    @GetMapping(path = "/sum-participants/schedule/{scheduleId}")
    public ResponseEntity<Integer> sumParticipantsByScheduleId(@PathVariable(name = "scheduleId") Long scheduleId) {
        return ResponseEntity.ok(bookingService.sumParticipantsByScheduleId(scheduleId));
    }

    @Override
    @GetMapping(path = "/generate-booking-number")
    public ResponseEntity<String> generateBookingNumber() {
        return ResponseEntity.ok(bookingService.generateBookingNumber());
    }

    @Override
    @PostMapping(path = "/process-expired")
    public ResponseEntity<Void> processExpiredBookings() {
        bookingService.processExpiredBookings();
        return ResponseEntity.ok().build();
    }
}
