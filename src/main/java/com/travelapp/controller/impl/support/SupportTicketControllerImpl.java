package com.travelapp.controller.impl.support;

import com.travelapp.controller.support.ISupportTicketController;
import com.travelapp.dto.support.SupportTicketDto;
import com.travelapp.dto.support.TicketMessageDto;
import com.travelapp.helper.enums.TicketCategory;
import com.travelapp.helper.enums.TicketPriority;
import com.travelapp.helper.enums.TicketStatus;
import com.travelapp.services.support.ISupportTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/support-ticket")
@RequiredArgsConstructor
public class SupportTicketControllerImpl implements ISupportTicketController {

    private final ISupportTicketService supportTicketService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<SupportTicketDto> createTicket(@RequestBody SupportTicketDto ticketDto) {
        SupportTicketDto createdTicket = supportTicketService.createTicket(ticketDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    @Override
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<SupportTicketDto> updateTicket(@PathVariable(name = "id") Long id, @RequestBody SupportTicketDto ticketDto) {
        SupportTicketDto updatedTicket = supportTicketService.updateTicket(id, ticketDto);
        return ResponseEntity.ok(updatedTicket);
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<SupportTicketDto> getTicketById(@PathVariable(name = "id") Long id) {
        SupportTicketDto ticket = supportTicketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    @Override
    @GetMapping(path = "/ticket-number/{ticketNumber}")
    public ResponseEntity<SupportTicketDto> getTicketByTicketNumber(@PathVariable(name = "ticketNumber") String ticketNumber) {
        SupportTicketDto ticket = supportTicketService.getTicketByTicketNumber(ticketNumber);
        return ResponseEntity.ok(ticket);
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable(name = "id") Long id) {
        supportTicketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping(path = "/all")
    public ResponseEntity<List<SupportTicketDto>> getAllTickets() {
        List<SupportTicketDto> tickets = supportTicketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/all/paginated")
    public ResponseEntity<Page<SupportTicketDto>> getAllTicketsWithPagination(Pageable pageable) {
        Page<SupportTicketDto> tickets = supportTicketService.getAllTickets(pageable);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<SupportTicketDto>> getTicketsByUserId(@PathVariable(name = "userId") Long userId) {
        List<SupportTicketDto> tickets = supportTicketService.getTicketsByUserId(userId);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/user/{userId}/paginated")
    public ResponseEntity<Page<SupportTicketDto>> getTicketsByUserIdWithPagination(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        Page<SupportTicketDto> tickets = supportTicketService.getTicketsByUserId(userId, pageable);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/status/{status}")
    public ResponseEntity<List<SupportTicketDto>> getTicketsByStatus(@PathVariable(name = "status") TicketStatus status) {
        List<SupportTicketDto> tickets = supportTicketService.getTicketsByStatus(status);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/status/{status}/paginated")
    public ResponseEntity<Page<SupportTicketDto>> getTicketsByStatusWithPagination(@PathVariable(name = "status") TicketStatus status, Pageable pageable) {
        Page<SupportTicketDto> tickets = supportTicketService.getTicketsByStatus(status, pageable);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @PutMapping(path = "/{id}/status")
    public ResponseEntity<SupportTicketDto> updateTicketStatus(@PathVariable(name = "id") Long id, @RequestParam TicketStatus status) {
        SupportTicketDto updatedTicket = supportTicketService.updateTicketStatus(id, status);
        return ResponseEntity.ok(updatedTicket);
    }

    @Override
    @GetMapping(path = "/category/{category}")
    public ResponseEntity<List<SupportTicketDto>> getTicketsByCategory(@PathVariable(name = "category") TicketCategory category) {
        List<SupportTicketDto> tickets = supportTicketService.getTicketsByCategory(category);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/category/{category}/paginated")
    public ResponseEntity<Page<SupportTicketDto>> getTicketsByCategoryWithPagination(@PathVariable(name = "category") TicketCategory category, Pageable pageable) {
        Page<SupportTicketDto> tickets = supportTicketService.getTicketsByCategory(category, pageable);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/priority/{priority}")
    public ResponseEntity<List<SupportTicketDto>> getTicketsByPriority(@PathVariable(name = "priority") TicketPriority priority) {
        List<SupportTicketDto> tickets = supportTicketService.getTicketsByPriority(priority);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/priority/{priority}/paginated")
    public ResponseEntity<Page<SupportTicketDto>> getTicketsByPriorityWithPagination(@PathVariable(name = "priority") TicketPriority priority, Pageable pageable) {
        Page<SupportTicketDto> tickets = supportTicketService.getTicketsByPriority(priority, pageable);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @PutMapping(path = "/{id}/priority")
    public ResponseEntity<SupportTicketDto> updateTicketPriority(@PathVariable(name = "id") Long id, @RequestParam TicketPriority priority) {
        SupportTicketDto updatedTicket = supportTicketService.updateTicketPriority(id, priority);
        return ResponseEntity.ok(updatedTicket);
    }

    @Override
    @GetMapping(path = "/assigned-to/{assignedToId}")
    public ResponseEntity<List<SupportTicketDto>> getTicketsByAssignedTo(@PathVariable(name = "assignedToId") Long assignedToId) {
        List<SupportTicketDto> tickets = supportTicketService.getTicketsByAssignedTo(assignedToId);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/assigned-to/{assignedToId}/paginated")
    public ResponseEntity<Page<SupportTicketDto>> getTicketsByAssignedToWithPagination(@PathVariable(name = "assignedToId") Long assignedToId, Pageable pageable) {
        Page<SupportTicketDto> tickets = supportTicketService.getTicketsByAssignedTo(assignedToId, pageable);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @PutMapping(path = "/{id}/assign")
    public ResponseEntity<SupportTicketDto> assignTicket(@PathVariable(name = "id") Long id, @RequestParam Long assignedToId) {
        SupportTicketDto assignedTicket = supportTicketService.assignTicket(id, assignedToId);
        return ResponseEntity.ok(assignedTicket);
    }

    @Override
    @PutMapping(path = "/{id}/unassign")
    public ResponseEntity<SupportTicketDto> unassignTicket(@PathVariable(name = "id") Long id) {
        SupportTicketDto unassignedTicket = supportTicketService.unassignTicket(id);
        return ResponseEntity.ok(unassignedTicket);
    }

    @Override
    @GetMapping(path = "/unassigned/status/{status}")
    public ResponseEntity<List<SupportTicketDto>> getUnassignedTickets(@PathVariable(name = "status") TicketStatus status) {
        List<SupportTicketDto> tickets = supportTicketService.getUnassignedTickets(status);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/unassigned/status/{status}/paginated")
    public ResponseEntity<Page<SupportTicketDto>> getUnassignedTicketsWithPagination(@PathVariable(name = "status") TicketStatus status, Pageable pageable) {
        Page<SupportTicketDto> tickets = supportTicketService.getUnassignedTickets(status, pageable);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/booking/{bookingId}")
    public ResponseEntity<List<SupportTicketDto>> getTicketsByBookingId(@PathVariable(name = "bookingId") Long bookingId) {
        List<SupportTicketDto> tickets = supportTicketService.getTicketsByBookingId(bookingId);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/booking/{bookingId}/paginated")
    public ResponseEntity<Page<SupportTicketDto>> getTicketsByBookingIdWithPagination(@PathVariable(name = "bookingId") Long bookingId, Pageable pageable) {
        Page<SupportTicketDto> tickets = supportTicketService.getTicketsByBookingId(bookingId, pageable);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/date-range")
    public ResponseEntity<List<SupportTicketDto>> getTicketsByDateRange(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<SupportTicketDto> tickets = supportTicketService.getTicketsByDateRange(startDate, endDate);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/date-range/paginated")
    public ResponseEntity<Page<SupportTicketDto>> getTicketsByDateRangeWithPagination(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate, Pageable pageable) {
        Page<SupportTicketDto> tickets = supportTicketService.getTicketsByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/search")
    public ResponseEntity<List<SupportTicketDto>> searchTickets(@RequestParam String keyword) {
        List<SupportTicketDto> tickets = supportTicketService.searchTickets(keyword);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/search/paginated")
    public ResponseEntity<Page<SupportTicketDto>> searchTicketsWithPagination(@RequestParam String keyword, Pageable pageable) {
        Page<SupportTicketDto> tickets = supportTicketService.searchTickets(keyword, pageable);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/filter")
    public ResponseEntity<Page<SupportTicketDto>> getTicketsWithFilters(@RequestParam(required = false) Long userId, @RequestParam(required = false) TicketStatus status, @RequestParam(required = false) TicketCategory category, @RequestParam(required = false) TicketPriority priority, @RequestParam(required = false) Long assignedToId, Pageable pageable) {
        Page<SupportTicketDto> tickets = supportTicketService.getTicketsWithFilters(userId, status, category, priority, assignedToId, pageable);
        return ResponseEntity.ok(tickets);
    }

    @Override
    @GetMapping(path = "/count/status/{status}")
    public ResponseEntity<Long> getTicketCountByStatus(@PathVariable(name = "status") TicketStatus status) {
        Long count = supportTicketService.getTicketCountByStatus(status);
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping(path = "/count/assigned-to/{assignedToId}/status/{status}")
    public ResponseEntity<Long> getTicketCountByAssignedToAndStatus(@PathVariable(name = "assignedToId") Long assignedToId, @PathVariable(name = "status") TicketStatus status) {
        Long count = supportTicketService.getTicketCountByAssignedToAndStatus(assignedToId, status);
        return ResponseEntity.ok(count);
    }

    @Override
    @PutMapping(path = "/{id}/resolve")
    public ResponseEntity<SupportTicketDto> resolveTicket(@PathVariable(name = "id") Long id) {
        SupportTicketDto resolvedTicket = supportTicketService.resolveTicket(id);
        return ResponseEntity.ok(resolvedTicket);
    }

    @Override
    @PutMapping(path = "/{id}/close")
    public ResponseEntity<SupportTicketDto> closeTicket(@PathVariable(name = "id") Long id) {
        SupportTicketDto closedTicket = supportTicketService.closeTicket(id);
        return ResponseEntity.ok(closedTicket);
    }

    @Override
    @PutMapping(path = "/{id}/reopen")
    public ResponseEntity<SupportTicketDto> reopenTicket(@PathVariable(name = "id") Long id) {
        SupportTicketDto reopenedTicket = supportTicketService.reopenTicket(id);
        return ResponseEntity.ok(reopenedTicket);
    }

    @Override
    @PostMapping(path = "/{ticketId}/message")
    public ResponseEntity<TicketMessageDto> addMessage(@PathVariable(name = "ticketId") Long ticketId, @RequestBody TicketMessageDto messageDto) {
        TicketMessageDto addedMessage = supportTicketService.addMessage(ticketId, messageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedMessage);
    }

    @Override
    @GetMapping(path = "/{ticketId}/messages")
    public ResponseEntity<List<TicketMessageDto>> getTicketMessages(@PathVariable(name = "ticketId") Long ticketId) {
        List<TicketMessageDto> messages = supportTicketService.getTicketMessages(ticketId);
        return ResponseEntity.ok(messages);
    }

    @Override
    @GetMapping(path = "/{ticketId}/messages/paginated")
    public ResponseEntity<Page<TicketMessageDto>> getTicketMessagesWithPagination(@PathVariable(name = "ticketId") Long ticketId, Pageable pageable) {
        Page<TicketMessageDto> messages = supportTicketService.getTicketMessages(ticketId, pageable);
        return ResponseEntity.ok(messages);
    }

    @Override
    @GetMapping(path = "/{ticketId}/messages/internal/{isInternal}")
    public ResponseEntity<List<TicketMessageDto>> getTicketMessagesByInternal(@PathVariable(name = "ticketId") Long ticketId, @PathVariable(name = "isInternal") Boolean isInternal) {
        List<TicketMessageDto> messages = supportTicketService.getTicketMessages(ticketId, isInternal);
        return ResponseEntity.ok(messages);
    }

    @Override
    @GetMapping(path = "/{ticketId}/messages/internal/{isInternal}/paginated")
    public ResponseEntity<Page<TicketMessageDto>> getTicketMessagesByInternalWithPagination(@PathVariable(name = "ticketId") Long ticketId, @PathVariable(name = "isInternal") Boolean isInternal, Pageable pageable) {
        Page<TicketMessageDto> messages = supportTicketService.getTicketMessages(ticketId, isInternal, pageable);
        return ResponseEntity.ok(messages);
    }
}
