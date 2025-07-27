package com.travelapp.controller.payment;

import com.travelapp.dto.payment.PaymentDto;
import com.travelapp.helper.enums.PaymentStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IPaymentController {

    ResponseEntity<PaymentDto> createPayment(@Valid @NotNull PaymentDto paymentDto);

    ResponseEntity<PaymentDto> updatePayment(Long id, @Valid @NotNull PaymentDto paymentDto);

    ResponseEntity<PaymentDto> getPaymentById(Long id);

    ResponseEntity<PaymentDto> getPaymentByPaymentId(String paymentId);

    ResponseEntity<PaymentDto> getPaymentByTransactionId(String transactionId);

    ResponseEntity<List<PaymentDto>> getPaymentsByBookingId(Long bookingId);

    ResponseEntity<List<PaymentDto>> getPaymentsByStatus(PaymentStatus status);

    ResponseEntity<List<PaymentDto>> getPaymentsByBookingIdAndStatus(Long bookingId, PaymentStatus status);

    ResponseEntity<Page<PaymentDto>> getPaymentsByUserId(Long userId, Pageable pageable);

    ResponseEntity<Page<PaymentDto>> getPaymentsByUserIdAndStatus(Long userId, PaymentStatus status, Pageable pageable);

    ResponseEntity<List<PaymentDto>> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    ResponseEntity<List<PaymentDto>> getPaymentsByStatusAndCreatedBefore(PaymentStatus status, LocalDateTime beforeDate);

    ResponseEntity<PaymentDto> updatePaymentStatus(Long id, PaymentStatus status);

    ResponseEntity<PaymentDto> processPayment(Long id);

    ResponseEntity<PaymentDto> authorizePayment(Long id);

    ResponseEntity<PaymentDto> capturePayment(Long id);

    ResponseEntity<PaymentDto> refundPayment(Long id, BigDecimal refundAmount);

    ResponseEntity<PaymentDto> failPayment(Long id, String failureReason);

    ResponseEntity<BigDecimal> getTotalPaidAmountByUserId(Long userId);

    ResponseEntity<Long> countPaymentsByStatus(PaymentStatus status);

    ResponseEntity<Boolean> existsByPaymentId(String paymentId);

    ResponseEntity<Boolean> existsByTransactionId(String transactionId);

    ResponseEntity<Void> deletePayment(Long id);

}
