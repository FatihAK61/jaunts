package com.travelapp.services.impl.notifications;

import com.travelapp.dto.notifications.NotificationDto;
import com.travelapp.helper.enums.NotificationChannel;
import com.travelapp.helper.enums.NotificationType;
import com.travelapp.helper.errorhandler.ResourceNotFoundException;
import com.travelapp.models.notifications.Notification;
import com.travelapp.models.users.UserBase;
import com.travelapp.repository.notifications.NotificationRepository;
import com.travelapp.repository.users.UserBaseRepository;
import com.travelapp.services.impl.notifications.mappers.NotificationMapperService;
import com.travelapp.services.notifications.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements INotificationService {

    private final NotificationRepository notificationRepository;
    private final UserBaseRepository userBaseRepository;
    private final NotificationMapperService notificationMapperService;

    @Override
    public NotificationDto createNotification(NotificationDto notificationDto) {
        Notification notification = notificationMapperService.toEntity(notificationDto);

        if (notificationDto.getUserId() != null) {
            UserBase user = userBaseRepository.findById(notificationDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: ", notificationDto.getUserId().toString()));
            notification.setUser(user);
        }

        if (notification.getIsRead() == null) {
            notification.setIsRead(false);
        }
        if (notification.getSent() == null) {
            notification.setSent(false);
        }

        Notification savedNotification = notificationRepository.save(notification);

        return notificationMapperService.toDto(savedNotification);
    }

    @Override
    public NotificationDto updateNotification(Long id, NotificationDto notificationDto) {
        Notification existingNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: ", id.toString()));

        notificationMapperService.updateEntityFromDto(notificationDto, existingNotification);

        Notification updatedNotification = notificationRepository.save(existingNotification);

        return notificationMapperService.toDto(updatedNotification);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotificationDto> getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .map(notificationMapperService::toDto);
    }

    @Override
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found with id: ", id.toString());
        }

        notificationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notificationMapperService.toDtoList(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationsByUserId(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return notificationMapperService.toDtoList(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationDto> getNotificationsByUserId(Long userId, Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        return notifications.map(notificationMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getUnreadNotificationsByUserId(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
        return notificationMapperService.toDtoList(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationDto> getUnreadNotificationsByUserId(Long userId, Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId, pageable);
        return notifications.map(notificationMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationsByUserIdAndType(Long userId, NotificationType type) {
        List<Notification> notifications = notificationRepository.findByUserIdAndTypeOrderByCreatedAtDesc(userId, type);
        return notificationMapperService.toDtoList(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationDto> getNotificationsByUserIdAndType(Long userId, NotificationType type, Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findByUserIdAndTypeOrderByCreatedAtDesc(userId, type, pageable);
        return notifications.map(notificationMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationsByUserIdAndChannel(Long userId, NotificationChannel channel) {
        List<Notification> notifications = notificationRepository.findByUserIdAndChannelOrderByCreatedAtDesc(userId, channel);
        return notificationMapperService.toDtoList(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationDto> getNotificationsByUserIdAndChannel(Long userId, NotificationChannel channel, Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findByUserIdAndChannelOrderByCreatedAtDesc(userId, channel, pageable);
        return notifications.map(notificationMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationsByUserIdAndReadStatus(Long userId, Boolean isRead) {
        List<Notification> notifications = notificationRepository.findByUserIdAndIsReadOrderByCreatedAtDesc(userId, isRead);
        return notificationMapperService.toDtoList(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationDto> getNotificationsByUserIdAndReadStatus(Long userId, Boolean isRead, Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findByUserIdAndIsReadOrderByCreatedAtDesc(userId, isRead, pageable);
        return notifications.map(notificationMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getUnsentNotifications() {
        List<Notification> notifications = notificationRepository.findBySentFalseOrderByCreatedAtAsc();
        return notificationMapperService.toDtoList(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getUnsentNotificationsByUserId(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndSentFalseOrderByCreatedAtAsc(userId);
        return notificationMapperService.toDtoList(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotificationDto> getNotificationByExternalId(String externalId) {
        return notificationRepository.findByExternalId(externalId)
                .map(notificationMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countUnreadNotificationsByUserId(Long userId) {
        return notificationRepository.countUnreadNotificationsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countNotificationsByUserIdAndType(Long userId, NotificationType type) {
        return notificationRepository.countNotificationsByUserIdAndType(userId, type);
    }

    @Override
    public void markNotificationAsRead(Long notificationId) {
        int updatedCount = notificationRepository.markAsRead(notificationId, LocalDateTime.now());
        if (updatedCount == 0) {
            throw new ResourceNotFoundException("Notification not found with id: ", notificationId.toString());
        }
    }

    @Override
    public void markAllNotificationsAsReadByUserId(Long userId) {
        int updatedCount = notificationRepository.markAllAsReadByUserId(userId, LocalDateTime.now());
    }

    @Override
    public void markNotificationAsSent(Long notificationId) {
        int updatedCount = notificationRepository.markAsSent(notificationId, LocalDateTime.now());
        if (updatedCount == 0) {
            throw new ResourceNotFoundException("Notification not found with id: ", notificationId.toString());
        }
    }

    @Override
    public void markNotificationAsSent(Long notificationId, String externalId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: ", notificationId.toString()));

        notification.setSent(true);
        notification.setSentAt(LocalDateTime.now());
        notification.setExternalId(externalId);

        notificationRepository.save(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getNotificationsByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Notification> notifications = notificationRepository.findByUserIdAndCreatedAtBetween(userId, startDate, endDate);
        return notificationMapperService.toDtoList(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getRecentNotifications(Long userId, LocalDateTime sinceDate) {
        List<Notification> notifications = notificationRepository.findRecentNotifications(userId, sinceDate);
        return notificationMapperService.toDtoList(notifications);
    }

    @Override
    public void deleteOldNotifications(LocalDateTime beforeDate) {
        int deletedCount = notificationRepository.deleteOldNotifications(beforeDate);
    }

    @Override
    public void deleteNotificationsByUserId(Long userId) {
        int deletedCount = notificationRepository.deleteByUserId(userId);
    }

    @Override
    public List<NotificationDto> createBulkNotifications(List<NotificationDto> notificationDtos) {
        List<Notification> notifications = notificationDtos.stream()
                .map(dto -> {
                    Notification notification = notificationMapperService.toEntity(dto);

                    if (dto.getUserId() != null) {
                        UserBase user = userBaseRepository.findById(dto.getUserId())
                                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: ", dto.getUserId().toString()));
                        notification.setUser(user);
                    }

                    if (notification.getIsRead() == null) {
                        notification.setIsRead(false);
                    }
                    if (notification.getSent() == null) {
                        notification.setSent(false);
                    }

                    return notification;
                })
                .collect(Collectors.toList());

        List<Notification> savedNotifications = notificationRepository.saveAll(notifications);

        return notificationMapperService.toDtoList(savedNotifications);
    }

    @Override
    public void markMultipleNotificationsAsRead(List<Long> notificationIds) {
        LocalDateTime now = LocalDateTime.now();
        int updatedCount = 0;

        for (Long notificationId : notificationIds) {
            updatedCount += notificationRepository.markAsRead(notificationId, now);
        }
    }

    @Override
    public NotificationDto createAndSendNotification(Long userId, String title, String message, NotificationType type, NotificationChannel channel) {
        return createAndSendNotification(userId, title, message, type, channel, null);
    }

    @Override
    public NotificationDto createAndSendNotification(Long userId, String title, String message, NotificationType type, NotificationChannel channel, String actionUrl) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setUserId(userId);
        notificationDto.setTitle(title);
        notificationDto.setMessage(message);
        notificationDto.setType(type);
        notificationDto.setChannel(channel);
        notificationDto.setActionUrl(actionUrl);
        notificationDto.setIsRead(false);
        notificationDto.setSent(false);

        // Here you would typically integrate with your notification sending service
        // For now, we'll just mark it as sent
        // TODO: Integrate with actual notification sending service (FCM, Email, SMS, etc.)

        return createNotification(notificationDto);
    }
}
