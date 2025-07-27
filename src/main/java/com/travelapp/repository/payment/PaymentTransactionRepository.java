package com.travelapp.repository.payment;

import com.travelapp.helper.enums.TransactionStatus;
import com.travelapp.helper.enums.TransactionType;
import com.travelapp.models.payment.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long>, JpaSpecificationExecutor<PaymentTransaction> {

    List<PaymentTransaction> findByPaymentId(Long paymentId);

    List<PaymentTransaction> findByPaymentIdOrderByCreatedAtDesc(Long paymentId);

    List<PaymentTransaction> findByType(TransactionType type);

    List<PaymentTransaction> findByStatus(TransactionStatus status);

    List<PaymentTransaction> findByPaymentIdAndType(Long paymentId, TransactionType type);

    List<PaymentTransaction> findByPaymentIdAndStatus(Long paymentId, TransactionStatus status);

    Optional<PaymentTransaction> findByGatewayTransactionId(String gatewayTransactionId);

    @Query("SELECT pt FROM PaymentTransaction pt WHERE pt.payment.id = :paymentId AND pt.type = :type AND pt.status = :status")
    List<PaymentTransaction> findByPaymentIdAndTypeAndStatus(@Param("paymentId") Long paymentId, @Param("type") TransactionType type, @Param("status") TransactionStatus status);

    @Query("SELECT pt FROM PaymentTransaction pt WHERE pt.createdAt BETWEEN :startDate AND :endDate")
    List<PaymentTransaction> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT pt FROM PaymentTransaction pt WHERE pt.payment.booking.user.id = :userId ORDER BY pt.createdAt DESC")
    List<PaymentTransaction> findByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(pt) FROM PaymentTransaction pt WHERE pt.type = :type AND pt.status = :status")
    Long countByTypeAndStatus(@Param("type") TransactionType type, @Param("status") TransactionStatus status);

}
