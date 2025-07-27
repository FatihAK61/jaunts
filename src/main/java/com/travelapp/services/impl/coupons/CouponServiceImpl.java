package com.travelapp.services.impl.coupons;

import com.travelapp.dto.coupons.CouponDto;
import com.travelapp.helper.enums.DiscountType;
import com.travelapp.models.coupons.Coupon;
import com.travelapp.repository.coupons.CouponRepository;
import com.travelapp.services.coupons.ICouponService;
import com.travelapp.services.impl.coupons.mappers.CouponMapperService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponServiceImpl implements ICouponService {

    private final CouponRepository couponRepository;
    private final CouponMapperService couponMapperService;

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCode(String code) {
        return couponRepository.existsByCode(code);
    }

    @Override
    public CouponDto createCoupon(CouponDto couponDto) {
        if (existsByCode(couponDto.getCode())) {
            throw new EntityExistsException("Coupon with code " + couponDto.getCode() + " already exists");
        }

        Coupon coupon = couponMapperService.toEntity(couponDto);
        Coupon savedCoupon = couponRepository.save(coupon);

        return couponMapperService.toDto(savedCoupon);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CouponDto> getCouponById(Long id) {
        return couponRepository.findById(id)
                .map(couponMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CouponDto> getCouponByCode(String code) {
        return couponRepository.findByCode(code)
                .map(couponMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponDto> getAllActiveCoupons() {
        List<Coupon> coupons = couponRepository.findActiveCoupons(LocalDateTime.now());
        return couponMapperService.toDtoList(coupons);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponDto> getAllActivePublicCoupons() {
        List<Coupon> coupons = couponRepository.findActivePublicCoupons(LocalDateTime.now());
        return couponMapperService.toDtoList(coupons);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CouponDto> getActiveCouponByCode(String code) {
        return couponRepository.findActiveByCode(code, LocalDateTime.now())
                .map(couponMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponDto> getCouponsByTour(Long tourId) {
        List<Coupon> coupons = couponRepository.findByApplicableTour(tourId, LocalDateTime.now());
        return couponMapperService.toDtoList(coupons);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponDto> getCouponsByCategory(Long categoryId) {
        List<Coupon> coupons = couponRepository.findByApplicableCategory(categoryId, LocalDateTime.now());
        return couponMapperService.toDtoList(coupons);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponDto> getAvailableCoupons() {
        List<Coupon> coupons = couponRepository.findAvailableCoupons();
        return couponMapperService.toDtoList(coupons);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponDto> getActiveCoupons(Pageable pageable) {
        Page<Coupon> coupons = couponRepository.findByActiveTrue(pageable);
        return coupons.map(couponMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponDto> getActivePublicCoupons(Pageable pageable) {
        Page<Coupon> coupons = couponRepository.findByActiveTrueAndIsPublicTrue(pageable);
        return coupons.map(couponMapperService::toDto);
    }

    @Override
    public CouponDto updateCoupon(Long id, CouponDto couponDto) {
        Coupon existingCoupon = couponRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coupon not found with ID: " + id));

        couponMapperService.updateEntityFromDto(couponDto, existingCoupon);
        Coupon updatedCoupon = couponRepository.save(existingCoupon);

        return couponMapperService.toDto(updatedCoupon);
    }

    @Override
    public void deleteCoupon(Long id) {
        if (!couponRepository.existsById(id)) {
            throw new EntityNotFoundException("Coupon not found with ID: " + id);
        }

        couponRepository.deleteById(id);
    }

    @Override
    public void deactivateCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coupon not found with ID: " + id));

        coupon.setActive(false);
        couponRepository.save(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isCouponValid(String code) {
        return couponRepository.findActiveByCode(code, LocalDateTime.now()).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isCouponApplicable(String code, Long tourId, Long categoryId, BigDecimal amount) {
        Optional<Coupon> couponOpt = couponRepository.findActiveByCode(code, LocalDateTime.now());

        if (couponOpt.isEmpty()) {
            return false;
        }

        Coupon coupon = couponOpt.get();

        if (coupon.getMinimumAmount() != null && amount.compareTo(coupon.getMinimumAmount()) < 0) {
            return false;
        }

        if (coupon.getUsageLimit() != null && coupon.getUsageCount() >= coupon.getUsageLimit()) {
            return false;
        }

        if (!coupon.getApplicableTours().isEmpty() && !coupon.getApplicableTours().contains(tourId)) {
            return false;
        }

        return coupon.getApplicableCategories().isEmpty() || coupon.getApplicableCategories().contains(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canUserUseCoupon(String code, Long userId) {
        Optional<Coupon> couponOpt = couponRepository.findActiveByCode(code, LocalDateTime.now());

        if (couponOpt.isEmpty()) {
            return false;
        }

        Coupon coupon = couponOpt.get();

        if (coupon.getUserUsageLimit() != null) {
            Integer userUsageCount = couponRepository.countUserUsage(coupon.getId(), userId);
            return userUsageCount < coupon.getUserUsageLimit();
        }

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateDiscount(String code, BigDecimal amount) {
        Optional<Coupon> couponOpt = couponRepository.findActiveByCode(code, LocalDateTime.now());

        if (couponOpt.isEmpty()) {
            return BigDecimal.ZERO;
        }

        Coupon coupon = couponOpt.get();
        BigDecimal discount;

        if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {
            discount = amount.multiply(coupon.getDiscountValue()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        } else {
            discount = coupon.getDiscountValue();
        }

        if (coupon.getMaximumDiscount() != null && discount.compareTo(coupon.getMaximumDiscount()) > 0) {
            discount = coupon.getMaximumDiscount();
        }

        if (discount.compareTo(amount) > 0) {
            discount = amount;
        }

        return discount;
    }

    @Override
    public void incrementUsageCount(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException("Coupon not found with ID: " + couponId));

        coupon.setUsageCount(coupon.getUsageCount() + 1);
        couponRepository.save(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponDto> getExpiredCoupons() {
        List<Coupon> coupons = couponRepository.findExpiredCoupons(LocalDateTime.now());
        return couponMapperService.toDtoList(coupons);
    }
}
