package com.travelapp.config.payment;

import com.travelapp.dto.payment.PaymentTransactionDto;
import com.travelapp.models.payment.PaymentTransaction;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PaymentTransactionMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(PaymentTransaction.class, PaymentTransactionDto.class)
                .addMapping(src -> src.getPayment().getId(), PaymentTransactionDto::setPaymentId);

        modelMapper.createTypeMap(PaymentTransactionDto.class, PaymentTransaction.class)
                .addMappings(mapping -> {
                    mapping.skip(PaymentTransaction::setId);
                    mapping.skip(PaymentTransaction::setPayment);
                });
    }
}
