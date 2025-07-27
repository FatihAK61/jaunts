package com.travelapp.controller.impl.payment;

import com.travelapp.controller.payment.IPaymentMethodController;
import com.travelapp.dto.payment.PaymentMethodDto;
import com.travelapp.helper.enums.PaymentMethodType;
import com.travelapp.services.payment.IPaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-method")
@RequiredArgsConstructor
public class PaymentMethodControllerImpl implements IPaymentMethodController {

    private final IPaymentMethodService paymentMethodService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<PaymentMethodDto> createPaymentMethod(@RequestBody PaymentMethodDto paymentMethodDto) {
        PaymentMethodDto createdPaymentMethod = paymentMethodService.createPaymentMethod(paymentMethodDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPaymentMethod);
    }

    @Override
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<PaymentMethodDto> updatePaymentMethod(@PathVariable(name = "id") Long id, @RequestBody PaymentMethodDto paymentMethodDto) {
        paymentMethodDto.setId(id);
        return ResponseEntity.ok(paymentMethodService.updatePaymentMethod(paymentMethodDto));
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<PaymentMethodDto> getPaymentMethodById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(paymentMethodService.getPaymentMethodById(id));
    }

    @Override
    @GetMapping(path = "/user/{userId}/active")
    public ResponseEntity<List<PaymentMethodDto>> getActivePaymentMethodsByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(paymentMethodService.getActivePaymentMethodsByUserId(userId));
    }

    @Override
    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<PaymentMethodDto>> getPaymentMethodsByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(paymentMethodService.getPaymentMethodsByUserId(userId));
    }

    @Override
    @GetMapping(path = "/user/{userId}/type/{type}")
    public ResponseEntity<List<PaymentMethodDto>> getPaymentMethodsByUserIdAndType(@PathVariable(name = "userId") Long userId, @PathVariable(name = "type") PaymentMethodType type) {
        return ResponseEntity.ok(paymentMethodService.getPaymentMethodsByUserIdAndType(userId, type));
    }

    @Override
    @GetMapping(path = "/user/{userId}/default")
    public ResponseEntity<PaymentMethodDto> getDefaultPaymentMethodByUserId(@PathVariable(name = "userId") Long userId) {
        PaymentMethodDto defaultPaymentMethod = paymentMethodService.getDefaultPaymentMethodByUserId(userId);
        if (defaultPaymentMethod != null) {
            return ResponseEntity.ok(defaultPaymentMethod);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    @GetMapping(path = "/user/{userId}/type/{type}/active")
    public ResponseEntity<List<PaymentMethodDto>> getActivePaymentMethodsByUserIdAndType(@PathVariable(name = "userId") Long userId, @PathVariable(name = "type") PaymentMethodType type) {
        return ResponseEntity.ok(paymentMethodService.getActivePaymentMethodsByUserIdAndType(userId, type));
    }

    @Override
    @PutMapping(path = "/{id}/set-default")
    public ResponseEntity<PaymentMethodDto> setAsDefault(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(paymentMethodService.setAsDefault(id));
    }

    @Override
    @PutMapping(path = "/{id}/deactivate")
    public ResponseEntity<Void> deactivatePaymentMethod(@PathVariable(name = "id") Long id) {
        paymentMethodService.deactivatePaymentMethod(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @PutMapping(path = "/{id}/activate")
    public ResponseEntity<PaymentMethodDto> activatePaymentMethod(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(paymentMethodService.activatePaymentMethod(id));
    }

    @Override
    @GetMapping(path = "/count/user/{userId}/active")
    public ResponseEntity<Long> countActivePaymentMethodsByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(paymentMethodService.countActivePaymentMethodsByUserId(userId));
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable(name = "id") Long id) {
        paymentMethodService.deletePaymentMethod(id);
        return ResponseEntity.noContent().build();
    }
}
