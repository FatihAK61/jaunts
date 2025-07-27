package com.travelapp.controller.coupons;

import com.travelapp.dto.coupons.CouponDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

public interface ICouponController {

    ResponseEntity<CouponDto> createCoupon(@RequestBody CouponDto couponDto);

    ResponseEntity<CouponDto> getCouponById(@PathVariable Long id);

    ResponseEntity<CouponDto> getCouponByCode(@PathVariable String code);

    ResponseEntity<List<CouponDto>> getAllActiveCoupons();

    ResponseEntity<List<CouponDto>> getAllActivePublicCoupons();

    ResponseEntity<CouponDto> getActiveCouponByCode(@PathVariable String code);

    ResponseEntity<List<CouponDto>> getCouponsByTour(@PathVariable Long tourId);

    ResponseEntity<List<CouponDto>> getCouponsByCategory(@PathVariable Long categoryId);

    ResponseEntity<List<CouponDto>> getAvailableCoupons();

    ResponseEntity<Page<CouponDto>> getActiveCoupons(Pageable pageable);

    ResponseEntity<Page<CouponDto>> getActivePublicCoupons(Pageable pageable);

    ResponseEntity<CouponDto> updateCoupon(@PathVariable Long id, @RequestBody CouponDto couponDto);

    ResponseEntity<Void> deleteCoupon(@PathVariable Long id);

    ResponseEntity<Void> deactivateCoupon(@PathVariable Long id);

    ResponseEntity<Boolean> isCouponValid(@PathVariable String code);

    ResponseEntity<Boolean> isCouponApplicable(@PathVariable String code, @RequestParam Long tourId, @RequestParam Long categoryId, @RequestParam BigDecimal amount);

    ResponseEntity<Boolean> canUserUseCoupon(@PathVariable String code, @RequestParam Long userId);

    ResponseEntity<BigDecimal> calculateDiscount(@PathVariable String code, @RequestParam BigDecimal amount);

    ResponseEntity<List<CouponDto>> getExpiredCoupons();

    ResponseEntity<Boolean> existsByCode(@PathVariable String code);

}
