package com.travelapp.services.impl.notifications.mappers;

import com.travelapp.dto.notifications.NotificationDto;
import com.travelapp.models.notifications.Notification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationMapperService {

    private final ModelMapper modelMapper;

    public NotificationDto toDto(Notification notification) {
        if (notification == null)
            return null;

        NotificationDto dto = modelMapper.map(notification, NotificationDto.class);

        if (notification.getUser() != null)
            dto.setUserId(notification.getUser().getId());

        return dto;
    }

    public Notification toEntity(NotificationDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, Notification.class);
    }

    public List<NotificationDto> toDtoList(List<Notification> notifications) {
        if (notifications == null) {
            return null;
        }

        return notifications.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<NotificationDto> toDtoSet(Set<Notification> notifications) {
        if (notifications == null) {
            return null;
        }

        return notifications.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<Notification> toEntityList(List<NotificationDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(NotificationDto dto, Notification existingNotification) {
        if (dto == null || existingNotification == null) {
            return;
        }

        modelMapper.map(dto, existingNotification);
    }
}
