package com.travelapp.controller.notifications;

import com.travelapp.dto.notifications.NotificationDto;
import com.travelapp.helper.enums.NotificationChannel;
import com.travelapp.helper.enums.NotificationType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface INotificationController {

    ResponseEntity<NotificationDto> createNotification(@Valid @NotNull NotificationDto notificationDto);

    ResponseEntity<NotificationDto> updateNotification(Long id, @Valid @NotNull NotificationDto notificationDto);

    ResponseEntity<NotificationDto> getNotificationById(Long id);

    ResponseEntity<List<NotificationDto>> getAllNotifications();

    ResponseEntity<List<NotificationDto>> getNotificationsByUserId(Long userId);

    ResponseEntity<Page<NotificationDto>> getNotificationsByUserIdWithPagination(Long userId, Pageable pageable);

    ResponseEntity<List<NotificationDto>> getUnreadNotificationsByUserId(Long userId);

    ResponseEntity<Page<NotificationDto>> getUnreadNotificationsByUserIdWithPagination(Long userId, Pageable pageable);

    ResponseEntity<List<NotificationDto>> getNotificationsByUserIdAndType(Long userId, NotificationType type);

    ResponseEntity<Page<NotificationDto>> getNotificationsByUserIdAndTypeWithPagination(Long userId, NotificationType type, Pageable pageable);

    ResponseEntity<List<NotificationDto>> getNotificationsByUserIdAndChannel(Long userId, NotificationChannel channel);

    ResponseEntity<Page<NotificationDto>> getNotificationsByUserIdAndChannelWithPagination(Long userId, NotificationChannel channel, Pageable pageable);

    ResponseEntity<List<NotificationDto>> getNotificationsByUserIdAndReadStatus(Long userId, Boolean isRead);

    ResponseEntity<Page<NotificationDto>> getNotificationsByUserIdAndReadStatusWithPagination(Long userId, Boolean isRead, Pageable pageable);

    ResponseEntity<List<NotificationDto>> getUnsentNotifications();

    ResponseEntity<List<NotificationDto>> getUnsentNotificationsByUserId(Long userId);

    ResponseEntity<NotificationDto> getNotificationByExternalId(String externalId);

    ResponseEntity<Long> countUnreadNotificationsByUserId(Long userId);

    ResponseEntity<Long> countNotificationsByUserIdAndType(Long userId, NotificationType type);

    ResponseEntity<Void> markNotificationAsRead(Long notificationId);

    ResponseEntity<Void> markAllNotificationsAsReadByUserId(Long userId);

    ResponseEntity<Void> markNotificationAsSent(Long notificationId);

    ResponseEntity<Void> markNotificationAsSentWithExternalId(Long notificationId, String externalId);

    ResponseEntity<List<NotificationDto>> getNotificationsByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    ResponseEntity<List<NotificationDto>> getRecentNotifications(Long userId, LocalDateTime sinceDate);

    ResponseEntity<Void> deleteNotification(Long id);

    ResponseEntity<Void> deleteOldNotifications(LocalDateTime beforeDate);

    ResponseEntity<Void> deleteNotificationsByUserId(Long userId);

    ResponseEntity<List<NotificationDto>> createBulkNotifications(@Valid @NotNull List<NotificationDto> notificationDtos);

    ResponseEntity<Void> markMultipleNotificationsAsRead(List<Long> notificationIds);

    ResponseEntity<NotificationDto> createAndSendNotification(Long userId, String title, String message, NotificationType type, NotificationChannel channel);

    ResponseEntity<NotificationDto> createAndSendNotificationWithAction(Long userId, String title, String message, NotificationType type, NotificationChannel channel, String actionUrl);

}
