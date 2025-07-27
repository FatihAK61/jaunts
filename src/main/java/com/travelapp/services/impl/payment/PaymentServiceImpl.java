package com.travelapp.services.impl.payment;

import com.travelapp.dto.payment.PaymentDto;
import com.travelapp.helper.enums.PaymentStatus;
import com.travelapp.models.book.Booking;
import com.travelapp.models.payment.Payment;
import com.travelapp.models.payment.PaymentMethod;
import com.travelapp.repository.book.BookingRepository;
import com.travelapp.repository.payment.PaymentMethodRepository;
import com.travelapp.repository.payment.PaymentRepository;
import com.travelapp.services.impl.payment.mappers.PaymentMapperService;
import com.travelapp.services.payment.IPaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMapperService paymentMapperService;

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = paymentMapperService.toEntity(paymentDto);

        if (paymentDto.getBookingId() != null) {
            Booking booking = bookingRepository.findById(paymentDto.getBookingId())
                    .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + paymentDto.getBookingId()));
            payment.setBooking(booking);
        }

        if (paymentDto.getPaymentMethodId() != null) {
            PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentDto.getPaymentMethodId())
                    .orElseThrow(() -> new EntityNotFoundException("Payment method not found with id: " + paymentDto.getPaymentMethodId()));
            payment.setPaymentMethod(paymentMethod);
        }

        if (payment.getPaymentId() == null) {
            payment.setPaymentId(generatePaymentId());
        }

        Payment saved = paymentRepository.save(payment);

        return paymentMapperService.toDto(saved);
    }

    @Override
    public PaymentDto updatePayment(PaymentDto paymentDto) {
        Payment existingPayment = paymentRepository.findById(paymentDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + paymentDto.getId()));

        paymentMapperService.updateEntityFromDto(paymentDto, existingPayment);

        Payment updated = paymentRepository.save(existingPayment);

        return paymentMapperService.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));
        return paymentMapperService.toDto(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDto getPaymentByPaymentId(String paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with payment id: " + paymentId));
        return paymentMapperService.toDto(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDto getPaymentByTransactionId(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with transaction id: " + transactionId));
        return paymentMapperService.toDto(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByBookingId(Long bookingId) {
        List<Payment> payments = paymentRepository.findByBookingId(bookingId);
        return paymentMapperService.toDtoList(payments);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByStatus(PaymentStatus status) {
        List<Payment> payments = paymentRepository.findByStatus(status);
        return paymentMapperService.toDtoList(payments);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByBookingIdAndStatus(Long bookingId, PaymentStatus status) {
        List<Payment> payments = paymentRepository.findByBookingIdAndStatus(bookingId, status);
        return paymentMapperService.toDtoList(payments);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentDto> getPaymentsByUserId(Long userId, Pageable pageable) {
        Page<Payment> payments = paymentRepository.findByBookingUserId(userId, pageable);
        return payments.map(paymentMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentDto> getPaymentsByUserIdAndStatus(Long userId, PaymentStatus status, Pageable pageable) {
        Page<Payment> payments = paymentRepository.findByUserIdAndStatus(userId, status, pageable);
        return payments.map(paymentMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Payment> payments = paymentRepository.findByDateRange(startDate, endDate);
        return paymentMapperService.toDtoList(payments);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getPaymentsByStatusAndCreatedBefore(PaymentStatus status, LocalDateTime beforeDate) {
        List<Payment> payments = paymentRepository.findByStatusAndCreatedBefore(status, beforeDate);
        return paymentMapperService.toDtoList(payments);
    }

    @Override
    public PaymentDto updatePaymentStatus(Long id, PaymentStatus status) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));

        payment.setStatus(status);
        Payment updated = paymentRepository.save(payment);

        return paymentMapperService.toDto(updated);
    }

    @Override
    public PaymentDto processPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));

        payment.setStatus(PaymentStatus.PROCESSING);
        payment.setProcessedAt(LocalDateTime.now());

        Payment updated = paymentRepository.save(payment);

        return paymentMapperService.toDto(updated);
    }

    @Override
    public PaymentDto authorizePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));

        payment.setStatus(PaymentStatus.AUTHORIZED);
        payment.setAuthorizedAt(LocalDateTime.now());

        Payment updated = paymentRepository.save(payment);

        return paymentMapperService.toDto(updated);
    }

    @Override
    public PaymentDto capturePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));

        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setCapturedAt(LocalDateTime.now());

        Payment updated = paymentRepository.save(payment);

        return paymentMapperService.toDto(updated);
    }

    @Override
    public PaymentDto refundPayment(Long id, BigDecimal refundAmount) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));

        if (refundAmount.compareTo(payment.getAmount()) > 0) {
            throw new IllegalArgumentException("Refund amount cannot be greater than payment amount.");
        }

        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setRefundAmount(refundAmount);
        payment.setRefundedAt(LocalDateTime.now());

        Payment updated = paymentRepository.save(payment);

        return paymentMapperService.toDto(updated);
    }

    @Override
    public PaymentDto failPayment(Long id, String failureReason) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + id));

        payment.setStatus(PaymentStatus.FAILED);
        payment.setFailureReason(failureReason);
        payment.setFailedAt(LocalDateTime.now());
        payment.setAttemptCount(payment.getAttemptCount() + 1);

        Payment updated = paymentRepository.save(payment);

        return paymentMapperService.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalPaidAmountByUserId(Long userId) {
        BigDecimal total = paymentRepository.getTotalPaidAmountByUserId(userId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    @Transactional(readOnly = true)
    public Long countPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.countByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByPaymentId(String paymentId) {
        return paymentRepository.existsByPaymentId(paymentId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByTransactionId(String transactionId) {
        return paymentRepository.existsByTransactionId(transactionId);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    private String generatePaymentId() {
        return "PAY_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
}
