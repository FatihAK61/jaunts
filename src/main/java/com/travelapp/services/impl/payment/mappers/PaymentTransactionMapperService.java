package com.travelapp.services.impl.payment.mappers;

import com.travelapp.dto.payment.PaymentTransactionDto;
import com.travelapp.models.payment.PaymentTransaction;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentTransactionMapperService {

    private final ModelMapper modelMapper;

    public PaymentTransactionDto toDto(PaymentTransaction transaction) {
        if (transaction == null) {
            return null;
        }

        PaymentTransactionDto dto = modelMapper.map(transaction, PaymentTransactionDto.class);

        if (transaction.getPayment() != null) {
            dto.setPaymentId(transaction.getPayment().getId());
        }

        return dto;
    }

    public PaymentTransaction toEntity(PaymentTransactionDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, PaymentTransaction.class);
    }

    public List<PaymentTransactionDto> toDtoList(List<PaymentTransaction> transactions) {
        if (transactions == null) {
            return null;
        }

        return transactions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<PaymentTransactionDto> toDtoSet(Set<PaymentTransaction> transactions) {
        if (transactions == null) {
            return null;
        }

        return transactions.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public void updateEntityFromDto(PaymentTransactionDto dto, PaymentTransaction existingTransaction) {
        if (dto == null || existingTransaction == null) {
            return;
        }

        modelMapper.map(dto, existingTransaction);
    }
}
