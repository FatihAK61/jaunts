package com.travelapp.services.support;

import com.travelapp.dto.support.SupportTicketDto;
import com.travelapp.dto.support.TicketMessageDto;
import com.travelapp.helper.enums.TicketCategory;
import com.travelapp.helper.enums.TicketPriority;
import com.travelapp.helper.enums.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ISupportTicketService {

    SupportTicketDto createTicket(SupportTicketDto ticketDto);

    SupportTicketDto updateTicket(Long id, SupportTicketDto ticketDto);

    SupportTicketDto getTicketById(Long id);

    SupportTicketDto getTicketByTicketNumber(String ticketNumber);

    void deleteTicket(Long id);

    List<SupportTicketDto> getAllTickets();

    Page<SupportTicketDto> getAllTickets(Pageable pageable);

    List<SupportTicketDto> getTicketsByUserId(Long userId);

    Page<SupportTicketDto> getTicketsByUserId(Long userId, Pageable pageable);

    List<SupportTicketDto> getTicketsByStatus(TicketStatus status);

    Page<SupportTicketDto> getTicketsByStatus(TicketStatus status, Pageable pageable);

    SupportTicketDto updateTicketStatus(Long id, TicketStatus status);

    List<SupportTicketDto> getTicketsByCategory(TicketCategory category);

    Page<SupportTicketDto> getTicketsByCategory(TicketCategory category, Pageable pageable);

    List<SupportTicketDto> getTicketsByPriority(TicketPriority priority);

    Page<SupportTicketDto> getTicketsByPriority(TicketPriority priority, Pageable pageable);

    SupportTicketDto updateTicketPriority(Long id, TicketPriority priority);

    List<SupportTicketDto> getTicketsByAssignedTo(Long assignedToId);

    Page<SupportTicketDto> getTicketsByAssignedTo(Long assignedToId, Pageable pageable);

    SupportTicketDto assignTicket(Long id, Long assignedToId);

    SupportTicketDto unassignTicket(Long id);

    List<SupportTicketDto> getUnassignedTickets(TicketStatus status);

    Page<SupportTicketDto> getUnassignedTickets(TicketStatus status, Pageable pageable);

    List<SupportTicketDto> getTicketsByBookingId(Long bookingId);

    Page<SupportTicketDto> getTicketsByBookingId(Long bookingId, Pageable pageable);

    List<SupportTicketDto> getTicketsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    Page<SupportTicketDto> getTicketsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    List<SupportTicketDto> searchTickets(String keyword);

    Page<SupportTicketDto> searchTickets(String keyword, Pageable pageable);

    Page<SupportTicketDto> getTicketsWithFilters(Long userId, TicketStatus status, TicketCategory category, TicketPriority priority, Long assignedToId, Pageable pageable);

    Long getTicketCountByStatus(TicketStatus status);

    Long getTicketCountByAssignedToAndStatus(Long assignedToId, TicketStatus status);

    SupportTicketDto resolveTicket(Long id);

    SupportTicketDto closeTicket(Long id);

    SupportTicketDto reopenTicket(Long id);

    TicketMessageDto addMessage(Long ticketId, TicketMessageDto messageDto);

    List<TicketMessageDto> getTicketMessages(Long ticketId);

    Page<TicketMessageDto> getTicketMessages(Long ticketId, Pageable pageable);

    List<TicketMessageDto> getTicketMessages(Long ticketId, Boolean isInternal);

    Page<TicketMessageDto> getTicketMessages(Long ticketId, Boolean isInternal, Pageable pageable);

}
