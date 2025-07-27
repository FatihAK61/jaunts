package com.travelapp.dto.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travelapp.dto.book.BookingDto;
import com.travelapp.dto.configurations.PageViewDto;
import com.travelapp.dto.configurations.SearchLogDto;
import com.travelapp.dto.coupons.CouponUsageDto;
import com.travelapp.dto.logs.AuditLogDto;
import com.travelapp.dto.logs.LoginAttemptDto;
import com.travelapp.dto.notifications.NotificationDto;
import com.travelapp.dto.payment.PaymentMethodDto;
import com.travelapp.dto.preferences.UserPreferenceDto;
import com.travelapp.dto.reviews.ReviewDto;
import com.travelapp.dto.support.SupportTicketDto;
import com.travelapp.dto.wishlists.WishlistDto;
import com.travelapp.helper.enums.Gender;
import com.travelapp.helper.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBaseDto {

    private Long id;

    private Integer loginAttempts;

    private Boolean phoneVerified;
    private Boolean emailVerified;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate passportExpiryDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime emailVerificationExpiry;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lockedUntil;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime passwordResetExpiry;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime phoneVerificationExpiry;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private Gender gender;
    private UserStatus status;

    private String firstName;
    private String lastName;
    private String email;
    private String emailVerificationToken;
    private String lastLoginIp;
    private String nationality;
    private String passportNumber;
    private String password;
    private String passwordResetToken;
    private String phoneNumber;
    private String phoneVerificationCode;
    private String profileImageUrl;

    private Set<BookingDto> bookings = new HashSet<>();
    private Set<SupportTicketDto> supportTickets = new HashSet<>();
    private Set<ReviewDto> reviews = new HashSet<>();
    private Set<CouponUsageDto> couponUsages = new HashSet<>();
    private Set<AuditLogDto> auditLogs = new HashSet<>();
    private Set<LoginAttemptDto> loginAttemptsSet = new HashSet<>();
    private Set<NotificationDto> notifications = new HashSet<>();
    private Set<PageViewDto> pageViews = new HashSet<>();
    private Set<SearchLogDto> searchLogs = new HashSet<>();
    private Set<UserAddressDto> userAddresses = new HashSet<>();
    private Set<PaymentMethodDto> paymentMethods = new HashSet<>();
    private Set<WishlistDto> wishlists = new HashSet<>();
    private Set<UserRoleDto> userRoles = new HashSet<>();
    private Set<UserPreferenceDto> userPreferences = new HashSet<>();
}
