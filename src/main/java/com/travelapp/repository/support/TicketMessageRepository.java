package com.travelapp.repository.support;

import com.travelapp.models.support.TicketMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketMessageRepository extends JpaRepository<TicketMessage, Long>, JpaSpecificationExecutor<TicketMessage> {

    List<TicketMessage> findByTicketId(Long ticketId);

    Page<TicketMessage> findByTicketId(Long ticketId, Pageable pageable);

    List<TicketMessage> findByTicketIdOrderByCreatedAtAsc(Long ticketId);

    List<TicketMessage> findByTicketIdOrderByCreatedAtDesc(Long ticketId);

    Page<TicketMessage> findByTicketIdOrderByCreatedAtAsc(Long ticketId, Pageable pageable);

    Page<TicketMessage> findByTicketIdOrderByCreatedAtDesc(Long ticketId, Pageable pageable);

    List<TicketMessage> findBySenderId(Long senderId);

    Page<TicketMessage> findBySenderId(Long senderId, Pageable pageable);

    List<TicketMessage> findByIsInternal(Boolean isInternal);

    Page<TicketMessage> findByIsInternal(Boolean isInternal, Pageable pageable);

    List<TicketMessage> findByTicketIdAndIsInternal(Long ticketId, Boolean isInternal);

    Page<TicketMessage> findByTicketIdAndIsInternal(Long ticketId, Boolean isInternal, Pageable pageable);

    @Query("SELECT tm FROM TicketMessage tm WHERE tm.createdAt BETWEEN :startDate AND :endDate")
    List<TicketMessage> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT tm FROM TicketMessage tm WHERE tm.createdAt BETWEEN :startDate AND :endDate")
    Page<TicketMessage> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

    @Query("SELECT tm FROM TicketMessage tm WHERE tm.ticket.id = :ticketId AND tm.createdAt BETWEEN :startDate AND :endDate")
    List<TicketMessage> findByTicketIdAndCreatedAtBetween(@Param("ticketId") Long ticketId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT tm FROM TicketMessage tm WHERE tm.ticket.id = :ticketId AND LOWER(tm.message) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<TicketMessage> searchInTicketByKeyword(@Param("ticketId") Long ticketId, @Param("keyword") String keyword);

    @Query("SELECT tm FROM TicketMessage tm WHERE tm.ticket.id = :ticketId AND LOWER(tm.message) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<TicketMessage> searchInTicketByKeyword(@Param("ticketId") Long ticketId, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT COUNT(tm) FROM TicketMessage tm WHERE tm.ticket.id = :ticketId")
    Long countByTicketId(@Param("ticketId") Long ticketId);

    @Query("SELECT COUNT(tm) FROM TicketMessage tm WHERE tm.ticket.id = :ticketId AND tm.isInternal = :isInternal")
    Long countByTicketIdAndIsInternal(@Param("ticketId") Long ticketId, @Param("isInternal") Boolean isInternal);

    @Query("SELECT tm FROM TicketMessage tm WHERE tm.ticket.id = :ticketId AND SIZE(tm.attachments) > 0")
    List<TicketMessage> findByTicketIdAndHasAttachments(@Param("ticketId") Long ticketId);

    @Query("SELECT tm FROM TicketMessage tm WHERE tm.ticket.id = :ticketId AND SIZE(tm.attachments) > 0")
    Page<TicketMessage> findByTicketIdAndHasAttachments(@Param("ticketId") Long ticketId, Pageable pageable);

    @Query("SELECT tm FROM TicketMessage tm WHERE tm.ticket.id IN :ticketIds ORDER BY tm.createdAt DESC")
    List<TicketMessage> findLatestMessagesByTicketIds(@Param("ticketIds") List<Long> ticketIds);

}
