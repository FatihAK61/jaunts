package com.travelapp.services.impl.coupons.mappers;

import com.travelapp.dto.coupons.CouponUsageDto;
import com.travelapp.models.coupons.CouponUsage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponUsageMapperService {

    private final ModelMapper modelMapper;

    public CouponUsageDto toDto(CouponUsage couponUsage) {
        if (couponUsage == null) {
            return null;
        }

        CouponUsageDto dto = modelMapper.map(couponUsage, CouponUsageDto.class);

        if (couponUsage.getCoupon() != null) {
            dto.setCouponId(couponUsage.getCoupon().getId());
        }

        if (couponUsage.getUser() != null) {
            dto.setUserId(couponUsage.getUser().getId());
        }

        if (couponUsage.getBooking() != null) {
            dto.setBookingId(couponUsage.getBooking().getId());
        }

        return dto;
    }

    public CouponUsage toEntity(CouponUsageDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, CouponUsage.class);
    }

    public List<CouponUsageDto> toDtoList(List<CouponUsage> couponUsages) {
        if (couponUsages == null) {
            return null;
        }

        return couponUsages.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<CouponUsageDto> toDtoSet(Set<CouponUsage> couponUsages) {
        if (couponUsages == null) {
            return null;
        }

        return couponUsages.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<CouponUsage> toEntityList(List<CouponUsageDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(CouponUsageDto dto, CouponUsage existingUsage) {
        if (dto == null || existingUsage == null) {
            return;
        }

        modelMapper.map(dto, existingUsage);
    }
}
