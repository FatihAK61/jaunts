package com.travelapp.config.payment;

import com.travelapp.dto.payment.PaymentDto;
import com.travelapp.models.payment.Payment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PaymentMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(Payment.class, PaymentDto.class)
                .addMapping(src -> src.getBooking().getId(), PaymentDto::setBookingId)
                .addMapping(src -> src.getPaymentMethod().getId(), PaymentDto::setPaymentMethodId)
                .addMappings(mapping -> {
                    mapping.skip(PaymentDto::setTransactions);
                });

        modelMapper.createTypeMap(PaymentDto.class, Payment.class)
                .addMappings(mapping -> {
                    mapping.skip(Payment::setId);
                    mapping.skip(Payment::setBooking);
                    mapping.skip(Payment::setPaymentMethod);
                    mapping.skip(Payment::setTransactions);
                });
    }
}
