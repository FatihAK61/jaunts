package com.travelapp.services.impl.payment.mappers;

import com.travelapp.dto.payment.PaymentMethodDto;
import com.travelapp.helper.enums.PaymentMethodType;
import com.travelapp.models.payment.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentMethodMapperService {

    private final ModelMapper modelMapper;

    public PaymentMethodDto toDto(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            return null;
        }

        PaymentMethodDto dto = modelMapper.map(paymentMethod, PaymentMethodDto.class);

        if (paymentMethod.getUser() != null) {
            dto.setUserId(paymentMethod.getUser().getId());
        }

        if (paymentMethod.getType() != null) {
            dto.setType(paymentMethod.getType().name());
        }

        return dto;
    }

    public PaymentMethod toEntity(PaymentMethodDto dto) {
        if (dto == null) {
            return null;
        }

        PaymentMethod entity = modelMapper.map(dto, PaymentMethod.class);

        if (dto.getType() != null) {
            entity.setType(PaymentMethodType.valueOf(dto.getType()));
        }

        return entity;
    }

    public List<PaymentMethodDto> toDtoList(List<PaymentMethod> paymentMethods) {
        if (paymentMethods == null) {
            return null;
        }

        return paymentMethods.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(PaymentMethodDto dto, PaymentMethod existingPaymentMethod) {
        if (dto == null || existingPaymentMethod == null) {
            return;
        }

        modelMapper.map(dto, existingPaymentMethod);

        if (dto.getType() != null) {
            existingPaymentMethod.setType(PaymentMethodType.valueOf(dto.getType()));
        }
    }
}
