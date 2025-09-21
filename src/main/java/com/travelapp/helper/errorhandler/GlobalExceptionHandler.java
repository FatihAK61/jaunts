package com.travelapp.helper.errorhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelapp.services.error.ErrorLogService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.util.*;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private static final String GENERIC_ERROR_MESSAGE = "An unexpected error occurred. Please try again later.";
    private static final String TRACE_ID_HEADER = "X-Trace-ID";
    private final ErrorLogService errorLogService;
    private final ObjectMapper objectMapper;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.warn("Resource not found [traceId={}]: {}", traceId, ex.getMessage());

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                ex.getErrorCode(),
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.of(
                ex.getErrorCode(),
                ex.getMessage(),
                request.getRequestURI(),
                ex.getStatus().value(),
                traceId
        );

        return ResponseEntity.status(ex.getStatus())
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.warn("Business exception occurred [traceId={}]: {}", traceId, ex.getMessage(), ex);

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                ex.getErrorCode(),
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.of(
                ex.getErrorCode(),
                ex.getMessage(),
                request.getRequestURI(),
                ex.getStatus().value(),
                traceId
        );

        return ResponseEntity.status(ex.getStatus())
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.warn("Validation error occurred [traceId={}]", traceId);
        List<ErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorResponse.FieldError(
                        error.getField(),
                        sanitizeValue(error.getRejectedValue()),
                        error.getDefaultMessage()
                ))
                .toList();

        String errorMessage = "Validation failed for one or more fields";

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                "VALIDATION_ERROR",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.withFieldErrors(
                "VALIDATION_ERROR",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                traceId,
                fieldErrors
        );

        return ResponseEntity.badRequest()
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.warn("Constraint violation occurred [traceId={}]: {}", traceId, ex.getMessage());
        List<ErrorResponse.FieldError> fieldErrors = ex.getConstraintViolations()
                .stream()
                .map(violation -> new ErrorResponse.FieldError(
                        violation.getPropertyPath().toString(),
                        sanitizeValue(violation.getInvalidValue()),
                        violation.getMessage()
                ))
                .toList();

        String errorMessage = "Request parameters validation failed";

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                "CONSTRAINT_VIOLATION",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.withFieldErrors(
                "CONSTRAINT_VIOLATION",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                traceId,
                fieldErrors
        );

        return ResponseEntity.badRequest()
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.warn("Method not supported [traceId={}]: {} for {}", traceId, ex.getMethod(), request.getRequestURI());
        String errorMessage = String.format("HTTP method '%s' is not supported for this endpoint", ex.getMethod());

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                "METHOD_NOT_SUPPORTED",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.of(
                "METHOD_NOT_SUPPORTED",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                traceId
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.warn("Media type not supported [traceId={}]: {}", traceId, ex.getContentType());
        String errorMessage = "The media type is not supported for this request";

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                "MEDIA_TYPE_NOT_SUPPORTED",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.of(
                "MEDIA_TYPE_NOT_SUPPORTED",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                traceId
        );

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.warn("Missing request parameter [traceId={}]: {}", traceId, ex.getParameterName());
        String errorMessage = String.format("Required parameter '%s' is missing", ex.getParameterName());

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                "MISSING_PARAMETER",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.of(
                "MISSING_PARAMETER",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                traceId
        );

        return ResponseEntity.badRequest()
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.warn("Type mismatch [traceId={}]: {} for parameter {}", traceId, ex.getValue(), ex.getName());
        String expectedType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown";
        String errorMessage = String.format("Parameter '%s' should be of type %s", ex.getName(), expectedType);

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                "TYPE_MISMATCH",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.of(
                "TYPE_MISMATCH",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                traceId
        );

        return ResponseEntity.badRequest()
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.warn("Access denied [traceId={}] for path: {}", traceId, request.getRequestURI());
        String errorMessage = "You don't have permission to access this resource";

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                "ACCESS_DENIED",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.of(
                "ACCESS_DENIED",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.FORBIDDEN.value(),
                traceId
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.error("Data integrity violation [traceId={}], error {}", traceId, ex.getMostSpecificCause().getMessage());
        String message = "The operation violates data integrity constraints";
        if (ex.getCause() instanceof ConstraintViolationException)
            message = "A unique constraint has been violated";

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                "DATA_INTEGRITY_VIOLATION",
                message,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.of(
                "DATA_INTEGRITY_VIOLATION",
                message,
                request.getRequestURI(),
                HttpStatus.CONFLICT.value(),
                traceId
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleRateLimit(RateLimitExceededException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.warn("Rate limit exceeded [traceId={}] for IP: {}", traceId, getClientIpAddress(request));
        String errorMessage = "Too many requests. Please try again later.";

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                "RATE_LIMIT_EXCEEDED",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.of(
                "RATE_LIMIT_EXCEEDED",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.TOO_MANY_REQUESTS.value(),
                traceId
        );

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseError(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.warn("JSON parse error [traceId={}], error {}", traceId, ex.getMostSpecificCause().getMessage());
        String errorMessage = "Invalid JSON format in request body";

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                "INVALID_JSON",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.of(
                "INVALID_JSON",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                traceId
        );

        return ResponseEntity.badRequest()
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.warn("Duplicate resource: {}", ex.getLocalizedMessage());
        String errorMessage = "Veritabanında aynı kayıt zaten bulunuyor.";

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                "DUPLICATE_RECORD_ERROR",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.of(
                "DUPLICATE_RECORD_ERROR",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                traceId
        );
        return ResponseEntity.badRequest()
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOperationException(InvalidOperationException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.warn("Invalid operation: {}", ex.getLocalizedMessage());
        String errorMessage = "Geçersiz işlem hatası.";

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                "INVALID_OPERATION_ERROR",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.of(
                "INVALID_OPERATION_ERROR",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                traceId
        );
        return ResponseEntity.badRequest()
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateOperationException(IllegalStateException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.warn("Invalid operation: {}", ex.getLocalizedMessage());
        String errorMessage = "Geçersiz işlem hatası.";

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                "INVALID_OPERATION_ERROR",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.of(
                "INVALID_OPERATION_ERROR",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                traceId
        );
        return ResponseEntity.badRequest()
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.warn("Entity not found: {}", ex.getLocalizedMessage());
        String errorMessage = "Entity not found: " + ex.getLocalizedMessage();

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                "ENTITY_NOT_FOUND_ERROR",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.NOT_FOUND.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.of(
                "ENTITY_NOT_FOUND_ERROR",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.NOT_FOUND.value(),
                traceId
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> handleEntityExistsException(EntityExistsException ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.warn("Entity exists: {}", ex.getLocalizedMessage());
        String errorMessage = "Entity already exists: " + ex.getLocalizedMessage();

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                "INTERNAL_SERVER_ERROR",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.of(
                "ENTITY_EXISTS_ERROR",
                errorMessage,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                traceId
        );
        return ResponseEntity.badRequest()
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        String traceId = generateTraceId();
        log.error("Unexpected error occurred [traceId={}], error: {}", traceId, ex.getMessage());

        String httpMethod = request != null ? request.getMethod() : null;
        String clientIp = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : null;
        String requestHeaders = getRequestHeaders(request);
        String requestParameters = getRequestParameters(request);
        String sessionId = request != null && request.getSession(false) != null ? request.getSession(false).getId() : null;
        String userId = getUserId(request);

        errorLogService.logError(
                traceId,
                "INTERNAL_SERVER_ERROR",
                GENERIC_ERROR_MESSAGE,
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex,
                httpMethod,
                clientIp,
                userAgent,
                requestHeaders,
                requestParameters,
                sessionId,
                userId
        );

        ErrorResponse error = ErrorResponse.of(
                "INTERNAL_SERVER_ERROR",
                GENERIC_ERROR_MESSAGE,
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                traceId
        );

        return ResponseEntity.internalServerError()
                .header(TRACE_ID_HEADER, traceId)
                .body(error);
    }

    private String generateTraceId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String[] headerNames = {
                "X-Forwarded-For",
                "X-Real-IP",
                "X-Client-IP",
                "X-Forwarded",
                "X-Cluster-Client-IP",
                "Forwarded-For",
                "Forwarded"
        };

        for (String headerName : headerNames) {
            String ip = request.getHeader(headerName);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                if (ip.contains(",")) {
                    ip = ip.split(",")[0].trim();
                }

                if (isValidIPv4(ip)) {
                    return ip;
                }
            }
        }

        String remoteAddr = request.getRemoteAddr();

        if (isIPv6(remoteAddr)) {
            return "127.0.0.1";
        }

        if (isValidIPv4(remoteAddr)) {
            return remoteAddr;
        }

        return "127.0.0.1";
    }

    private boolean isValidIPv4(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }

        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            return false;
        }

        try {
            for (String part : parts) {
                int num = Integer.parseInt(part);
                if (num < 0 || num > 255) {
                    return false;
                }
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isIPv6(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }

        return ip.contains(":") && (ip.length() > 15 || ip.equals("::1"));
    }

    private Object sanitizeValue(Object value) {
        if (value == null) return null;

        String str = value.toString();
        if (str.toLowerCase().contains("password") ||
                str.toLowerCase().contains("token") ||
                str.toLowerCase().contains("secret")) {
            return "[REDACTED]";
        }

        return str.length() > 100 ? str.substring(0, 100) + "..." : str;
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
