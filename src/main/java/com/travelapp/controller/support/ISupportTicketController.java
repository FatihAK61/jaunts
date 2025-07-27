package com.travelapp.controller.support;

import com.travelapp.dto.support.SupportTicketDto;
import com.travelapp.dto.support.TicketMessageDto;
import com.travelapp.helper.enums.TicketCategory;
import com.travelapp.helper.enums.TicketPriority;
import com.travelapp.helper.enums.TicketStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface ISupportTicketController {

    ResponseEntity<SupportTicketDto> createTicket(@Valid @NotNull SupportTicketDto ticketDto);

    ResponseEntity<SupportTicketDto> updateTicket(Long id, @Valid @NotNull SupportTicketDto ticketDto);

    ResponseEntity<SupportTicketDto> getTicketById(Long id);

    ResponseEntity<SupportTicketDto> getTicketByTicketNumber(String ticketNumber);

    ResponseEntity<Void> deleteTicket(Long id);

    ResponseEntity<List<SupportTicketDto>> getAllTickets();

    ResponseEntity<Page<SupportTicketDto>> getAllTicketsWithPagination(Pageable pageable);

    ResponseEntity<List<SupportTicketDto>> getTicketsByUserId(Long userId);

    ResponseEntity<Page<SupportTicketDto>> getTicketsByUserIdWithPagination(Long userId, Pageable pageable);

    ResponseEntity<List<SupportTicketDto>> getTicketsByStatus(TicketStatus status);

    ResponseEntity<Page<SupportTicketDto>> getTicketsByStatusWithPagination(TicketStatus status, Pageable pageable);

    ResponseEntity<SupportTicketDto> updateTicketStatus(Long id, TicketStatus status);

    ResponseEntity<List<SupportTicketDto>> getTicketsByCategory(TicketCategory category);

    ResponseEntity<Page<SupportTicketDto>> getTicketsByCategoryWithPagination(TicketCategory category, Pageable pageable);

    ResponseEntity<List<SupportTicketDto>> getTicketsByPriority(TicketPriority priority);

    ResponseEntity<Page<SupportTicketDto>> getTicketsByPriorityWithPagination(TicketPriority priority, Pageable pageable);

    ResponseEntity<SupportTicketDto> updateTicketPriority(Long id, TicketPriority priority);

    ResponseEntity<List<SupportTicketDto>> getTicketsByAssignedTo(Long assignedToId);

    ResponseEntity<Page<SupportTicketDto>> getTicketsByAssignedToWithPagination(Long assignedToId, Pageable pageable);

    ResponseEntity<SupportTicketDto> assignTicket(Long id, Long assignedToId);

    ResponseEntity<SupportTicketDto> unassignTicket(Long id);

    ResponseEntity<List<SupportTicketDto>> getUnassignedTickets(TicketStatus status);

    ResponseEntity<Page<SupportTicketDto>> getUnassignedTicketsWithPagination(TicketStatus status, Pageable pageable);

    ResponseEntity<List<SupportTicketDto>> getTicketsByBookingId(Long bookingId);

    ResponseEntity<Page<SupportTicketDto>> getTicketsByBookingIdWithPagination(Long bookingId, Pageable pageable);

    ResponseEntity<List<SupportTicketDto>> getTicketsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    ResponseEntity<Page<SupportTicketDto>> getTicketsByDateRangeWithPagination(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    ResponseEntity<List<SupportTicketDto>> searchTickets(String keyword);

    ResponseEntity<Page<SupportTicketDto>> searchTicketsWithPagination(String keyword, Pageable pageable);

    ResponseEntity<Page<SupportTicketDto>> getTicketsWithFilters(Long userId, TicketStatus status, TicketCategory category, TicketPriority priority, Long assignedToId, Pageable pageable);

    ResponseEntity<Long> getTicketCountByStatus(TicketStatus status);

    ResponseEntity<Long> getTicketCountByAssignedToAndStatus(Long assignedToId, TicketStatus status);

    ResponseEntity<SupportTicketDto> resolveTicket(Long id);

    ResponseEntity<SupportTicketDto> closeTicket(Long id);

    ResponseEntity<SupportTicketDto> reopenTicket(Long id);

    ResponseEntity<TicketMessageDto> addMessage(Long ticketId, @Valid @NotNull TicketMessageDto messageDto);

    ResponseEntity<List<TicketMessageDto>> getTicketMessages(Long ticketId);

    ResponseEntity<Page<TicketMessageDto>> getTicketMessagesWithPagination(Long ticketId, Pageable pageable);

    ResponseEntity<List<TicketMessageDto>> getTicketMessagesByInternal(Long ticketId, Boolean isInternal);

    ResponseEntity<Page<TicketMessageDto>> getTicketMessagesByInternalWithPagination(Long ticketId, Boolean isInternal, Pageable pageable);

}
