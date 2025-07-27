package com.travelapp.controller.impl.coupons;

import com.travelapp.controller.coupons.ICouponController;
import com.travelapp.dto.coupons.CouponDto;
import com.travelapp.services.coupons.ICouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/coupon")
@RequiredArgsConstructor
public class CouponControllerImpl implements ICouponController {

    private final ICouponService couponService;

    @Override
    @PostMapping("/save")
    public ResponseEntity<CouponDto> createCoupon(@Valid @RequestBody CouponDto couponDto) {
        CouponDto createdCoupon = couponService.createCoupon(couponDto);
        return new ResponseEntity<>(createdCoupon, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CouponDto> getCouponById(@PathVariable Long id) {
        Optional<CouponDto> coupon = couponService.getCouponById(id);
        return coupon.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping("/code/{code}")
    public ResponseEntity<CouponDto> getCouponByCode(@PathVariable String code) {
        Optional<CouponDto> coupon = couponService.getCouponByCode(code);
        return coupon.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping("/active")
    public ResponseEntity<List<CouponDto>> getAllActiveCoupons() {
        List<CouponDto> coupons = couponService.getAllActiveCoupons();
        return ResponseEntity.ok(coupons);
    }

    @Override
    @GetMapping("/active/public")
    public ResponseEntity<List<CouponDto>> getAllActivePublicCoupons() {
        List<CouponDto> coupons = couponService.getAllActivePublicCoupons();
        return ResponseEntity.ok(coupons);
    }

    @Override
    @GetMapping("/active/code/{code}")
    public ResponseEntity<CouponDto> getActiveCouponByCode(@PathVariable String code) {
        Optional<CouponDto> coupon = couponService.getActiveCouponByCode(code);
        return coupon.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping("/tour/{tourId}")
    public ResponseEntity<List<CouponDto>> getCouponsByTour(@PathVariable Long tourId) {
        List<CouponDto> coupons = couponService.getCouponsByTour(tourId);
        return ResponseEntity.ok(coupons);
    }

    @Override
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<CouponDto>> getCouponsByCategory(@PathVariable Long categoryId) {
        List<CouponDto> coupons = couponService.getCouponsByCategory(categoryId);
        return ResponseEntity.ok(coupons);
    }

    @Override
    @GetMapping("/available")
    public ResponseEntity<List<CouponDto>> getAvailableCoupons() {
        List<CouponDto> coupons = couponService.getAvailableCoupons();
        return ResponseEntity.ok(coupons);
    }

    @Override
    @GetMapping("/active/paginated")
    public ResponseEntity<Page<CouponDto>> getActiveCoupons(Pageable pageable) {
        Page<CouponDto> coupons = couponService.getActiveCoupons(pageable);
        return ResponseEntity.ok(coupons);
    }

    @Override
    @GetMapping("/active/public/paginated")
    public ResponseEntity<Page<CouponDto>> getActivePublicCoupons(Pageable pageable) {
        Page<CouponDto> coupons = couponService.getActivePublicCoupons(pageable);
        return ResponseEntity.ok(coupons);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<CouponDto> updateCoupon(@PathVariable Long id, @Valid @RequestBody CouponDto couponDto) {
        CouponDto updatedCoupon = couponService.updateCoupon(id, couponDto);
        return ResponseEntity.ok(updatedCoupon);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateCoupon(@PathVariable Long id) {
        couponService.deactivateCoupon(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/validate/{code}")
    public ResponseEntity<Boolean> isCouponValid(@PathVariable String code) {
        boolean isValid = couponService.isCouponValid(code);
        return ResponseEntity.ok(isValid);
    }

    @Override
    @GetMapping("/applicable/{code}")
    public ResponseEntity<Boolean> isCouponApplicable(@PathVariable String code, @RequestParam Long tourId, @RequestParam Long categoryId, @RequestParam BigDecimal amount) {
        boolean isApplicable = couponService.isCouponApplicable(code, tourId, categoryId, amount);
        return ResponseEntity.ok(isApplicable);
    }

    @Override
    @GetMapping("/user-can-use/{code}")
    public ResponseEntity<Boolean> canUserUseCoupon(@PathVariable String code, @RequestParam Long userId) {
        boolean canUse = couponService.canUserUseCoupon(code, userId);
        return ResponseEntity.ok(canUse);
    }

    @Override
    @GetMapping("/discount/{code}")
    public ResponseEntity<BigDecimal> calculateDiscount(@PathVariable String code, @RequestParam BigDecimal amount) {
        BigDecimal discount = couponService.calculateDiscount(code, amount);
        return ResponseEntity.ok(discount);
    }

    @Override
    @GetMapping("/expired")
    public ResponseEntity<List<CouponDto>> getExpiredCoupons() {
        List<CouponDto> expiredCoupons = couponService.getExpiredCoupons();
        return ResponseEntity.ok(expiredCoupons);
    }

    @Override
    @GetMapping("/exists/{code}")
    public ResponseEntity<Boolean> existsByCode(@PathVariable String code) {
        boolean exists = couponService.existsByCode(code);
        return ResponseEntity.ok(exists);
    }
}
