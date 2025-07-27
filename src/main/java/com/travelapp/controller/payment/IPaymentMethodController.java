package com.travelapp.controller.payment;

import com.travelapp.dto.payment.PaymentMethodDto;
import com.travelapp.helper.enums.PaymentMethodType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPaymentMethodController {

    ResponseEntity<PaymentMethodDto> createPaymentMethod(@Valid @NotNull PaymentMethodDto paymentMethodDto);

    ResponseEntity<PaymentMethodDto> updatePaymentMethod(Long id, @Valid @NotNull PaymentMethodDto paymentMethodDto);

    ResponseEntity<PaymentMethodDto> getPaymentMethodById(Long id);

    ResponseEntity<List<PaymentMethodDto>> getActivePaymentMethodsByUserId(Long userId);

    ResponseEntity<List<PaymentMethodDto>> getPaymentMethodsByUserId(Long userId);

    ResponseEntity<List<PaymentMethodDto>> getPaymentMethodsByUserIdAndType(Long userId, PaymentMethodType type);

    ResponseEntity<PaymentMethodDto> getDefaultPaymentMethodByUserId(Long userId);

    ResponseEntity<List<PaymentMethodDto>> getActivePaymentMethodsByUserIdAndType(Long userId, PaymentMethodType type);

    ResponseEntity<PaymentMethodDto> setAsDefault(Long id);

    ResponseEntity<Void> deactivatePaymentMethod(Long id);

    ResponseEntity<PaymentMethodDto> activatePaymentMethod(Long id);

    ResponseEntity<Long> countActivePaymentMethodsByUserId(Long userId);

    ResponseEntity<Void> deletePaymentMethod(Long id);

}
