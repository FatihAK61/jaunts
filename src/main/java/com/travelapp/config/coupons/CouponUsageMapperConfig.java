package com.travelapp.config.coupons;

import com.travelapp.dto.coupons.CouponUsageDto;
import com.travelapp.models.coupons.CouponUsage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CouponUsageMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(CouponUsage.class, CouponUsageDto.class)
                .addMapping(src -> src.getCoupon().getId(), CouponUsageDto::setCouponId)
                .addMapping(src -> src.getUser().getId(), CouponUsageDto::setUserId)
                .addMapping(src -> src.getBooking().getId(), CouponUsageDto::setBookingId);

        modelMapper.createTypeMap(CouponUsageDto.class, CouponUsage.class)
                .addMappings(mapping -> {
                    mapping.skip(CouponUsage::setId);
                    mapping.skip(CouponUsage::setCoupon);
                    mapping.skip(CouponUsage::setUser);
                    mapping.skip(CouponUsage::setBooking);
                    mapping.skip(CouponUsage::setUsedAt);
                });
    }
}
