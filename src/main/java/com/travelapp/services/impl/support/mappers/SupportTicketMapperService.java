package com.travelapp.services.impl.support.mappers;

import com.travelapp.dto.support.SupportTicketDto;
import com.travelapp.dto.support.TicketMessageDto;
import com.travelapp.models.support.SupportTicket;
import com.travelapp.models.support.TicketMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupportTicketMapperService {

    private final ModelMapper modelMapper;

    public SupportTicketDto toDto(SupportTicket supportTicket) {
        if (supportTicket == null) {
            return null;
        }

        SupportTicketDto dto = modelMapper.map(supportTicket, SupportTicketDto.class);

        if (supportTicket.getUser() != null) {
            dto.setUserId(supportTicket.getUser().getId());
        }

        if (supportTicket.getBooking() != null) {
            dto.setBookingId(supportTicket.getBooking().getId());
        }

        if (supportTicket.getAssignedTo() != null) {
            dto.setAssignedTo(supportTicket.getAssignedTo().getId());
        }

        if (supportTicket.getMessages() != null) {
            Set<TicketMessageDto> messageDtos = supportTicket.getMessages().stream()
                    .map(this::messageToDto)
                    .collect(Collectors.toSet());
            dto.setMessages(messageDtos);
        }

        return dto;
    }

    public SupportTicket toEntity(SupportTicketDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, SupportTicket.class);
    }

    public List<SupportTicketDto> toDtoList(List<SupportTicket> supportTickets) {
        if (supportTickets == null) {
            return null;
        }

        return supportTickets.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<SupportTicketDto> toDtoSet(Set<SupportTicket> supportTickets) {
        if (supportTickets == null) {
            return null;
        }

        return supportTickets.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<SupportTicket> toEntityList(List<SupportTicketDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(SupportTicketDto dto, SupportTicket existingSupportTicket) {
        if (dto == null || existingSupportTicket == null) {
            return;
        }

        modelMapper.map(dto, existingSupportTicket);
    }

    public TicketMessageDto messageToDto(TicketMessage ticketMessage) {
        if (ticketMessage == null) {
            return null;
        }

        TicketMessageDto dto = modelMapper.map(ticketMessage, TicketMessageDto.class);

        if (ticketMessage.getTicket() != null) {
            dto.setTicketId(ticketMessage.getTicket().getId());
        }

        if (ticketMessage.getSender() != null) {
            dto.setSenderId(ticketMessage.getSender().getId());
        }

        return dto;
    }

    public TicketMessage messageToEntity(TicketMessageDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, TicketMessage.class);
    }

    public List<TicketMessageDto> messageToDtoList(List<TicketMessage> ticketMessages) {
        if (ticketMessages == null) {
            return null;
        }

        return ticketMessages.stream()
                .map(this::messageToDto)
                .collect(Collectors.toList());
    }

    public Set<TicketMessageDto> messageToDtoSet(Set<TicketMessage> ticketMessages) {
        if (ticketMessages == null) {
            return null;
        }

        return ticketMessages.stream()
                .map(this::messageToDto)
                .collect(Collectors.toSet());
    }

    public List<TicketMessage> messageToEntityList(List<TicketMessageDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::messageToEntity)
                .collect(Collectors.toList());
    }

    public void updateMessageEntityFromDto(TicketMessageDto dto, TicketMessage existingTicketMessage) {
        if (dto == null || existingTicketMessage == null) {
            return;
        }

        modelMapper.map(dto, existingTicketMessage);
    }
}
