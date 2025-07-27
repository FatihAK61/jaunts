package com.travelapp.dto.logs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travelapp.dto.users.UserBaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginAttemptDto {

    private Long id;

    private Boolean successful;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime attemptedAt;

    private String email;
    private String failureReason;
    private String ipAddress;
    private String userAgent;

    private Long userId;
    private UserBaseDto user;
}
