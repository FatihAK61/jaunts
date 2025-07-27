package com.travelapp.services.payment;

import com.travelapp.dto.payment.PaymentMethodDto;
import com.travelapp.helper.enums.PaymentMethodType;

import java.util.List;

public interface IPaymentMethodService {

    PaymentMethodDto createPaymentMethod(PaymentMethodDto paymentMethodDto);

    PaymentMethodDto updatePaymentMethod(PaymentMethodDto paymentMethodDto);

    PaymentMethodDto getPaymentMethodById(Long id);

    List<PaymentMethodDto> getActivePaymentMethodsByUserId(Long userId);

    List<PaymentMethodDto> getPaymentMethodsByUserId(Long userId);

    List<PaymentMethodDto> getPaymentMethodsByUserIdAndType(Long userId, PaymentMethodType type);

    PaymentMethodDto getDefaultPaymentMethodByUserId(Long userId);

    List<PaymentMethodDto> getActivePaymentMethodsByUserIdAndType(Long userId, PaymentMethodType type);

    PaymentMethodDto setAsDefault(Long id);

    void deactivatePaymentMethod(Long id);

    PaymentMethodDto activatePaymentMethod(Long id);

    Long countActivePaymentMethodsByUserId(Long userId);

    void deletePaymentMethod(Long id);

}
