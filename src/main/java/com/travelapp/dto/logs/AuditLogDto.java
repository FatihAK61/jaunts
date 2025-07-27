package com.travelapp.dto.logs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travelapp.dto.users.UserBaseDto;
import com.travelapp.helper.enums.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditLogDto {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private AuditAction action;
    private String additionalInfo;
    private String entityType;
    private String entityId;
    private String ipAddress;
    private String newValues;
    private String oldValues;
    private String requestMethod;
    private String requestUrl;
    private String sessionId;
    private String userAgent;

    private Long userId;
    private UserBaseDto user;
}
