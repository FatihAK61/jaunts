package com.travelapp.services.impl.coupons.mappers;

import com.travelapp.dto.coupons.CouponDto;
import com.travelapp.models.coupons.Coupon;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponMapperService {

    private final ModelMapper modelMapper;

    public CouponDto toDto(Coupon coupon) {
        if (coupon == null) {
            return null;
        }

        return modelMapper.map(coupon, CouponDto.class);
    }

    public Coupon toEntity(CouponDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, Coupon.class);
    }

    public List<CouponDto> toDtoList(List<Coupon> coupons) {
        if (coupons == null) {
            return null;
        }

        return coupons.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<CouponDto> toDtoSet(Set<Coupon> coupons) {
        if (coupons == null) {
            return null;
        }

        return coupons.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<Coupon> toEntityList(List<CouponDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(CouponDto dto, Coupon existingCoupon) {
        if (dto == null || existingCoupon == null) {
            return;
        }

        modelMapper.map(dto, existingCoupon);
    }
}
