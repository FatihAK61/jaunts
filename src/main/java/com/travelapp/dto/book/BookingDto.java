package com.travelapp.dto.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travelapp.dto.coupons.CouponUsageDto;
import com.travelapp.dto.payment.PaymentDto;
import com.travelapp.dto.reviews.ReviewDto;
import com.travelapp.dto.support.SupportTicketDto;
import com.travelapp.helper.enums.BookingStatus;
import com.travelapp.helper.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDto {

    private Long id;

    private Integer numberOfPeople;
    private BigDecimal paidAmount;
    private BigDecimal refundAmount;
    private BigDecimal totalAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cancelledAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime confirmedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paidAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refundedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiresAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private String adminNotes;
    private String bookingNumber;
    private String cancellationReason;
    private String customerNotes;
    private String specialRequests;

    private PaymentStatus paymentStatus;
    private BookingStatus status;

    private Long scheduleId;
    private Long tourId;
    private Long userId;

    private Set<BookingParticipantDto> participants = new HashSet<>();
    private Set<PaymentDto> payments = new HashSet<>();
    private Set<SupportTicketDto> supportTickets = new HashSet<>();
    private Set<ReviewDto> reviews = new HashSet<>();
    private Set<CouponUsageDto> couponUsages = new HashSet<>();
}
