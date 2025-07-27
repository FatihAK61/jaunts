package com.travelapp.controller.payment;

import com.travelapp.dto.payment.PaymentTransactionDto;
import com.travelapp.helper.enums.TransactionStatus;
import com.travelapp.helper.enums.TransactionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface IPaymentTransactionController {

    ResponseEntity<PaymentTransactionDto> createTransaction(@Valid @NotNull PaymentTransactionDto transactionDto);

    ResponseEntity<PaymentTransactionDto> updateTransaction(Long id, @Valid @NotNull PaymentTransactionDto transactionDto);

    ResponseEntity<PaymentTransactionDto> getTransactionById(Long id);

    ResponseEntity<List<PaymentTransactionDto>> getTransactionsByPaymentId(Long paymentId);

    ResponseEntity<List<PaymentTransactionDto>> getTransactionsByPaymentIdOrderByCreatedAt(Long paymentId);

    ResponseEntity<List<PaymentTransactionDto>> getTransactionsByType(TransactionType type);

    ResponseEntity<List<PaymentTransactionDto>> getTransactionsByStatus(TransactionStatus status);

    ResponseEntity<List<PaymentTransactionDto>> getTransactionsByPaymentIdAndType(Long paymentId, TransactionType type);

    ResponseEntity<List<PaymentTransactionDto>> getTransactionsByPaymentIdAndStatus(Long paymentId, TransactionStatus status);

    ResponseEntity<PaymentTransactionDto> getTransactionByGatewayTransactionId(String gatewayTransactionId);

    ResponseEntity<List<PaymentTransactionDto>> getTransactionsByPaymentIdAndTypeAndStatus(Long paymentId, TransactionType type, TransactionStatus status);

    ResponseEntity<List<PaymentTransactionDto>> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    ResponseEntity<List<PaymentTransactionDto>> getTransactionsByUserId(Long userId);

    ResponseEntity<PaymentTransactionDto> updateTransactionStatus(Long id, TransactionStatus status);

    ResponseEntity<Long> countTransactionsByTypeAndStatus(TransactionType type, TransactionStatus status);

    ResponseEntity<Void> deleteTransaction(Long id);

}
