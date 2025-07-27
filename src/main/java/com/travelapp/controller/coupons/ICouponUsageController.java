package com.travelapp.controller.coupons;

import com.travelapp.dto.coupons.CouponUsageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

public interface ICouponUsageController {

    ResponseEntity<CouponUsageDto> createCouponUsage(@RequestBody CouponUsageDto couponUsageDto);

    ResponseEntity<CouponUsageDto> getCouponUsageById(@PathVariable Long id);

    ResponseEntity<List<CouponUsageDto>> getCouponUsagesByCoupon(@PathVariable Long couponId);

    ResponseEntity<List<CouponUsageDto>> getCouponUsagesByUser(@PathVariable Long userId);

    ResponseEntity<List<CouponUsageDto>> getCouponUsagesByBooking(@PathVariable Long bookingId);

    ResponseEntity<List<CouponUsageDto>> getCouponUsagesByCouponAndUser(@PathVariable Long couponId, @PathVariable Long userId);

    ResponseEntity<Integer> getCouponUsageCount(@PathVariable Long couponId);

    ResponseEntity<Integer> getUserCouponUsageCount(@PathVariable Long couponId, @PathVariable Long userId);

    ResponseEntity<List<CouponUsageDto>> getCouponUsagesByDateRange(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate);

    ResponseEntity<Double> getTotalDiscountByCoupon(@PathVariable Long couponId);

    ResponseEntity<Double> getTotalDiscountByUser(@PathVariable Long userId);

    ResponseEntity<Page<CouponUsageDto>> getCouponUsagesByCouponPaginated(@PathVariable Long couponId, Pageable pageable);

    ResponseEntity<Page<CouponUsageDto>> getCouponUsagesByUserPaginated(@PathVariable Long userId, Pageable pageable);

    ResponseEntity<Void> deleteCouponUsage(@PathVariable Long id);

    ResponseEntity<Boolean> existsByCouponAndBooking(@PathVariable Long couponId, @PathVariable Long bookingId);

    ResponseEntity<CouponUsageDto> useCoupon(@PathVariable String couponCode, @RequestParam Long userId, @RequestParam Long bookingId);

}
