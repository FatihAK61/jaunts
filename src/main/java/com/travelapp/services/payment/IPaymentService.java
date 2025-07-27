package com.travelapp.services.payment;

import com.travelapp.dto.payment.PaymentDto;
import com.travelapp.helper.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IPaymentService {

    PaymentDto createPayment(PaymentDto paymentDto);

    PaymentDto updatePayment(PaymentDto paymentDto);

    PaymentDto getPaymentById(Long id);

    PaymentDto getPaymentByPaymentId(String paymentId);

    PaymentDto getPaymentByTransactionId(String transactionId);

    List<PaymentDto> getPaymentsByBookingId(Long bookingId);

    List<PaymentDto> getPaymentsByStatus(PaymentStatus status);

    List<PaymentDto> getPaymentsByBookingIdAndStatus(Long bookingId, PaymentStatus status);

    Page<PaymentDto> getPaymentsByUserId(Long userId, Pageable pageable);

    Page<PaymentDto> getPaymentsByUserIdAndStatus(Long userId, PaymentStatus status, Pageable pageable);

    List<PaymentDto> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<PaymentDto> getPaymentsByStatusAndCreatedBefore(PaymentStatus status, LocalDateTime beforeDate);

    PaymentDto updatePaymentStatus(Long id, PaymentStatus status);

    PaymentDto processPayment(Long id);

    PaymentDto authorizePayment(Long id);

    PaymentDto capturePayment(Long id);

    PaymentDto refundPayment(Long id, BigDecimal refundAmount);

    PaymentDto failPayment(Long id, String failureReason);

    BigDecimal getTotalPaidAmountByUserId(Long userId);

    Long countPaymentsByStatus(PaymentStatus status);

    boolean existsByPaymentId(String paymentId);

    boolean existsByTransactionId(String transactionId);

    void deletePayment(Long id);

}
