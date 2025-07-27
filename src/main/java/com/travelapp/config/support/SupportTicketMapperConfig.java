package com.travelapp.config.support;

import com.travelapp.dto.support.SupportTicketDto;
import com.travelapp.dto.support.TicketMessageDto;
import com.travelapp.models.support.SupportTicket;
import com.travelapp.models.support.TicketMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SupportTicketMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(SupportTicket.class, SupportTicketDto.class)
                .addMapping(src -> src.getUser().getId(), SupportTicketDto::setUserId)
                .addMapping(src -> src.getBooking().getId(), SupportTicketDto::setBookingId)
                .addMapping(src -> src.getAssignedTo().getId(), SupportTicketDto::setAssignedTo)
                .addMappings(mapping -> {
                    mapping.skip(SupportTicketDto::setMessages);
                });
        
        modelMapper.createTypeMap(SupportTicketDto.class, SupportTicket.class)
                .addMappings(mapping -> {
                    mapping.skip(SupportTicket::setId);
                    mapping.skip(SupportTicket::setUser);
                    mapping.skip(SupportTicket::setBooking);
                    mapping.skip(SupportTicket::setAssignedTo);
                    mapping.skip(SupportTicket::setCreatedAt);
                    mapping.skip(SupportTicket::setUpdatedAt);
                    mapping.skip(SupportTicket::setMessages);
                });

        modelMapper.createTypeMap(TicketMessage.class, TicketMessageDto.class)
                .addMapping(src -> src.getTicket().getId(), TicketMessageDto::setTicketId)
                .addMapping(src -> src.getSender().getId(), TicketMessageDto::setSenderId);

        modelMapper.createTypeMap(TicketMessageDto.class, TicketMessage.class)
                .addMappings(mapping -> {
                    mapping.skip(TicketMessage::setId);
                    mapping.skip(TicketMessage::setTicket);
                    mapping.skip(TicketMessage::setSender);
                    mapping.skip(TicketMessage::setCreatedAt);
                });
    }
}
