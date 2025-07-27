package com.travelapp.dto.logs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travelapp.helper.enums.LogLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemLogDto {

    private Long id;

    private Integer lineNumber;

    private LogLevel level;

    private String logger;
    private String message;
    private String stackTrace;
    private String threadName;
    private String className;
    private String methodName;
    private String additionalData;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
