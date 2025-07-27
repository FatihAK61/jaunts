package com.travelapp.repository.book;

import com.travelapp.models.book.BookingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingParticipantRepository extends JpaRepository<BookingParticipant, Long>, JpaSpecificationExecutor<BookingParticipant> {

    List<BookingParticipant> findByBookingId(Long bookingId);

    @Query("SELECT bp FROM BookingParticipant bp WHERE bp.booking.id = :bookingId")
    List<BookingParticipant> findParticipantsByBookingId(@Param("bookingId") Long bookingId);

    Optional<BookingParticipant> findByBookingIdAndId(Long bookingId, Long participantId);

    @Query("SELECT bp FROM BookingParticipant bp WHERE bp.email = :email")
    List<BookingParticipant> findByEmail(@Param("email") String email);

    @Query("SELECT bp FROM BookingParticipant bp WHERE bp.passportNumber = :passportNumber")
    Optional<BookingParticipant> findByPassportNumber(@Param("passportNumber") String passportNumber);

    @Query("SELECT COUNT(bp) FROM BookingParticipant bp WHERE bp.booking.id = :bookingId")
    Long countByBookingId(@Param("bookingId") Long bookingId);

    void deleteByBookingId(Long bookingId);
}
