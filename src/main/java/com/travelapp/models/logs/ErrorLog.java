package com.travelapp.models.logs;

import com.travelapp.helper.enums.ErrorSeverity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@Table(name = "ERROR_LOGS")
public class ErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String traceId;

    @Column(nullable = false, length = 100)
    private String errorCode;

    @Column(nullable = false, length = 1000)
    private String errorMessage;

    @Column(length = 500)
    private String requestPath;

    @Column(length = 10)
    private String httpMethod;

    @Column(nullable = false)
    private Integer httpStatus;

    @Column(length = 45)
    private String clientIp;

    @Column(length = 1000)
    private String userAgent;

    @Column
    private String exceptionClass;

    @Column(columnDefinition = "TEXT")
    private String stackTrace;

    @Column(columnDefinition = "TEXT")
    private String requestHeaders;

    @Column(columnDefinition = "TEXT")
    private String requestParameters;

    @Column(length = 100)
    private String sessionId;

    @Column(length = 100)
    private String userId;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private ErrorSeverity severity;

    @Column(nullable = false)
    @Builder.Default
    private Boolean resolved = false;

    @Column
    private LocalDateTime resolvedAt;

    @Column(length = 100)
    private String resolvedBy;

    @Column(length = 1000)
    private String resolutionNotes;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        LoginAttempt that = (LoginAttempt) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
