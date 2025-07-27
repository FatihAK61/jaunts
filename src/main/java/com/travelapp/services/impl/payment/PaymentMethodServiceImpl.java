package com.travelapp.services.impl.payment;

import com.travelapp.dto.payment.PaymentMethodDto;
import com.travelapp.helper.enums.PaymentMethodType;
import com.travelapp.models.payment.PaymentMethod;
import com.travelapp.models.users.UserBase;
import com.travelapp.repository.payment.PaymentMethodRepository;
import com.travelapp.repository.users.UserBaseRepository;
import com.travelapp.services.impl.payment.mappers.PaymentMethodMapperService;
import com.travelapp.services.payment.IPaymentMethodService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentMethodServiceImpl implements IPaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final UserBaseRepository userRepository;
    private final PaymentMethodMapperService paymentMethodMapperService;

    @Override
    public PaymentMethodDto createPaymentMethod(PaymentMethodDto paymentMethodDto) {
        PaymentMethod paymentMethod = paymentMethodMapperService.toEntity(paymentMethodDto);

        if (paymentMethodDto.getUserId() != null) {
            UserBase user = userRepository.findById(paymentMethodDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + paymentMethodDto.getUserId()));
            paymentMethod.setUser(user);
        }

        Long activeCount = paymentMethodRepository.countActiveByUserId(paymentMethodDto.getUserId());
        if (activeCount == 0) {
            paymentMethod.setIsDefault(true);
        }

        PaymentMethod saved = paymentMethodRepository.save(paymentMethod);

        return paymentMethodMapperService.toDto(saved);
    }

    @Override
    public PaymentMethodDto updatePaymentMethod(PaymentMethodDto paymentMethodDto) {
        PaymentMethod existingPaymentMethod = paymentMethodRepository.findById(paymentMethodDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Payment method not found with id: " + paymentMethodDto.getId()));

        paymentMethodMapperService.updateEntityFromDto(paymentMethodDto, existingPaymentMethod);

        PaymentMethod updated = paymentMethodRepository.save(existingPaymentMethod);

        return paymentMethodMapperService.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentMethodDto getPaymentMethodById(Long id) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment method not found with id: " + id));
        return paymentMethodMapperService.toDto(paymentMethod);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodDto> getActivePaymentMethodsByUserId(Long userId) {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findByUserIdAndActiveTrueOrderByIsDefaultDesc(userId);
        return paymentMethodMapperService.toDtoList(paymentMethods);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodDto> getPaymentMethodsByUserId(Long userId) {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findByUserId(userId);
        return paymentMethodMapperService.toDtoList(paymentMethods);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodDto> getPaymentMethodsByUserIdAndType(Long userId, PaymentMethodType type) {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findByUserIdAndType(userId, type);
        return paymentMethodMapperService.toDtoList(paymentMethods);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentMethodDto getDefaultPaymentMethodByUserId(Long userId) {
        PaymentMethod paymentMethod = paymentMethodRepository.findByUserIdAndIsDefaultTrue(userId)
                .orElse(null);
        return paymentMethod != null ? paymentMethodMapperService.toDto(paymentMethod) : null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethodDto> getActivePaymentMethodsByUserIdAndType(Long userId, PaymentMethodType type) {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findActiveByUserIdAndType(userId, type);
        return paymentMethodMapperService.toDtoList(paymentMethods);
    }

    @Override
    public PaymentMethodDto setAsDefault(Long id) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment method not found with id: " + id));

        paymentMethodRepository.resetDefaultForUser(paymentMethod.getUser().getId());

        paymentMethod.setIsDefault(true);
        PaymentMethod updated = paymentMethodRepository.save(paymentMethod);

        return paymentMethodMapperService.toDto(updated);
    }

    @Override
    public void deactivatePaymentMethod(Long id) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment method not found with id: " + id));

        if (paymentMethod.getIsDefault()) {
            List<PaymentMethod> otherActiveMethods = paymentMethodRepository.findByUserIdAndActiveTrue(paymentMethod.getUser().getId());
            otherActiveMethods.stream()
                    .filter(pm -> !pm.getId().equals(id))
                    .findFirst()
                    .ifPresent(pm -> {
                        pm.setIsDefault(true);
                        paymentMethodRepository.save(pm);
                    });
        }

        paymentMethodRepository.softDelete(id);
    }

    @Override
    public PaymentMethodDto activatePaymentMethod(Long id) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment method not found with id: " + id));

        paymentMethod.setActive(true);

        if (paymentMethodRepository.findByUserIdAndIsDefaultTrue(paymentMethod.getUser().getId()).isEmpty()) {
            paymentMethod.setIsDefault(true);
        }

        PaymentMethod updated = paymentMethodRepository.save(paymentMethod);

        return paymentMethodMapperService.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countActivePaymentMethodsByUserId(Long userId) {
        return paymentMethodRepository.countActiveByUserId(userId);
    }

    @Override
    public void deletePaymentMethod(Long id) {
        paymentMethodRepository.deleteById(id);
    }
}
