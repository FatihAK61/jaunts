package com.travelapp.controller.impl.payment;

import com.travelapp.controller.payment.IPaymentController;
import com.travelapp.dto.payment.PaymentDto;
import com.travelapp.helper.enums.PaymentStatus;
import com.travelapp.services.payment.IPaymentService;
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
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentControllerImpl implements IPaymentController {

    private final IPaymentService paymentService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto paymentDto) {
        PaymentDto createdPayment = paymentService.createPayment(paymentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
    }

    @Override
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable(name = "id") Long id, @RequestBody PaymentDto paymentDto) {
        paymentDto.setId(id);
        return ResponseEntity.ok(paymentService.updatePayment(paymentDto));
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @Override
    @GetMapping(path = "/payment-id/{paymentId}")
    public ResponseEntity<PaymentDto> getPaymentByPaymentId(@PathVariable(name = "paymentId") String paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentByPaymentId(paymentId));
    }

    @Override
    @GetMapping(path = "/transaction-id/{transactionId}")
    public ResponseEntity<PaymentDto> getPaymentByTransactionId(@PathVariable(name = "transactionId") String transactionId) {
        return ResponseEntity.ok(paymentService.getPaymentByTransactionId(transactionId));
    }

    @Override
    @GetMapping(path = "/booking/{bookingId}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByBookingId(@PathVariable(name = "bookingId") Long bookingId) {
        return ResponseEntity.ok(paymentService.getPaymentsByBookingId(bookingId));
    }

    @Override
    @GetMapping(path = "/status/{status}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByStatus(@PathVariable(name = "status") PaymentStatus status) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status));
    }

    @Override
    @GetMapping(path = "/booking/{bookingId}/status/{status}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByBookingIdAndStatus(@PathVariable(name = "bookingId") Long bookingId, @PathVariable(name = "status") PaymentStatus status) {
        return ResponseEntity.ok(paymentService.getPaymentsByBookingIdAndStatus(bookingId, status));
    }

    @Override
    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<Page<PaymentDto>> getPaymentsByUserId(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        return ResponseEntity.ok(paymentService.getPaymentsByUserId(userId, pageable));
    }

    @Override
    @GetMapping(path = "/user/{userId}/status/{status}")
    public ResponseEntity<Page<PaymentDto>> getPaymentsByUserIdAndStatus(@PathVariable(name = "userId") Long userId, @PathVariable(name = "status") PaymentStatus status, Pageable pageable) {
        return ResponseEntity.ok(paymentService.getPaymentsByUserIdAndStatus(userId, status, pageable));
    }

    @Override
    @GetMapping(path = "/date-range")
    public ResponseEntity<List<PaymentDto>> getPaymentsByDateRange(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(paymentService.getPaymentsByDateRange(startDate, endDate));
    }

    @Override
    @GetMapping(path = "/status/{status}/created-before")
    public ResponseEntity<List<PaymentDto>> getPaymentsByStatusAndCreatedBefore(@PathVariable(name = "status") PaymentStatus status, @RequestParam LocalDateTime beforeDate) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatusAndCreatedBefore(status, beforeDate));
    }

    @Override
    @PutMapping(path = "/{id}/status/{status}")
    public ResponseEntity<PaymentDto> updatePaymentStatus(@PathVariable(name = "id") Long id, @PathVariable(name = "status") PaymentStatus status) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(id, status));
    }

    @Override
    @PostMapping(path = "/{id}/process")
    public ResponseEntity<PaymentDto> processPayment(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(paymentService.processPayment(id));
    }

    @Override
    @PostMapping(path = "/{id}/authorize")
    public ResponseEntity<PaymentDto> authorizePayment(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(paymentService.authorizePayment(id));
    }

    @Override
    @PostMapping(path = "/{id}/capture")
    public ResponseEntity<PaymentDto> capturePayment(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(paymentService.capturePayment(id));
    }

    @Override
    @PostMapping(path = "/{id}/refund")
    public ResponseEntity<PaymentDto> refundPayment(@PathVariable(name = "id") Long id, @RequestParam BigDecimal refundAmount) {
        return ResponseEntity.ok(paymentService.refundPayment(id, refundAmount));
    }

    @Override
    @PostMapping(path = "/{id}/fail")
    public ResponseEntity<PaymentDto> failPayment(@PathVariable(name = "id") Long id, @RequestParam String failureReason) {
        return ResponseEntity.ok(paymentService.failPayment(id, failureReason));
    }

    @Override
    @GetMapping(path = "/total-paid/user/{userId}")
    public ResponseEntity<BigDecimal> getTotalPaidAmountByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(paymentService.getTotalPaidAmountByUserId(userId));
    }

    @Override
    @GetMapping(path = "/count/status/{status}")
    public ResponseEntity<Long> countPaymentsByStatus(@PathVariable(name = "status") PaymentStatus status) {
        return ResponseEntity.ok(paymentService.countPaymentsByStatus(status));
    }

    @Override
    @GetMapping(path = "/exists/payment-id/{paymentId}")
    public ResponseEntity<Boolean> existsByPaymentId(@PathVariable(name = "paymentId") String paymentId) {
        return ResponseEntity.ok(paymentService.existsByPaymentId(paymentId));
    }

    @Override
    @GetMapping(path = "/exists/transaction-id/{transactionId}")
    public ResponseEntity<Boolean> existsByTransactionId(@PathVariable(name = "transactionId") String transactionId) {
        return ResponseEntity.ok(paymentService.existsByTransactionId(transactionId));
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable(name = "id") Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
