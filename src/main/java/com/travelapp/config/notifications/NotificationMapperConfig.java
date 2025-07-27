package com.travelapp.config.notifications;

import com.travelapp.dto.notifications.NotificationDto;
import com.travelapp.models.notifications.Notification;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NotificationMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(Notification.class, NotificationDto.class)
                .addMapping(src -> src.getUser().getId(), NotificationDto::setUserId)
                .addMappings(mapping -> {
                    mapping.skip(NotificationDto::setUserId);
                });

        modelMapper.createTypeMap(NotificationDto.class, Notification.class)
                .addMappings(mapping -> {
                    mapping.skip(Notification::setId);
                    mapping.skip(Notification::setUser);
                    mapping.skip(Notification::setCreatedAt);
                    mapping.skip(Notification::setUpdatedAt);
                });
    }
}
