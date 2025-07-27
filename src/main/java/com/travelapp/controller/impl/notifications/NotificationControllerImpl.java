package com.travelapp.controller.impl.notifications;

import com.travelapp.controller.notifications.INotificationController;
import com.travelapp.dto.notifications.NotificationDto;
import com.travelapp.helper.enums.NotificationChannel;
import com.travelapp.helper.enums.NotificationType;
import com.travelapp.services.notifications.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationControllerImpl implements INotificationController {

    private final INotificationService notificationService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<NotificationDto> createNotification(@RequestBody NotificationDto notificationDto) {
        NotificationDto createdNotification = notificationService.createNotification(notificationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotification);
    }

    @Override
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<NotificationDto> updateNotification(@PathVariable(name = "id") Long id, @RequestBody NotificationDto notificationDto) {
        return ResponseEntity.ok(notificationService.updateNotification(id, notificationDto));
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable(name = "id") Long id) {
        Optional<NotificationDto> notification = notificationService.getNotificationById(id);
        return notification.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping(path = "/all")
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @Override
    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId));
    }

    @Override
    @GetMapping(path = "/user/{userId}/paginated")
    public ResponseEntity<Page<NotificationDto>> getNotificationsByUserIdWithPagination(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId, pageable));
    }

    @Override
    @GetMapping(path = "/user/{userId}/unread")
    public ResponseEntity<List<NotificationDto>> getUnreadNotificationsByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(notificationService.getUnreadNotificationsByUserId(userId));
    }

    @Override
    @GetMapping(path = "/user/{userId}/unread/paginated")
    public ResponseEntity<Page<NotificationDto>> getUnreadNotificationsByUserIdWithPagination(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        return ResponseEntity.ok(notificationService.getUnreadNotificationsByUserId(userId, pageable));
    }

    @Override
    @GetMapping(path = "/user/{userId}/type/{type}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByUserIdAndType(@PathVariable(name = "userId") Long userId, @PathVariable(name = "type") NotificationType type) {
        return ResponseEntity.ok(notificationService.getNotificationsByUserIdAndType(userId, type));
    }

    @Override
    @GetMapping(path = "/user/{userId}/type/{type}/paginated")
    public ResponseEntity<Page<NotificationDto>> getNotificationsByUserIdAndTypeWithPagination(@PathVariable(name = "userId") Long userId, @PathVariable(name = "type") NotificationType type, Pageable pageable) {
        return ResponseEntity.ok(notificationService.getNotificationsByUserIdAndType(userId, type, pageable));
    }

    @Override
    @GetMapping(path = "/user/{userId}/channel/{channel}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByUserIdAndChannel(@PathVariable(name = "userId") Long userId, @PathVariable(name = "channel") NotificationChannel channel) {
        return ResponseEntity.ok(notificationService.getNotificationsByUserIdAndChannel(userId, channel));
    }

    @Override
    @GetMapping(path = "/user/{userId}/channel/{channel}/paginated")
    public ResponseEntity<Page<NotificationDto>> getNotificationsByUserIdAndChannelWithPagination(@PathVariable(name = "userId") Long userId, @PathVariable(name = "channel") NotificationChannel channel, Pageable pageable) {
        return ResponseEntity.ok(notificationService.getNotificationsByUserIdAndChannel(userId, channel, pageable));
    }

    @Override
    @GetMapping(path = "/user/{userId}/read-status/{isRead}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByUserIdAndReadStatus(@PathVariable(name = "userId") Long userId, @PathVariable(name = "isRead") Boolean isRead) {
        return ResponseEntity.ok(notificationService.getNotificationsByUserIdAndReadStatus(userId, isRead));
    }

    @Override
    @GetMapping(path = "/user/{userId}/read-status/{isRead}/paginated")
    public ResponseEntity<Page<NotificationDto>> getNotificationsByUserIdAndReadStatusWithPagination(@PathVariable(name = "userId") Long userId, @PathVariable(name = "isRead") Boolean isRead, Pageable pageable) {
        return ResponseEntity.ok(notificationService.getNotificationsByUserIdAndReadStatus(userId, isRead, pageable));
    }

    @Override
    @GetMapping(path = "/unsent")
    public ResponseEntity<List<NotificationDto>> getUnsentNotifications() {
        return ResponseEntity.ok(notificationService.getUnsentNotifications());
    }

    @Override
    @GetMapping(path = "/user/{userId}/unsent")
    public ResponseEntity<List<NotificationDto>> getUnsentNotificationsByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(notificationService.getUnsentNotificationsByUserId(userId));
    }

    @Override
    @GetMapping(path = "/external/{externalId}")
    public ResponseEntity<NotificationDto> getNotificationByExternalId(@PathVariable(name = "externalId") String externalId) {
        Optional<NotificationDto> notification = notificationService.getNotificationByExternalId(externalId);
        return notification.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping(path = "/count/unread/user/{userId}")
    public ResponseEntity<Long> countUnreadNotificationsByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(notificationService.countUnreadNotificationsByUserId(userId));
    }

    @Override
    @GetMapping(path = "/count/user/{userId}/type/{type}")
    public ResponseEntity<Long> countNotificationsByUserIdAndType(@PathVariable(name = "userId") Long userId, @PathVariable(name = "type") NotificationType type) {
        return ResponseEntity.ok(notificationService.countNotificationsByUserIdAndType(userId, type));
    }

    @Override
    @PutMapping(path = "/{notificationId}/mark-read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable(name = "notificationId") Long notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.ok().build();
    }

    @Override
    @PutMapping(path = "/user/{userId}/mark-all-read")
    public ResponseEntity<Void> markAllNotificationsAsReadByUserId(@PathVariable(name = "userId") Long userId) {
        notificationService.markAllNotificationsAsReadByUserId(userId);
        return ResponseEntity.ok().build();
    }

    @Override
    @PutMapping(path = "/{notificationId}/mark-sent")
    public ResponseEntity<Void> markNotificationAsSent(@PathVariable(name = "notificationId") Long notificationId) {
        notificationService.markNotificationAsSent(notificationId);
        return ResponseEntity.ok().build();
    }

    @Override
    @PutMapping(path = "/{notificationId}/mark-sent-external")
    public ResponseEntity<Void> markNotificationAsSentWithExternalId(@PathVariable(name = "notificationId") Long notificationId, @RequestParam String externalId) {
        notificationService.markNotificationAsSent(notificationId, externalId);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping(path = "/user/{userId}/date-range")
    public ResponseEntity<List<NotificationDto>> getNotificationsByUserIdAndDateRange(@PathVariable(name = "userId") Long userId, @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(notificationService.getNotificationsByUserIdAndDateRange(userId, startDate, endDate));
    }

    @Override
    @GetMapping(path = "/user/{userId}/recent")
    public ResponseEntity<List<NotificationDto>> getRecentNotifications(@PathVariable(name = "userId") Long userId, @RequestParam LocalDateTime sinceDate) {
        return ResponseEntity.ok(notificationService.getRecentNotifications(userId, sinceDate));
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable(name = "id") Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping(path = "/old")
    public ResponseEntity<Void> deleteOldNotifications(@RequestParam LocalDateTime beforeDate) {
        notificationService.deleteOldNotifications(beforeDate);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping(path = "/user/{userId}")
    public ResponseEntity<Void> deleteNotificationsByUserId(@PathVariable(name = "userId") Long userId) {
        notificationService.deleteNotificationsByUserId(userId);
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping(path = "/bulk")
    public ResponseEntity<List<NotificationDto>> createBulkNotifications(@RequestBody List<NotificationDto> notificationDtos) {
        List<NotificationDto> createdNotifications = notificationService.createBulkNotifications(notificationDtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotifications);
    }

    @Override
    @PutMapping(path = "/mark-multiple-read")
    public ResponseEntity<Void> markMultipleNotificationsAsRead(@RequestBody List<Long> notificationIds) {
        notificationService.markMultipleNotificationsAsRead(notificationIds);
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping(path = "/create-and-send")
    public ResponseEntity<NotificationDto> createAndSendNotification(@RequestParam Long userId, @RequestParam String title, @RequestParam String message, @RequestParam NotificationType type, @RequestParam NotificationChannel channel) {
        NotificationDto notification = notificationService.createAndSendNotification(userId, title, message, type, channel);
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }

    @Override
    @PostMapping(path = "/create-and-send-with-action")
    public ResponseEntity<NotificationDto> createAndSendNotificationWithAction(@RequestParam Long userId, @RequestParam String title, @RequestParam String message, @RequestParam NotificationType type, @RequestParam NotificationChannel channel, @RequestParam String actionUrl) {
        NotificationDto notification = notificationService.createAndSendNotification(userId, title, message, type, channel, actionUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }
}
