package com.travelapp.repository.support;

import com.travelapp.helper.enums.TicketCategory;
import com.travelapp.helper.enums.TicketPriority;
import com.travelapp.helper.enums.TicketStatus;
import com.travelapp.models.support.SupportTicket;
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
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long>, JpaSpecificationExecutor<SupportTicket> {

    Optional<SupportTicket> findByTicketNumber(String ticketNumber);

    List<SupportTicket> findByUserId(Long userId);

    Page<SupportTicket> findByUserId(Long userId, Pageable pageable);

    List<SupportTicket> findByStatus(TicketStatus status);

    Page<SupportTicket> findByStatus(TicketStatus status, Pageable pageable);

    List<SupportTicket> findByCategory(TicketCategory category);

    Page<SupportTicket> findByCategory(TicketCategory category, Pageable pageable);

    List<SupportTicket> findByPriority(TicketPriority priority);

    Page<SupportTicket> findByPriority(TicketPriority priority, Pageable pageable);

    List<SupportTicket> findByAssignedToId(Long assignedToId);

    Page<SupportTicket> findByAssignedToId(Long assignedToId, Pageable pageable);

    List<SupportTicket> findByBookingId(Long bookingId);

    Page<SupportTicket> findByBookingId(Long bookingId, Pageable pageable);

    @Query("SELECT st FROM SupportTicket st WHERE st.createdAt BETWEEN :startDate AND :endDate")
    List<SupportTicket> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT st FROM SupportTicket st WHERE st.createdAt BETWEEN :startDate AND :endDate")
    Page<SupportTicket> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

    @Query("SELECT st FROM SupportTicket st WHERE st.status = :status AND st.assignedTo IS NULL")
    List<SupportTicket> findUnassignedTicketsByStatus(@Param("status") TicketStatus status);

    @Query("SELECT st FROM SupportTicket st WHERE st.status = :status AND st.assignedTo IS NULL")
    Page<SupportTicket> findUnassignedTicketsByStatus(@Param("status") TicketStatus status, Pageable pageable);

    @Query("SELECT st FROM SupportTicket st WHERE st.status IN :statuses")
    List<SupportTicket> findByStatusIn(@Param("statuses") List<TicketStatus> statuses);

    @Query("SELECT st FROM SupportTicket st WHERE st.status IN :statuses")
    Page<SupportTicket> findByStatusIn(@Param("statuses") List<TicketStatus> statuses, Pageable pageable);

    @Query("SELECT st FROM SupportTicket st WHERE " +
            "(:userId IS NULL OR st.user.id = :userId) AND " +
            "(:status IS NULL OR st.status = :status) AND " +
            "(:category IS NULL OR st.category = :category) AND " +
            "(:priority IS NULL OR st.priority = :priority) AND " +
            "(:assignedToId IS NULL OR st.assignedTo.id = :assignedToId)")
    Page<SupportTicket> findTicketsWithFilters(@Param("userId") Long userId, @Param("status") TicketStatus status, @Param("category") TicketCategory category, @Param("priority") TicketPriority priority, @Param("assignedToId") Long assignedToId, Pageable pageable);

    @Query("SELECT COUNT(st) FROM SupportTicket st WHERE st.status = :status")
    Long countByStatus(@Param("status") TicketStatus status);

    @Query("SELECT COUNT(st) FROM SupportTicket st WHERE st.assignedTo.id = :assignedToId AND st.status = :status")
    Long countByAssignedToIdAndStatus(@Param("assignedToId") Long assignedToId, @Param("status") TicketStatus status);

    @Query("SELECT st FROM SupportTicket st WHERE " +
            "LOWER(st.subject) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(st.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<SupportTicket> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT st FROM SupportTicket st WHERE " +
            "LOWER(st.subject) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(st.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<SupportTicket> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
