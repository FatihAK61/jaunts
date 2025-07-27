package com.travelapp.config.payment;

import com.travelapp.dto.payment.PaymentMethodDto;
import com.travelapp.models.payment.PaymentMethod;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PaymentMethodMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(PaymentMethod.class, PaymentMethodDto.class)
                .addMapping(src -> src.getUser().getId(), PaymentMethodDto::setUserId);

        modelMapper.createTypeMap(PaymentMethodDto.class, PaymentMethod.class)
                .addMappings(mapping -> {
                    mapping.skip(PaymentMethod::setId);
                    mapping.skip(PaymentMethod::setUser);
                });
    }
}
