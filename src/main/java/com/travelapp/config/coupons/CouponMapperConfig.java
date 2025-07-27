package com.travelapp.config.coupons;

import com.travelapp.dto.coupons.CouponDto;
import com.travelapp.models.coupons.Coupon;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CouponMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(Coupon.class, CouponDto.class)
                .addMappings(mapping -> {
                    mapping.skip(CouponDto::setUsages);
                });

        modelMapper.createTypeMap(CouponDto.class, Coupon.class)
                .addMappings(mapping -> {
                    mapping.skip(Coupon::setId);
                    mapping.skip(Coupon::setUsages);
                    mapping.skip(Coupon::setCreatedAt);
                    mapping.skip(Coupon::setUpdatedAt);
                });
    }
}
