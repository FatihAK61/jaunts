package com.travelapp.repository.payment;

import com.travelapp.helper.enums.PaymentStatus;
import com.travelapp.models.payment.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {

    Optional<Payment> findByPaymentId(String paymentId);

    Optional<Payment> findByTransactionId(String transactionId);

    List<Payment> findByBookingId(Long bookingId);

    List<Payment> findByStatus(PaymentStatus status);

    List<Payment> findByBookingIdAndStatus(Long bookingId, PaymentStatus status);

    Page<Payment> findByBookingUserId(Long userId, Pageable pageable);

    @Query("SELECT p FROM Payment p WHERE p.booking.user.id = :userId AND p.status = :status")
    Page<Payment> findByUserIdAndStatus(@Param("userId") Long userId,
                                        @Param("status") PaymentStatus status,
                                        Pageable pageable);

    @Query("SELECT p FROM Payment p WHERE p.createdAt BETWEEN :startDate AND :endDate")
    List<Payment> findByDateRange(@Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate);

    @Query("SELECT p FROM Payment p WHERE p.status = :status AND p.createdAt < :beforeDate")
    List<Payment> findByStatusAndCreatedBefore(@Param("status") PaymentStatus status,
                                               @Param("beforeDate") LocalDateTime beforeDate);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'COMPLETED' AND p.booking.user.id = :userId")
    BigDecimal getTotalPaidAmountByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = :status")
    Long countByStatus(@Param("status") PaymentStatus status);

    boolean existsByPaymentId(String paymentId);

    boolean existsByTransactionId(String transactionId);

}
