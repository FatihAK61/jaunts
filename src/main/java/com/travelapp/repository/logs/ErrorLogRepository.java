package com.travelapp.repository.logs;

import com.travelapp.helper.enums.ErrorSeverity;
import com.travelapp.models.logs.ErrorLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {

    Optional<ErrorLog> findByTraceId(String traceId);

    List<ErrorLog> findByErrorCodeOrderByCreatedAtDesc(String errorCode);

    List<ErrorLog> findByHttpStatusOrderByCreatedAtDesc(Integer httpStatus);

    List<ErrorLog> findBySeverityOrderByCreatedAtDesc(ErrorSeverity severity);

    Page<ErrorLog> findByResolvedFalseOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT e FROM ErrorLog e WHERE e.createdAt BETWEEN :startDate AND :endDate ORDER BY e.createdAt DESC")
    List<ErrorLog> findByDateRange(@Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate);

    @Query("SELECT e.errorCode, COUNT(e) FROM ErrorLog e WHERE e.createdAt >= :since GROUP BY e.errorCode ORDER BY COUNT(e) DESC")
    List<Object[]> getErrorStatistics(@Param("since") LocalDateTime since);

    @Query("SELECT COUNT(e) FROM ErrorLog e WHERE e.createdAt >= :since AND e.severity = :severity")
    Long countBySeveritySince(@Param("since") LocalDateTime since,
                              @Param("severity") ErrorSeverity severity);

    List<ErrorLog> findByClientIpOrderByCreatedAtDesc(String clientIp);

    @Query("SELECT e FROM ErrorLog e WHERE e.userId IS NOT NULL AND e.userId = :userId ORDER BY e.createdAt DESC")
    List<ErrorLog> findByUserIdOrderByCreatedAtDesc(@Param("userId") String userId);

}
