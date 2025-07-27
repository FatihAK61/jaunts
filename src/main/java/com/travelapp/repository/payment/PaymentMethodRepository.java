package com.travelapp.repository.payment;

import com.travelapp.helper.enums.PaymentMethodType;
import com.travelapp.models.payment.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long>, JpaSpecificationExecutor<PaymentMethod> {

    List<PaymentMethod> findByUserIdAndActiveTrue(Long userId);

    List<PaymentMethod> findByUserId(Long userId);

    List<PaymentMethod> findByUserIdAndType(Long userId, PaymentMethodType type);

    Optional<PaymentMethod> findByUserIdAndIsDefaultTrue(Long userId);

    List<PaymentMethod> findByUserIdAndActiveTrueOrderByIsDefaultDesc(Long userId);

    @Query("SELECT pm FROM PaymentMethod pm WHERE pm.user.id = :userId AND pm.active = true AND pm.type = :type")
    List<PaymentMethod> findActiveByUserIdAndType(@Param("userId") Long userId, @Param("type") PaymentMethodType type);

    @Modifying
    @Query("UPDATE PaymentMethod pm SET pm.isDefault = false WHERE pm.user.id = :userId")
    void resetDefaultForUser(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE PaymentMethod pm SET pm.active = false WHERE pm.id = :id")
    void softDelete(@Param("id") Long id);

    boolean existsByUserIdAndIsDefaultTrueAndIdNot(Long userId, Long id);

    @Query("SELECT COUNT(pm) FROM PaymentMethod pm WHERE pm.user.id = :userId AND pm.active = true")
    Long countActiveByUserId(@Param("userId") Long userId);

}
