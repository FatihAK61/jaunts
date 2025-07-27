package com.travelapp.repository.coupons;

import com.travelapp.models.coupons.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>, JpaSpecificationExecutor<Coupon> {

    Optional<Coupon> findByCode(String code);

    @Query("SELECT c FROM Coupon c WHERE c.active = true AND c.validFrom <= :now AND c.validUntil >= :now")
    List<Coupon> findActiveCoupons(@Param("now") LocalDateTime now);

    @Query("SELECT c FROM Coupon c WHERE c.active = true AND c.isPublic = true AND c.validFrom <= :now AND c.validUntil >= :now")
    List<Coupon> findActivePublicCoupons(@Param("now") LocalDateTime now);

    @Query("SELECT c FROM Coupon c WHERE c.active = true AND c.code = :code AND c.validFrom <= :now AND c.validUntil >= :now")
    Optional<Coupon> findActiveByCode(@Param("code") String code, @Param("now") LocalDateTime now);

    @Query("SELECT c FROM Coupon c WHERE :tourId MEMBER OF c.applicableTours AND c.active = true AND c.validFrom <= :now AND c.validUntil >= :now")
    List<Coupon> findByApplicableTour(@Param("tourId") Long tourId, @Param("now") LocalDateTime now);

    @Query("SELECT c FROM Coupon c WHERE :categoryId MEMBER OF c.applicableCategories AND c.active = true AND c.validFrom <= :now AND c.validUntil >= :now")
    List<Coupon> findByApplicableCategory(@Param("categoryId") Long categoryId, @Param("now") LocalDateTime now);

    @Query("SELECT c FROM Coupon c WHERE c.usageLimit IS NULL OR c.usageCount < c.usageLimit")
    List<Coupon> findAvailableCoupons();

    @Query("SELECT c FROM Coupon c WHERE c.validUntil < :now")
    List<Coupon> findExpiredCoupons(@Param("now") LocalDateTime now);

    Page<Coupon> findByActiveTrue(Pageable pageable);

    Page<Coupon> findByActiveTrueAndIsPublicTrue(Pageable pageable);

    boolean existsByCode(String code);

    @Query("SELECT COUNT(cu) FROM CouponUsage cu WHERE cu.coupon.id = :couponId AND cu.user.id = :userId")
    Integer countUserUsage(@Param("couponId") Long couponId, @Param("userId") Long userId);
}
