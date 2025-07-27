package com.travelapp.helper.errorhandler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String error,
        String message,
        String path,
        int status,
        String timestamp,
        String traceId,
        List<FieldError> fieldErrors
) {
    public static ErrorResponse of(String error, String message, String path, int status, String traceId) {
        return new ErrorResponse(error, message, path, status, Instant.now().toString(), traceId, null);
    }

    public static ErrorResponse withFieldErrors(String error, String message, String path, int status, String traceId, List<FieldError> fieldErrors) {
        return new ErrorResponse(error, message, path, status, Instant.now().toString(), traceId, fieldErrors);
    }

    public record FieldError(String field, Object rejectedValue, String message) {
    }
}
