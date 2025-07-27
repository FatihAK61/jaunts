package com.travelapp.repository.coupons;

import com.travelapp.models.coupons.CouponUsage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsage, Long>, JpaSpecificationExecutor<CouponUsage> {

    List<CouponUsage> findByCouponId(Long couponId);

    List<CouponUsage> findByUserId(Long userId);

    List<CouponUsage> findByBookingId(Long bookingId);

    @Query("SELECT cu FROM CouponUsage cu WHERE cu.coupon.id = :couponId AND cu.user.id = :userId")
    List<CouponUsage> findByCouponIdAndUserId(@Param("couponId") Long couponId, @Param("userId") Long userId);

    @Query("SELECT COUNT(cu) FROM CouponUsage cu WHERE cu.coupon.id = :couponId")
    Integer countByCouponId(@Param("couponId") Long couponId);

    @Query("SELECT COUNT(cu) FROM CouponUsage cu WHERE cu.coupon.id = :couponId AND cu.user.id = :userId")
    Integer countByCouponIdAndUserId(@Param("couponId") Long couponId, @Param("userId") Long userId);

    @Query("SELECT cu FROM CouponUsage cu WHERE cu.usedAt BETWEEN :startDate AND :endDate")
    List<CouponUsage> findByUsedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(cu.discountAmount) FROM CouponUsage cu WHERE cu.coupon.id = :couponId")
    Double getTotalDiscountByCouponId(@Param("couponId") Long couponId);

    @Query("SELECT SUM(cu.discountAmount) FROM CouponUsage cu WHERE cu.user.id = :userId")
    Double getTotalDiscountByUserId(@Param("userId") Long userId);

    Page<CouponUsage> findByCouponId(Long couponId, Pageable pageable);

    Page<CouponUsage> findByUserId(Long userId, Pageable pageable);

    boolean existsByCouponIdAndBookingId(Long couponId, Long bookingId);

}
