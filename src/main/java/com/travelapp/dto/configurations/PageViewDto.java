package com.travelapp.dto.configurations;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageViewDto {
    
    private Long id;

    private Long viewDuration;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime viewedAt;

    private String browser;
    private String device;
    private String ipAddress;
    private String os;
    private String referrer;
    private String sessionId;
    private String url;
    private String userAgent;

    private Long userId;
}
