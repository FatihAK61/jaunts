package com.travelapp.services.impl.payment.mappers;

import com.travelapp.dto.payment.PaymentDto;
import com.travelapp.dto.payment.PaymentTransactionDto;
import com.travelapp.models.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentMapperService {

    private final ModelMapper modelMapper;
    private final PaymentTransactionMapperService transactionMapperService;

    public PaymentDto toDto(Payment payment) {
        if (payment == null) {
            return null;
        }

        PaymentDto dto = modelMapper.map(payment, PaymentDto.class);

        if (payment.getBooking() != null) {
            dto.setBookingId(payment.getBooking().getId());
        }

        if (payment.getPaymentMethod() != null) {
            dto.setPaymentMethodId(payment.getPaymentMethod().getId());
        }

        if (payment.getTransactions() != null && !payment.getTransactions().isEmpty()) {
            Set<PaymentTransactionDto> transactionDtos = payment.getTransactions().stream()
                    .map((element) -> modelMapper.map(element, PaymentTransactionDto.class))
                    .collect(Collectors.toSet());
            dto.setTransactions(transactionDtos);
        }

        return dto;
    }

    public Payment toEntity(PaymentDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, Payment.class);
    }

    public List<PaymentDto> toDtoList(List<Payment> payments) {
        if (payments == null) {
            return null;
        }

        return payments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<PaymentDto> toDtoSet(Set<Payment> payments) {
        if (payments == null) {
            return null;
        }

        return payments.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<Payment> toEntityList(List<PaymentDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(PaymentDto dto, Payment existingPayment) {
        if (dto == null || existingPayment == null) {
            return;
        }

        modelMapper.map(dto, existingPayment);
    }
}
