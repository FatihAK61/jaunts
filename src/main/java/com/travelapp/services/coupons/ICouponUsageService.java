package com.travelapp.services.coupons;

import com.travelapp.dto.coupons.CouponUsageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ICouponUsageService {

    CouponUsageDto createCouponUsage(CouponUsageDto couponUsageDto);

    Optional<CouponUsageDto> getCouponUsageById(Long id);

    List<CouponUsageDto> getCouponUsagesByCoupon(Long couponId);

    List<CouponUsageDto> getCouponUsagesByUser(Long userId);

    List<CouponUsageDto> getCouponUsagesByBooking(Long bookingId);

    List<CouponUsageDto> getCouponUsagesByCouponAndUser(Long couponId, Long userId);

    Integer getCouponUsageCount(Long couponId);

    Integer getUserCouponUsageCount(Long couponId, Long userId);

    List<CouponUsageDto> getCouponUsagesByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    Double getTotalDiscountByCoupon(Long couponId);

    Double getTotalDiscountByUser(Long userId);

    Page<CouponUsageDto> getCouponUsagesByCoupon(Long couponId, Pageable pageable);

    Page<CouponUsageDto> getCouponUsagesByUser(Long userId, Pageable pageable);

    void deleteCouponUsage(Long id);

    boolean existsByCouponAndBooking(Long couponId, Long bookingId);

    CouponUsageDto useCoupon(String couponCode, Long userId, Long bookingId);

}
