package com.travelapp.services.impl.payment;

import com.travelapp.dto.payment.PaymentTransactionDto;
import com.travelapp.helper.enums.TransactionStatus;
import com.travelapp.helper.enums.TransactionType;
import com.travelapp.models.payment.Payment;
import com.travelapp.models.payment.PaymentTransaction;
import com.travelapp.repository.payment.PaymentRepository;
import com.travelapp.repository.payment.PaymentTransactionRepository;
import com.travelapp.services.impl.payment.mappers.PaymentTransactionMapperService;
import com.travelapp.services.payment.IPaymentTransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentTransactionServiceImpl implements IPaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentTransactionMapperService paymentTransactionMapperService;

    @Override
    public PaymentTransactionDto createTransaction(PaymentTransactionDto transactionDto) {
        PaymentTransaction transaction = paymentTransactionMapperService.toEntity(transactionDto);
        
        if (transactionDto.getPaymentId() != null) {
            Payment payment = paymentRepository.findById(transactionDto.getPaymentId())
                    .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + transactionDto.getPaymentId()));
            transaction.setPayment(payment);
        }

        PaymentTransaction saved = paymentTransactionRepository.save(transaction);

        return paymentTransactionMapperService.toDto(saved);
    }

    @Override
    public PaymentTransactionDto updateTransaction(PaymentTransactionDto transactionDto) {
        PaymentTransaction existingTransaction = paymentTransactionRepository.findById(transactionDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + transactionDto.getId()));

        paymentTransactionMapperService.updateEntityFromDto(transactionDto, existingTransaction);

        PaymentTransaction updated = paymentTransactionRepository.save(existingTransaction);

        return paymentTransactionMapperService.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentTransactionDto getTransactionById(Long id) {
        PaymentTransaction transaction = paymentTransactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + id));
        return paymentTransactionMapperService.toDto(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentTransactionDto> getTransactionsByPaymentId(Long paymentId) {
        List<PaymentTransaction> transactions = paymentTransactionRepository.findByPaymentId(paymentId);
        return paymentTransactionMapperService.toDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentTransactionDto> getTransactionsByPaymentIdOrderByCreatedAt(Long paymentId) {
        List<PaymentTransaction> transactions = paymentTransactionRepository.findByPaymentIdOrderByCreatedAtDesc(paymentId);
        return paymentTransactionMapperService.toDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentTransactionDto> getTransactionsByType(TransactionType type) {
        List<PaymentTransaction> transactions = paymentTransactionRepository.findByType(type);
        return paymentTransactionMapperService.toDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentTransactionDto> getTransactionsByStatus(TransactionStatus status) {
        List<PaymentTransaction> transactions = paymentTransactionRepository.findByStatus(status);
        return paymentTransactionMapperService.toDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentTransactionDto> getTransactionsByPaymentIdAndType(Long paymentId, TransactionType type) {
        List<PaymentTransaction> transactions = paymentTransactionRepository.findByPaymentIdAndType(paymentId, type);
        return paymentTransactionMapperService.toDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentTransactionDto> getTransactionsByPaymentIdAndStatus(Long paymentId, TransactionStatus status) {
        List<PaymentTransaction> transactions = paymentTransactionRepository.findByPaymentIdAndStatus(paymentId, status);
        return paymentTransactionMapperService.toDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentTransactionDto getTransactionByGatewayTransactionId(String gatewayTransactionId) {
        PaymentTransaction transaction = paymentTransactionRepository.findByGatewayTransactionId(gatewayTransactionId)
                .orElse(null);
        return transaction != null ? paymentTransactionMapperService.toDto(transaction) : null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentTransactionDto> getTransactionsByPaymentIdAndTypeAndStatus(Long paymentId, TransactionType type, TransactionStatus status) {
        List<PaymentTransaction> transactions = paymentTransactionRepository.findByPaymentIdAndTypeAndStatus(paymentId, type, status);
        return paymentTransactionMapperService.toDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentTransactionDto> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<PaymentTransaction> transactions = paymentTransactionRepository.findByDateRange(startDate, endDate);
        return paymentTransactionMapperService.toDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentTransactionDto> getTransactionsByUserId(Long userId) {
        List<PaymentTransaction> transactions = paymentTransactionRepository.findByUserId(userId);
        return paymentTransactionMapperService.toDtoList(transactions);
    }

    @Override
    public PaymentTransactionDto updateTransactionStatus(Long id, TransactionStatus status) {
        PaymentTransaction transaction = paymentTransactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + id));

        transaction.setStatus(status);
        PaymentTransaction updated = paymentTransactionRepository.save(transaction);

        return paymentTransactionMapperService.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countTransactionsByTypeAndStatus(TransactionType type, TransactionStatus status) {
        return paymentTransactionRepository.countByTypeAndStatus(type, status);
    }

    @Override
    public void deleteTransaction(Long id) {
        paymentTransactionRepository.deleteById(id);
    }
}
