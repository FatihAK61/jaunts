package com.travelapp.services.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelapp.helper.enums.ErrorSeverity;
import com.travelapp.models.logs.ErrorLog;
import com.travelapp.repository.logs.ErrorLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ErrorLogService {

    private final ErrorLogRepository errorLogRepository;
    private final ObjectMapper objectMapper;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logError(String traceId, String errorCode, String errorMessage, String requestPath, Integer httpStatus, Exception exception, HttpServletRequest request) {
        try {
            ErrorLog errorLog = ErrorLog.builder()
                    .traceId(traceId)
                    .errorCode(errorCode)
                    .errorMessage(errorMessage)
                    .requestPath(requestPath)
                    .httpMethod(request != null ? request.getMethod() : null)
                    .httpStatus(httpStatus)
                    .clientIp(getClientIpAddress(request))
                    .userAgent(request != null ? request.getHeader("User-Agent") : null)
                    .exceptionClass(exception != null ? exception.getClass().getSimpleName() : null)
                    .stackTrace(getStackTrace(exception))
                    .requestHeaders(getRequestHeaders(request))
                    .requestParameters(getRequestParameters(request))
                    .sessionId(getSessionId(request))
                    .userId(getUserId(request))
                    .severity(determineSeverity(httpStatus, exception))
                    .resolved(false)
                    .build();

            errorLogRepository.save(errorLog);
            log.debug("Error logged to database with traceId: {}", traceId);

        } catch (Exception e) {
            log.error("Failed to save error log to database for traceId: {}. Error: {}",
                    traceId, e.getMessage(), e);
        }
    }

    @Transactional
    public void markAsResolved(String traceId, String resolvedBy, String resolutionNotes) {
        errorLogRepository.findByTraceId(traceId)
                .ifPresent(errorLog -> {
                    errorLog.setResolved(true);
                    errorLog.setResolvedAt(LocalDateTime.now());
                    errorLog.setResolvedBy(resolvedBy);
                    errorLog.setResolutionNotes(resolutionNotes);
                    errorLogRepository.save(errorLog);
                });
    }

    public List<ErrorLog> getUnresolvedErrors() {
        return errorLogRepository.findByResolvedFalseOrderByCreatedAtDesc(
                org.springframework.data.domain.PageRequest.of(0, 100)
        ).getContent();
    }

    public List<ErrorLog> getErrorsByCode(String errorCode) {
        return errorLogRepository.findByErrorCodeOrderByCreatedAtDesc(errorCode);
    }

    public List<ErrorLog> getErrorsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return errorLogRepository.findByDateRange(startDate, endDate);
    }

    public Map<String, Long> getErrorStatistics(LocalDateTime since) {
        List<Object[]> results = errorLogRepository.getErrorStatistics(since);
        Map<String, Long> statistics = new HashMap<>();

        for (Object[] result : results) {
            statistics.put((String) result[0], (Long) result[1]);
        }

        return statistics;
    }

    private String getClientIpAddress(HttpServletRequest request) {
        if (request == null) return null;

        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }

    private String getStackTrace(Exception exception) {
        if (exception == null) return null;

        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            String stackTrace = sw.toString();

            // Stack trace'i maksimum 10000 karakter ile sınırla
            return stackTrace.length() > 10000 ? stackTrace.substring(0, 10000) + "..." : stackTrace;
        } catch (Exception e) {
            return "Could not generate stack trace: " + e.getMessage();
        }
    }

    private String getRequestHeaders(HttpServletRequest request) {
        if (request == null) return null;

        try {
            Map<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = request.getHeaderNames();

            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);

                if (isSensitiveHeader(headerName)) {
                    headerValue = "[REDACTED]";
                }

                headers.put(headerName, headerValue);
            }

            String headersJson = objectMapper.writeValueAsString(headers);
            return headersJson.length() > 2000 ? headersJson.substring(0, 2000) + "..." : headersJson;

        } catch (Exception e) {
            return "Could not serialize headers: " + e.getMessage();
        }
    }

    private String getRequestParameters(HttpServletRequest request) {
        if (request == null) return null;

        try {
            Map<String, String[]> parameters = request.getParameterMap();
            Map<String, Object> sanitizedParams = new HashMap<>();

            for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
                String key = entry.getKey();
                String[] values = entry.getValue();

                if (isSensitiveParameter(key)) {
                    sanitizedParams.put(key, "[REDACTED]");
                } else {
                    sanitizedParams.put(key, values.length == 1 ? values[0] : values);
                }
            }

            String paramsJson = objectMapper.writeValueAsString(sanitizedParams);
            return paramsJson.length() > 2000 ? paramsJson.substring(0, 2000) + "..." : paramsJson;

        } catch (Exception e) {
            return "Could not serialize parameters: " + e.getMessage();
        }
    }

    private String getSessionId(HttpServletRequest request) {
        if (request == null || request.getSession(false) == null) return null;
        return request.getSession(false).getId();
    }

    private String getUserId(HttpServletRequest request) {
        if (request == null) return null;

        // Buraya authentication mekanizmanıza göre user ID çekme logic'i ekleyin
        // Örnek: JWT token'dan user ID çekme, session'dan user bilgisi alma vs.

        // Spring Security kullanıyorsanız:
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
        //     return auth.getName();
        // }

        return request.getHeader("X-User-ID"); // veya başka bir header'dan
    }

    private ErrorSeverity determineSeverity(Integer httpStatus, Exception exception) {
        if (httpStatus == null) return ErrorSeverity.MEDIUM;

        if (httpStatus >= 500) {
            return ErrorSeverity.CRITICAL;
        } else if (httpStatus == 429 || httpStatus == 403) {
            return ErrorSeverity.HIGH;
        } else if (httpStatus >= 400) {
            return ErrorSeverity.MEDIUM;
        } else {
            return ErrorSeverity.LOW;
        }
    }

    private boolean isSensitiveHeader(String headerName) {
        if (headerName == null) return false;

        String lowerName = headerName.toLowerCase();
        return lowerName.contains("authorization") ||
                lowerName.contains("token") ||
                lowerName.contains("password") ||
                lowerName.contains("secret") ||
                lowerName.contains("key");
    }

    private boolean isSensitiveParameter(String paramName) {
        if (paramName == null) return false;

        String lowerName = paramName.toLowerCase();
        return lowerName.contains("password") ||
                lowerName.contains("token") ||
                lowerName.contains("secret") ||
                lowerName.contains("key") ||
                lowerName.contains("pin") ||
                lowerName.contains("ssn") ||
                lowerName.contains("credit");
    }
}
