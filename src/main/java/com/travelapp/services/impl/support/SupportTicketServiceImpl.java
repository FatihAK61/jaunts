package com.travelapp.services.impl.support;

import com.travelapp.dto.support.SupportTicketDto;
import com.travelapp.dto.support.TicketMessageDto;
import com.travelapp.helper.enums.TicketCategory;
import com.travelapp.helper.enums.TicketPriority;
import com.travelapp.helper.enums.TicketStatus;
import com.travelapp.helper.errorhandler.ResourceNotFoundException;
import com.travelapp.models.book.Booking;
import com.travelapp.models.support.SupportTicket;
import com.travelapp.models.support.TicketMessage;
import com.travelapp.models.users.UserBase;
import com.travelapp.repository.book.BookingRepository;
import com.travelapp.repository.support.SupportTicketRepository;
import com.travelapp.repository.support.TicketMessageRepository;
import com.travelapp.repository.users.UserBaseRepository;
import com.travelapp.services.impl.support.mappers.SupportTicketMapperService;
import com.travelapp.services.support.ISupportTicketService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SupportTicketServiceImpl implements ISupportTicketService {

    private final SupportTicketRepository supportTicketRepository;
    private final TicketMessageRepository ticketMessageRepository;
    private final UserBaseRepository userBaseRepository;
    private final BookingRepository bookingRepository;
    private final SupportTicketMapperService mapperService;

    @Override
    public SupportTicketDto createTicket(SupportTicketDto ticketDto) {
        SupportTicket ticket = mapperService.toEntity(ticketDto);

        UserBase user = userBaseRepository.findById(ticketDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + ticketDto.getUserId()));
        ticket.setUser(user);

        if (ticketDto.getBookingId() != null) {
            Booking booking = bookingRepository.findById(ticketDto.getBookingId())
                    .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + ticketDto.getBookingId()));
            ticket.setBooking(booking);
        }

        if (ticketDto.getAssignedTo() != null) {
            UserBase assignedUser = userBaseRepository.findById(ticketDto.getAssignedTo())
                    .orElseThrow(() -> new EntityNotFoundException("Assigned user not found with id: " + ticketDto.getAssignedTo()));
            ticket.setAssignedTo(assignedUser);
            ticket.setAssignedAt(LocalDateTime.now());
        }

        ticket.setTicketNumber(generateTicketNumber());

        if (ticket.getStatus() == null) {
            ticket.setStatus(TicketStatus.OPEN);
        }
        if (ticket.getPriority() == null) {
            ticket.setPriority(TicketPriority.MEDIUM);
        }

        SupportTicket savedTicket = supportTicketRepository.save(ticket);

        return mapperService.toDto(savedTicket);
    }

    @Override
    public SupportTicketDto updateTicket(Long id, SupportTicketDto ticketDto) {
        SupportTicket existingTicket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Support ticket not found with id: ", id.toString()));

        mapperService.updateEntityFromDto(ticketDto, existingTicket);

        if (ticketDto.getAssignedTo() != null) {
            UserBase assignedUser = userBaseRepository.findById(ticketDto.getAssignedTo())
                    .orElseThrow(() -> new EntityNotFoundException("Assigned user not found with id: " + ticketDto.getAssignedTo()));
            existingTicket.setAssignedTo(assignedUser);
            existingTicket.setAssignedAt(LocalDateTime.now());
        } else if (existingTicket.getAssignedTo() != null && ticketDto.getAssignedTo() == null) {
            existingTicket.setAssignedTo(null);
            existingTicket.setAssignedAt(null);
        }

        SupportTicket updatedTicket = supportTicketRepository.save(existingTicket);

        return mapperService.toDto(updatedTicket);
    }

    @Override
    @Transactional(readOnly = true)
    public SupportTicketDto getTicketById(Long id) {
        SupportTicket ticket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Support ticket not found with id: " + id));

        return mapperService.toDto(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public SupportTicketDto getTicketByTicketNumber(String ticketNumber) {
        SupportTicket ticket = supportTicketRepository.findByTicketNumber(ticketNumber)
                .orElseThrow(() -> new EntityNotFoundException("Support ticket not found with ticket number: " + ticketNumber));

        return mapperService.toDto(ticket);
    }

    @Override
    public void deleteTicket(Long id) {
        if (!supportTicketRepository.existsById(id)) {
            throw new EntityNotFoundException("Support ticket not found with id: " + id);
        }

        supportTicketRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicketDto> getAllTickets() {
        List<SupportTicket> tickets = supportTicketRepository.findAll();
        return mapperService.toDtoList(tickets);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportTicketDto> getAllTickets(Pageable pageable) {
        Page<SupportTicket> tickets = supportTicketRepository.findAll(pageable);
        return tickets.map(mapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicketDto> getTicketsByUserId(Long userId) {
        List<SupportTicket> tickets = supportTicketRepository.findByUserId(userId);
        return mapperService.toDtoList(tickets);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportTicketDto> getTicketsByUserId(Long userId, Pageable pageable) {
        Page<SupportTicket> tickets = supportTicketRepository.findByUserId(userId, pageable);
        return tickets.map(mapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicketDto> getTicketsByStatus(TicketStatus status) {
        List<SupportTicket> tickets = supportTicketRepository.findByStatus(status);
        return mapperService.toDtoList(tickets);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportTicketDto> getTicketsByStatus(TicketStatus status, Pageable pageable) {
        Page<SupportTicket> tickets = supportTicketRepository.findByStatus(status, pageable);
        return tickets.map(mapperService::toDto);
    }

    @Override
    public SupportTicketDto updateTicketStatus(Long id, TicketStatus status) {
        SupportTicket ticket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Support ticket not found with id: " + id));

        TicketStatus oldStatus = ticket.getStatus();
        ticket.setStatus(status);

        if (status == TicketStatus.RESOLVED && oldStatus != TicketStatus.RESOLVED) {
            ticket.setResolvedAt(LocalDateTime.now());
        } else if (status == TicketStatus.CLOSED && oldStatus != TicketStatus.CLOSED) {
            ticket.setClosedAt(LocalDateTime.now());
        } else if (status == TicketStatus.OPEN && (oldStatus == TicketStatus.RESOLVED || oldStatus == TicketStatus.CLOSED)) {
            ticket.setResolvedAt(null);
            ticket.setClosedAt(null);
        }

        SupportTicket updatedTicket = supportTicketRepository.save(ticket);

        return mapperService.toDto(updatedTicket);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicketDto> getTicketsByCategory(TicketCategory category) {
        List<SupportTicket> tickets = supportTicketRepository.findByCategory(category);
        return mapperService.toDtoList(tickets);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportTicketDto> getTicketsByCategory(TicketCategory category, Pageable pageable) {
        Page<SupportTicket> tickets = supportTicketRepository.findByCategory(category, pageable);
        return tickets.map(mapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicketDto> getTicketsByPriority(TicketPriority priority) {
        List<SupportTicket> tickets = supportTicketRepository.findByPriority(priority);
        return mapperService.toDtoList(tickets);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportTicketDto> getTicketsByPriority(TicketPriority priority, Pageable pageable) {
        Page<SupportTicket> tickets = supportTicketRepository.findByPriority(priority, pageable);
        return tickets.map(mapperService::toDto);
    }

    @Override
    public SupportTicketDto updateTicketPriority(Long id, TicketPriority priority) {
        SupportTicket ticket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Support ticket not found with id: " + id));

        ticket.setPriority(priority);
        SupportTicket updatedTicket = supportTicketRepository.save(ticket);

        return mapperService.toDto(updatedTicket);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicketDto> getTicketsByAssignedTo(Long assignedToId) {
        List<SupportTicket> tickets = supportTicketRepository.findByAssignedToId(assignedToId);
        return mapperService.toDtoList(tickets);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportTicketDto> getTicketsByAssignedTo(Long assignedToId, Pageable pageable) {
        Page<SupportTicket> tickets = supportTicketRepository.findByAssignedToId(assignedToId, pageable);
        return tickets.map(mapperService::toDto);
    }

    @Override
    public SupportTicketDto assignTicket(Long id, Long assignedToId) {
        SupportTicket ticket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Support ticket not found with id: " + id));

        UserBase assignedUser = userBaseRepository.findById(assignedToId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + assignedToId));

        ticket.setAssignedTo(assignedUser);
        ticket.setAssignedAt(LocalDateTime.now());

        SupportTicket updatedTicket = supportTicketRepository.save(ticket);

        return mapperService.toDto(updatedTicket);
    }

    @Override
    public SupportTicketDto unassignTicket(Long id) {
        SupportTicket ticket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Support ticket not found with id: " + id));

        ticket.setAssignedTo(null);
        ticket.setAssignedAt(null);

        SupportTicket updatedTicket = supportTicketRepository.save(ticket);

        return mapperService.toDto(updatedTicket);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicketDto> getUnassignedTickets(TicketStatus status) {
        List<SupportTicket> tickets = supportTicketRepository.findUnassignedTicketsByStatus(status);
        return mapperService.toDtoList(tickets);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportTicketDto> getUnassignedTickets(TicketStatus status, Pageable pageable) {
        Page<SupportTicket> tickets = supportTicketRepository.findUnassignedTicketsByStatus(status, pageable);
        return tickets.map(mapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicketDto> getTicketsByBookingId(Long bookingId) {
        List<SupportTicket> tickets = supportTicketRepository.findByBookingId(bookingId);
        return mapperService.toDtoList(tickets);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportTicketDto> getTicketsByBookingId(Long bookingId, Pageable pageable) {
        Page<SupportTicket> tickets = supportTicketRepository.findByBookingId(bookingId, pageable);
        return tickets.map(mapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicketDto> getTicketsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<SupportTicket> tickets = supportTicketRepository.findByCreatedAtBetween(startDate, endDate);
        return mapperService.toDtoList(tickets);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportTicketDto> getTicketsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Page<SupportTicket> tickets = supportTicketRepository.findByCreatedAtBetween(startDate, endDate, pageable);
        return tickets.map(mapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicketDto> searchTickets(String keyword) {
        List<SupportTicket> tickets = supportTicketRepository.searchByKeyword(keyword);
        return mapperService.toDtoList(tickets);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportTicketDto> searchTickets(String keyword, Pageable pageable) {
        Page<SupportTicket> tickets = supportTicketRepository.searchByKeyword(keyword, pageable);
        return tickets.map(mapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportTicketDto> getTicketsWithFilters(Long userId, TicketStatus status, TicketCategory category, TicketPriority priority, Long assignedToId, Pageable pageable) {
        Page<SupportTicket> tickets = supportTicketRepository.findTicketsWithFilters(userId, status, category, priority, assignedToId, pageable);
        return tickets.map(mapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getTicketCountByStatus(TicketStatus status) {
        return supportTicketRepository.countByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getTicketCountByAssignedToAndStatus(Long assignedToId, TicketStatus status) {
        return supportTicketRepository.countByAssignedToIdAndStatus(assignedToId, status);
    }

    @Override
    public SupportTicketDto resolveTicket(Long id) {
        return updateTicketStatus(id, TicketStatus.RESOLVED);
    }

    @Override
    public SupportTicketDto closeTicket(Long id) {
        return updateTicketStatus(id, TicketStatus.CLOSED);
    }

    @Override
    public SupportTicketDto reopenTicket(Long id) {
        return updateTicketStatus(id, TicketStatus.OPEN);
    }

    @Override
    public TicketMessageDto addMessage(Long ticketId, TicketMessageDto messageDto) {
        SupportTicket ticket = supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Support ticket not found with id: " + ticketId));

        UserBase sender = userBaseRepository.findById(messageDto.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + messageDto.getSenderId()));

        TicketMessage message = mapperService.messageToEntity(messageDto);
        message.setTicket(ticket);
        message.setSender(sender);

        TicketMessage savedMessage = ticketMessageRepository.save(message);

        return mapperService.messageToDto(savedMessage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketMessageDto> getTicketMessages(Long ticketId) {
        List<TicketMessage> messages = ticketMessageRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);
        return mapperService.messageToDtoList(messages);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketMessageDto> getTicketMessages(Long ticketId, Pageable pageable) {
        Page<TicketMessage> messages = ticketMessageRepository.findByTicketIdOrderByCreatedAtAsc(ticketId, pageable);
        return messages.map(mapperService::messageToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketMessageDto> getTicketMessages(Long ticketId, Boolean isInternal) {
        List<TicketMessage> messages = ticketMessageRepository.findByTicketIdAndIsInternal(ticketId, isInternal);
        return mapperService.messageToDtoList(messages);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketMessageDto> getTicketMessages(Long ticketId, Boolean isInternal, Pageable pageable) {
        Page<TicketMessage> messages = ticketMessageRepository.findByTicketIdAndIsInternal(ticketId, isInternal, pageable);
        return messages.map(mapperService::messageToDto);
    }

    private String generateTicketNumber() {
        String prefix = "TKT";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String random = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return prefix + "-" + timestamp.substring(timestamp.length() - 6) + "-" + random;
    }
}
