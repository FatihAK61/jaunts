package com.travelapp.controller.impl.payment;

import com.travelapp.controller.payment.IPaymentTransactionController;
import com.travelapp.dto.payment.PaymentTransactionDto;
import com.travelapp.helper.enums.TransactionStatus;
import com.travelapp.helper.enums.TransactionType;
import com.travelapp.services.payment.IPaymentTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-transaction")
@RequiredArgsConstructor
public class PaymentTransactionControllerImpl implements IPaymentTransactionController {

    private final IPaymentTransactionService paymentTransactionService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<PaymentTransactionDto> createTransaction(@RequestBody PaymentTransactionDto transactionDto) {
        PaymentTransactionDto createdTransaction = paymentTransactionService.createTransaction(transactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }

    @Override
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<PaymentTransactionDto> updateTransaction(@PathVariable(name = "id") Long id, @RequestBody PaymentTransactionDto transactionDto) {
        transactionDto.setId(id);
        return ResponseEntity.ok(paymentTransactionService.updateTransaction(transactionDto));
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<PaymentTransactionDto> getTransactionById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(paymentTransactionService.getTransactionById(id));
    }

    @Override
    @GetMapping(path = "/payment/{paymentId}")
    public ResponseEntity<List<PaymentTransactionDto>> getTransactionsByPaymentId(@PathVariable(name = "paymentId") Long paymentId) {
        return ResponseEntity.ok(paymentTransactionService.getTransactionsByPaymentId(paymentId));
    }

    @Override
    @GetMapping(path = "/payment/{paymentId}/ordered")
    public ResponseEntity<List<PaymentTransactionDto>> getTransactionsByPaymentIdOrderByCreatedAt(@PathVariable(name = "paymentId") Long paymentId) {
        return ResponseEntity.ok(paymentTransactionService.getTransactionsByPaymentIdOrderByCreatedAt(paymentId));
    }

    @Override
    @GetMapping(path = "/type/{type}")
    public ResponseEntity<List<PaymentTransactionDto>> getTransactionsByType(@PathVariable(name = "type") TransactionType type) {
        return ResponseEntity.ok(paymentTransactionService.getTransactionsByType(type));
    }

    @Override
    @GetMapping(path = "/status/{status}")
    public ResponseEntity<List<PaymentTransactionDto>> getTransactionsByStatus(@PathVariable(name = "status") TransactionStatus status) {
        return ResponseEntity.ok(paymentTransactionService.getTransactionsByStatus(status));
    }

    @Override
    @GetMapping(path = "/payment/{paymentId}/type/{type}")
    public ResponseEntity<List<PaymentTransactionDto>> getTransactionsByPaymentIdAndType(@PathVariable(name = "paymentId") Long paymentId, @PathVariable(name = "type") TransactionType type) {
        return ResponseEntity.ok(paymentTransactionService.getTransactionsByPaymentIdAndType(paymentId, type));
    }

    @Override
    @GetMapping(path = "/payment/{paymentId}/status/{status}")
    public ResponseEntity<List<PaymentTransactionDto>> getTransactionsByPaymentIdAndStatus(@PathVariable(name = "paymentId") Long paymentId, @PathVariable(name = "status") TransactionStatus status) {
        return ResponseEntity.ok(paymentTransactionService.getTransactionsByPaymentIdAndStatus(paymentId, status));
    }

    @Override
    @GetMapping(path = "/gateway/{gatewayTransactionId}")
    public ResponseEntity<PaymentTransactionDto> getTransactionByGatewayTransactionId(@PathVariable(name = "gatewayTransactionId") String gatewayTransactionId) {
        PaymentTransactionDto transaction = paymentTransactionService.getTransactionByGatewayTransactionId(gatewayTransactionId);
        if (transaction != null) {
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    @GetMapping(path = "/payment/{paymentId}/type/{type}/status/{status}")
    public ResponseEntity<List<PaymentTransactionDto>> getTransactionsByPaymentIdAndTypeAndStatus(@PathVariable(name = "paymentId") Long paymentId, @PathVariable(name = "type") TransactionType type, @PathVariable(name = "status") TransactionStatus status) {
        return ResponseEntity.ok(paymentTransactionService.getTransactionsByPaymentIdAndTypeAndStatus(paymentId, type, status));
    }

    @Override
    @GetMapping(path = "/date-range")
    public ResponseEntity<List<PaymentTransactionDto>> getTransactionsByDateRange(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(paymentTransactionService.getTransactionsByDateRange(startDate, endDate));
    }

    @Override
    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<PaymentTransactionDto>> getTransactionsByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(paymentTransactionService.getTransactionsByUserId(userId));
    }

    @Override
    @PutMapping(path = "/{id}/status/{status}")
    public ResponseEntity<PaymentTransactionDto> updateTransactionStatus(@PathVariable(name = "id") Long id, @PathVariable(name = "status") TransactionStatus status) {
        return ResponseEntity.ok(paymentTransactionService.updateTransactionStatus(id, status));
    }

    @Override
    @GetMapping(path = "/count/type/{type}/status/{status}")
    public ResponseEntity<Long> countTransactionsByTypeAndStatus(@PathVariable(name = "type") TransactionType type, @PathVariable(name = "status") TransactionStatus status) {
        return ResponseEntity.ok(paymentTransactionService.countTransactionsByTypeAndStatus(type, status));
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable(name = "id") Long id) {
        paymentTransactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
