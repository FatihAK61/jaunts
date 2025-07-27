package com.travelapp.services.impl.coupons;

import com.travelapp.dto.coupons.CouponUsageDto;
import com.travelapp.helper.errorhandler.InvalidOperationException;
import com.travelapp.models.book.Booking;
import com.travelapp.models.coupons.Coupon;
import com.travelapp.models.coupons.CouponUsage;
import com.travelapp.models.users.UserBase;
import com.travelapp.repository.book.BookingRepository;
import com.travelapp.repository.coupons.CouponRepository;
import com.travelapp.repository.coupons.CouponUsageRepository;
import com.travelapp.repository.users.UserBaseRepository;
import com.travelapp.services.coupons.ICouponService;
import com.travelapp.services.coupons.ICouponUsageService;
import com.travelapp.services.impl.coupons.mappers.CouponUsageMapperService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponUsageServiceImpl implements ICouponUsageService {

    private final CouponUsageRepository couponUsageRepository;
    private final CouponRepository couponRepository;
    private final UserBaseRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CouponUsageMapperService couponUsageMapperService;
    private final ICouponService couponService;

    @Override
    public CouponUsageDto createCouponUsage(CouponUsageDto couponUsageDto) {
        CouponUsage couponUsage = couponUsageMapperService.toEntity(couponUsageDto);

        if (couponUsageDto.getCouponId() != null) {
            Coupon coupon = couponRepository.findById(couponUsageDto.getCouponId())
                    .orElseThrow(() -> new EntityNotFoundException("Coupon not found with ID: " + couponUsageDto.getCouponId()));
            couponUsage.setCoupon(coupon);
        }

        if (couponUsageDto.getUserId() != null) {
            UserBase user = userRepository.findById(couponUsageDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + couponUsageDto.getUserId()));
            couponUsage.setUser(user);
        }

        if (couponUsageDto.getBookingId() != null) {
            Booking booking = bookingRepository.findById(couponUsageDto.getBookingId())
                    .orElseThrow(() -> new EntityNotFoundException("Booking not found with ID: " + couponUsageDto.getBookingId()));
            couponUsage.setBooking(booking);
        }

        CouponUsage savedUsage = couponUsageRepository.save(couponUsage);

        return couponUsageMapperService.toDto(savedUsage);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CouponUsageDto> getCouponUsageById(Long id) {
        return couponUsageRepository.findById(id)
                .map(couponUsageMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponUsageDto> getCouponUsagesByCoupon(Long couponId) {
        List<CouponUsage> usages = couponUsageRepository.findByCouponId(couponId);
        return couponUsageMapperService.toDtoList(usages);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponUsageDto> getCouponUsagesByUser(Long userId) {
        List<CouponUsage> usages = couponUsageRepository.findByUserId(userId);
        return couponUsageMapperService.toDtoList(usages);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponUsageDto> getCouponUsagesByBooking(Long bookingId) {
        List<CouponUsage> usages = couponUsageRepository.findByBookingId(bookingId);
        return couponUsageMapperService.toDtoList(usages);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponUsageDto> getCouponUsagesByCouponAndUser(Long couponId, Long userId) {
        List<CouponUsage> usages = couponUsageRepository.findByCouponIdAndUserId(couponId, userId);
        return couponUsageMapperService.toDtoList(usages);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getCouponUsageCount(Long couponId) {
        return couponUsageRepository.countByCouponId(couponId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getUserCouponUsageCount(Long couponId, Long userId) {
        return couponUsageRepository.countByCouponIdAndUserId(couponId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponUsageDto> getCouponUsagesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<CouponUsage> usages = couponUsageRepository.findByUsedAtBetween(startDate, endDate);
        return couponUsageMapperService.toDtoList(usages);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalDiscountByCoupon(Long couponId) {
        Double total = couponUsageRepository.getTotalDiscountByCouponId(couponId);
        return total != null ? total : 0.0;
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalDiscountByUser(Long userId) {
        Double total = couponUsageRepository.getTotalDiscountByUserId(userId);
        return total != null ? total : 0.0;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponUsageDto> getCouponUsagesByCoupon(Long couponId, Pageable pageable) {
        Page<CouponUsage> usages = couponUsageRepository.findByCouponId(couponId, pageable);
        return usages.map(couponUsageMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponUsageDto> getCouponUsagesByUser(Long userId, Pageable pageable) {
        Page<CouponUsage> usages = couponUsageRepository.findByUserId(userId, pageable);
        return usages.map(couponUsageMapperService::toDto);
    }

    @Override
    public void deleteCouponUsage(Long id) {
        if (!couponUsageRepository.existsById(id)) {
            throw new EntityNotFoundException("Coupon usage not found with ID: " + id);
        }

        couponUsageRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCouponAndBooking(Long couponId, Long bookingId) {
        return couponUsageRepository.existsByCouponIdAndBookingId(couponId, bookingId);
    }

    @Override
    public CouponUsageDto useCoupon(String couponCode, Long userId, Long bookingId) {
        Coupon coupon = couponRepository.findActiveByCode(couponCode, LocalDateTime.now())
                .orElseThrow(() -> new InvalidOperationException("Invalid or expired coupon code: " + couponCode));

        if (!couponService.canUserUseCoupon(couponCode, userId)) {
            throw new InvalidOperationException("User has exceeded usage limit for this coupon.");
        }

        if (existsByCouponAndBooking(coupon.getId(), bookingId)) {
            throw new InvalidOperationException("Coupon has already been used for this booking.");
        }

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with ID: " + bookingId));

        if (!couponService.isCouponApplicable(couponCode, booking.getTour().getId(),
                booking.getTour().getCategory().getId(), booking.getTotalAmount())) {
            throw new IllegalArgumentException("Coupon is not applicable for this booking.");
        }

        BigDecimal discountAmount = couponService.calculateDiscount(couponCode, booking.getTotalAmount());

        CouponUsage couponUsage = new CouponUsage();
        couponUsage.setCoupon(coupon);
        couponUsage.setUser(booking.getUser());
        couponUsage.setBooking(booking);
        couponUsage.setDiscountAmount(discountAmount);

        CouponUsage savedUsage = couponUsageRepository.save(couponUsage);

        couponService.incrementUsageCount(coupon.getId());

        return couponUsageMapperService.toDto(savedUsage);
    }
}
