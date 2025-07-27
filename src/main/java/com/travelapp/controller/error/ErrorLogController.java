package com.travelapp.controller.error;

import com.travelapp.models.logs.ErrorLog;
import com.travelapp.services.error.ErrorLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/error-logs")
@RequiredArgsConstructor
public class ErrorLogController {

    private final ErrorLogService errorLogService;

    @GetMapping("/unresolved")
    public ResponseEntity<List<ErrorLog>> getUnresolvedErrors() {
        List<ErrorLog> errors = errorLogService.getUnresolvedErrors();
        return ResponseEntity.ok(errors);
    }

    @GetMapping("/by-code/{errorCode}")
    public ResponseEntity<List<ErrorLog>> getErrorsByCode(@PathVariable String errorCode) {
        List<ErrorLog> errors = errorLogService.getErrorsByCode(errorCode);
        return ResponseEntity.ok(errors);
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<ErrorLog>> getErrorsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<ErrorLog> errors = errorLogService.getErrorsByDateRange(startDate, endDate);
        return ResponseEntity.ok(errors);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getErrorStatistics(@RequestParam(defaultValue = "24") int hoursBack) {
        LocalDateTime since = LocalDateTime.now().minusHours(hoursBack);
        Map<String, Long> statistics = errorLogService.getErrorStatistics(since);
        return ResponseEntity.ok(statistics);
    }

    @PutMapping("/{traceId}/resolve")
    public ResponseEntity<Void> markAsResolved(@PathVariable String traceId, @RequestParam String resolvedBy, @RequestParam(required = false) String resolutionNotes) {
        errorLogService.markAsResolved(traceId, resolvedBy, resolutionNotes);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{traceId}")
    public ResponseEntity<ErrorLog> getErrorByTraceId(@PathVariable String traceId) {
        // Bu metodu ErrorLogService'e eklemen gerekecek
        return ResponseEntity.ok().build();
    }
}
