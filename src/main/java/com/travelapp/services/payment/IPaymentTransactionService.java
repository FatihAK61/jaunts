package com.travelapp.services.payment;

import com.travelapp.dto.payment.PaymentTransactionDto;
import com.travelapp.helper.enums.TransactionStatus;
import com.travelapp.helper.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.List;

public interface IPaymentTransactionService {

    PaymentTransactionDto createTransaction(PaymentTransactionDto transactionDto);

    PaymentTransactionDto updateTransaction(PaymentTransactionDto transactionDto);

    PaymentTransactionDto getTransactionById(Long id);

    List<PaymentTransactionDto> getTransactionsByPaymentId(Long paymentId);

    List<PaymentTransactionDto> getTransactionsByPaymentIdOrderByCreatedAt(Long paymentId);

    List<PaymentTransactionDto> getTransactionsByType(TransactionType type);

    List<PaymentTransactionDto> getTransactionsByStatus(TransactionStatus status);

    List<PaymentTransactionDto> getTransactionsByPaymentIdAndType(Long paymentId, TransactionType type);

    List<PaymentTransactionDto> getTransactionsByPaymentIdAndStatus(Long paymentId, TransactionStatus status);

    PaymentTransactionDto getTransactionByGatewayTransactionId(String gatewayTransactionId);

    List<PaymentTransactionDto> getTransactionsByPaymentIdAndTypeAndStatus(Long paymentId, TransactionType type, TransactionStatus status);

    List<PaymentTransactionDto> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<PaymentTransactionDto> getTransactionsByUserId(Long userId);

    PaymentTransactionDto updateTransactionStatus(Long id, TransactionStatus status);

    Long countTransactionsByTypeAndStatus(TransactionType type, TransactionStatus status);

    void deleteTransaction(Long id);

}
