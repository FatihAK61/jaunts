package com.travelapp.services.coupons;

import com.travelapp.dto.coupons.CouponDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ICouponService {

    CouponDto createCoupon(CouponDto couponDto);

    Optional<CouponDto> getCouponById(Long id);

    Optional<CouponDto> getCouponByCode(String code);

    List<CouponDto> getAllActiveCoupons();

    List<CouponDto> getAllActivePublicCoupons();

    Optional<CouponDto> getActiveCouponByCode(String code);

    List<CouponDto> getCouponsByTour(Long tourId);

    List<CouponDto> getCouponsByCategory(Long categoryId);

    List<CouponDto> getAvailableCoupons();

    Page<CouponDto> getActiveCoupons(Pageable pageable);

    Page<CouponDto> getActivePublicCoupons(Pageable pageable);

    CouponDto updateCoupon(Long id, CouponDto couponDto);

    void deleteCoupon(Long id);

    void deactivateCoupon(Long id);

    boolean isCouponValid(String code);

    boolean isCouponApplicable(String code, Long tourId, Long categoryId, BigDecimal amount);

    boolean canUserUseCoupon(String code, Long userId);

    BigDecimal calculateDiscount(String code, BigDecimal amount);

    void incrementUsageCount(Long couponId);

    List<CouponDto> getExpiredCoupons();

    boolean existsByCode(String code);
}
