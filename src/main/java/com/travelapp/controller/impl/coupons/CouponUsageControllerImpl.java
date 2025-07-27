package com.travelapp.controller.impl.coupons;

import com.travelapp.controller.coupons.ICouponUsageController;
import com.travelapp.dto.coupons.CouponUsageDto;
import com.travelapp.services.coupons.ICouponUsageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/coupon-usage")
@RequiredArgsConstructor
public class CouponUsageControllerImpl implements ICouponUsageController {

    private final ICouponUsageService couponUsageService;

    @Override
    @PostMapping("/save")
    public ResponseEntity<CouponUsageDto> createCouponUsage(@Valid @RequestBody CouponUsageDto couponUsageDto) {
        CouponUsageDto createdUsage = couponUsageService.createCouponUsage(couponUsageDto);
        return new ResponseEntity<>(createdUsage, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CouponUsageDto> getCouponUsageById(@PathVariable Long id) {
        Optional<CouponUsageDto> usage = couponUsageService.getCouponUsageById(id);
        return usage.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping("/coupon/{couponId}")
    public ResponseEntity<List<CouponUsageDto>> getCouponUsagesByCoupon(@PathVariable Long couponId) {
        List<CouponUsageDto> usages = couponUsageService.getCouponUsagesByCoupon(couponId);
        return ResponseEntity.ok(usages);
    }

    @Override
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CouponUsageDto>> getCouponUsagesByUser(@PathVariable Long userId) {
        List<CouponUsageDto> usages = couponUsageService.getCouponUsagesByUser(userId);
        return ResponseEntity.ok(usages);
    }

    @Override
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<CouponUsageDto>> getCouponUsagesByBooking(@PathVariable Long bookingId) {
        List<CouponUsageDto> usages = couponUsageService.getCouponUsagesByBooking(bookingId);
        return ResponseEntity.ok(usages);
    }

    @Override
    @GetMapping("/coupon/{couponId}/user/{userId}")
    public ResponseEntity<List<CouponUsageDto>> getCouponUsagesByCouponAndUser(@PathVariable Long couponId, @PathVariable Long userId) {
        List<CouponUsageDto> usages = couponUsageService.getCouponUsagesByCouponAndUser(couponId, userId);
        return ResponseEntity.ok(usages);
    }

    @Override
    @GetMapping("/coupon/{couponId}/count")
    public ResponseEntity<Integer> getCouponUsageCount(@PathVariable Long couponId) {
        Integer count = couponUsageService.getCouponUsageCount(couponId);
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping("/coupon/{couponId}/user/{userId}/count")
    public ResponseEntity<Integer> getUserCouponUsageCount(@PathVariable Long couponId, @PathVariable Long userId) {
        Integer count = couponUsageService.getUserCouponUsageCount(couponId, userId);
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping("/date-range")
    public ResponseEntity<List<CouponUsageDto>> getCouponUsagesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<CouponUsageDto> usages = couponUsageService.getCouponUsagesByDateRange(startDate, endDate);
        return ResponseEntity.ok(usages);
    }

    @Override
    @GetMapping("/coupon/{couponId}/total-discount")
    public ResponseEntity<Double> getTotalDiscountByCoupon(@PathVariable Long couponId) {
        Double totalDiscount = couponUsageService.getTotalDiscountByCoupon(couponId);
        return ResponseEntity.ok(totalDiscount);
    }

    @Override
    @GetMapping("/user/{userId}/total-discount")
    public ResponseEntity<Double> getTotalDiscountByUser(@PathVariable Long userId) {
        Double totalDiscount = couponUsageService.getTotalDiscountByUser(userId);
        return ResponseEntity.ok(totalDiscount);
    }

    @Override
    @GetMapping("/coupon/{couponId}/paginated")
    public ResponseEntity<Page<CouponUsageDto>> getCouponUsagesByCouponPaginated(@PathVariable Long couponId, Pageable pageable) {
        Page<CouponUsageDto> usages = couponUsageService.getCouponUsagesByCoupon(couponId, pageable);
        return ResponseEntity.ok(usages);
    }

    @Override
    @GetMapping("/user/{userId}/paginated")
    public ResponseEntity<Page<CouponUsageDto>> getCouponUsagesByUserPaginated(@PathVariable Long userId, Pageable pageable) {
        Page<CouponUsageDto> usages = couponUsageService.getCouponUsagesByUser(userId, pageable);
        return ResponseEntity.ok(usages);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCouponUsage(@PathVariable Long id) {
        couponUsageService.deleteCouponUsage(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/exists/coupon/{couponId}/booking/{bookingId}")
    public ResponseEntity<Boolean> existsByCouponAndBooking(@PathVariable Long couponId, @PathVariable Long bookingId) {
        boolean exists = couponUsageService.existsByCouponAndBooking(couponId, bookingId);
        return ResponseEntity.ok(exists);
    }

    @Override
    @PostMapping("/use/{couponCode}")
    public ResponseEntity<CouponUsageDto> useCoupon(@PathVariable String couponCode, @RequestParam Long userId, @RequestParam Long bookingId) {
        CouponUsageDto usageDto = couponUsageService.useCoupon(couponCode, userId, bookingId);
        return new ResponseEntity<>(usageDto, HttpStatus.CREATED);
    }
}
