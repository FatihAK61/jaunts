package com.travelapp.services.notifications;

import com.travelapp.dto.notifications.NotificationDto;
import com.travelapp.helper.enums.NotificationChannel;
import com.travelapp.helper.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface INotificationService {

    NotificationDto createNotification(NotificationDto notificationDto);

    NotificationDto updateNotification(Long id, NotificationDto notificationDto);

    Optional<NotificationDto> getNotificationById(Long id);

    void deleteNotification(Long id);

    List<NotificationDto> getAllNotifications();

    List<NotificationDto> getNotificationsByUserId(Long userId);

    Page<NotificationDto> getNotificationsByUserId(Long userId, Pageable pageable);

    List<NotificationDto> getUnreadNotificationsByUserId(Long userId);

    Page<NotificationDto> getUnreadNotificationsByUserId(Long userId, Pageable pageable);

    List<NotificationDto> getNotificationsByUserIdAndType(Long userId, NotificationType type);

    Page<NotificationDto> getNotificationsByUserIdAndType(Long userId, NotificationType type, Pageable pageable);

    List<NotificationDto> getNotificationsByUserIdAndChannel(Long userId, NotificationChannel channel);

    Page<NotificationDto> getNotificationsByUserIdAndChannel(Long userId, NotificationChannel channel, Pageable pageable);

    List<NotificationDto> getNotificationsByUserIdAndReadStatus(Long userId, Boolean isRead);

    Page<NotificationDto> getNotificationsByUserIdAndReadStatus(Long userId, Boolean isRead, Pageable pageable);

    List<NotificationDto> getUnsentNotifications();

    List<NotificationDto> getUnsentNotificationsByUserId(Long userId);

    Optional<NotificationDto> getNotificationByExternalId(String externalId);

    Long countUnreadNotificationsByUserId(Long userId);

    Long countNotificationsByUserIdAndType(Long userId, NotificationType type);

    void markNotificationAsRead(Long notificationId);

    void markAllNotificationsAsReadByUserId(Long userId);

    void markNotificationAsSent(Long notificationId);

    void markNotificationAsSent(Long notificationId, String externalId);

    List<NotificationDto> getNotificationsByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    List<NotificationDto> getRecentNotifications(Long userId, LocalDateTime sinceDate);

    void deleteOldNotifications(LocalDateTime beforeDate);

    void deleteNotificationsByUserId(Long userId);
    
    List<NotificationDto> createBulkNotifications(List<NotificationDto> notificationDtos);

    void markMultipleNotificationsAsRead(List<Long> notificationIds);

    NotificationDto createAndSendNotification(Long userId, String title, String message, NotificationType type, NotificationChannel channel);

    NotificationDto createAndSendNotification(Long userId, String title, String message, NotificationType type, NotificationChannel channel, String actionUrl);

}
