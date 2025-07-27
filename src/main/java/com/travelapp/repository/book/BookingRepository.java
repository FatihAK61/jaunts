package com.travelapp.repository.book;

import com.travelapp.helper.enums.BookingStatus;
import com.travelapp.helper.enums.PaymentStatus;
import com.travelapp.models.book.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    @Query("SELECT b FROM Booking b WHERE b.id = :id")
    Optional<Booking> findBookingById(Long id);

    Optional<Booking> findByBookingNumber(String bookingNumber);

    List<Booking> findByUserId(Long userId);

    List<Booking> findByTourId(Long tourId);

    List<Booking> findByScheduleId(Long scheduleId);

    List<Booking> findByStatus(BookingStatus status);

    List<Booking> findByPaymentStatus(PaymentStatus paymentStatus);

    Page<Booking> findByUserId(Long userId, Pageable pageable);

    Page<Booking> findByUserIdAndStatus(Long userId, BookingStatus status, Pageable pageable);

    Page<Booking> findByTourId(Long tourId, Pageable pageable);

    Page<Booking> findByScheduleId(Long scheduleId, Pageable pageable);

    Page<Booking> findByStatus(BookingStatus status, Pageable pageable);

    Page<Booking> findByPaymentStatus(PaymentStatus paymentStatus, Pageable pageable);

    @Query("SELECT b FROM Booking b WHERE b.createdAt BETWEEN :startDate AND :endDate")
    List<Booking> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate);

    @Query("SELECT b FROM Booking b WHERE b.confirmedAt BETWEEN :startDate AND :endDate")
    List<Booking> findByConfirmedAtBetween(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    @Query("SELECT b FROM Booking b WHERE b.expiresAt < :currentTime AND b.status = :status")
    List<Booking> findExpiredBookings(@Param("currentTime") LocalDateTime currentTime,
                                      @Param("status") BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.status = :status")
    List<Booking> findByUserIdAndStatus(@Param("userId") Long userId,
                                        @Param("status") BookingStatus status);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.tour.id = :tourId AND b.status IN :statuses")
    Long countByTourIdAndStatusIn(@Param("tourId") Long tourId,
                                  @Param("statuses") List<BookingStatus> statuses);

    @Query("SELECT SUM(b.numberOfPeople) FROM Booking b WHERE b.schedule.id = :scheduleId AND b.status IN :statuses")
    Integer sumParticipantsByScheduleIdAndStatusIn(@Param("scheduleId") Long scheduleId,
                                                   @Param("statuses") List<BookingStatus> statuses);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.tour.id = :tourId")
    List<Booking> findByUserIdAndTourId(@Param("userId") Long userId,
                                        @Param("tourId") Long tourId);

    boolean existsByBookingNumber(String bookingNumber);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.tour.id = :tourId")
    Long countByTourId(@Param("tourId") Long tourId);

    @Query("SELECT SUM(b.numberOfPeople) FROM Booking b WHERE b.schedule.id = :scheduleId")
    Integer sumParticipantsByScheduleId(@Param("scheduleId") Long scheduleId);

    @Query("SELECT b FROM Booking b WHERE b.schedule.id = :scheduleId AND b.status IN :statuses")
    List<Booking> findByScheduleIdAndStatusIn(@Param("scheduleId") Long scheduleId,
                                              @Param("statuses") List<BookingStatus> statuses);

    @Query("SELECT b FROM Booking b WHERE b.createdAt >= :startDate AND b.createdAt <= :endDate")
    List<Booking> findBookingsByDateRange(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

    @Query("SELECT b FROM Booking b WHERE b.confirmedAt >= :startDate AND b.confirmedAt <= :endDate")
    List<Booking> findConfirmedBookingsByDateRange(@Param("startDate") LocalDateTime startDate,
                                                   @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.user.id = :userId AND b.tour.id = :tourId")
    Long countByUserIdAndTourId(@Param("userId") Long userId, @Param("tourId") Long tourId);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.schedule.id = :scheduleId AND b.status IN ('CONFIRMED', 'PENDING')")
    Long countActiveBookingsByScheduleId(@Param("scheduleId") Long scheduleId);

    @Query("SELECT COALESCE(SUM(b.numberOfPeople), 0) FROM Booking b WHERE b.schedule.id = :scheduleId AND b.status IN ('CONFIRMED', 'PENDING')")
    Integer sumActiveParticipantsByScheduleId(@Param("scheduleId") Long scheduleId);

    @Query("SELECT b FROM Booking b WHERE b.paymentStatus = :paymentStatus AND b.createdAt BETWEEN :startDate AND :endDate")
    List<Booking> findByPaymentStatusAndDateRange(@Param("paymentStatus") PaymentStatus paymentStatus,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.user.id = :userId AND b.status = :status")
    Long countByUserIdAndStatus(@Param("userId") Long userId, @Param("status") BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.createdAt > :date ORDER BY b.createdAt DESC")
    List<Booking> findRecentBookings(@Param("date") LocalDateTime date);

    @Query("SELECT b FROM Booking b WHERE b.expiresAt < :date AND b.status = 'PENDING'")
    List<Booking> findExpiredPendingBookings(@Param("date") LocalDateTime date);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.paymentStatus = :paymentStatus")
    Long countByPaymentStatus(@Param("paymentStatus") PaymentStatus paymentStatus);

    @Query("SELECT b FROM Booking b WHERE b.tour.id = :tourId AND b.status = :status")
    List<Booking> findByTourIdAndStatus(@Param("tourId") Long tourId, @Param("status") BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.schedule.id = :scheduleId AND b.status = :status")
    List<Booking> findByScheduleIdAndStatus(@Param("scheduleId") Long scheduleId, @Param("status") BookingStatus status);

    @Query("SELECT b FROM Booking b ORDER BY b.createdAt DESC")
    List<Booking> findLatestBookings(Pageable pageable);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId ORDER BY b.createdAt DESC")
    List<Booking> findLatestBookingsByUserId(@Param("userId") Long userId, Pageable pageable);
}
