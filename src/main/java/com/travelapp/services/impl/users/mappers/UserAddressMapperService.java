package com.travelapp.services.impl.users.mappers;

import com.travelapp.dto.users.UserAddressDto;
import com.travelapp.models.users.UserAddress;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAddressMapperService {

    private final ModelMapper modelMapper;

    public UserAddressDto toDto(UserAddress userAddress) {
        if (userAddress == null) {
            return null;
        }

        UserAddressDto dto = modelMapper.map(userAddress, UserAddressDto.class);

        if (userAddress.getUser() != null) {
            dto.setUserId(userAddress.getUser().getId());
        }

        return dto;
    }

    public UserAddress toEntity(UserAddressDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, UserAddress.class);
    }

    public List<UserAddressDto> toDtoList(List<UserAddress> userAddresses) {
        if (userAddresses == null) {
            return null;
        }

        return userAddresses.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<UserAddressDto> toDtoSet(Set<UserAddress> userAddresses) {
        if (userAddresses == null) {
            return null;
        }

        return userAddresses.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<UserAddress> toEntityList(List<UserAddressDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public Set<UserAddress> toEntitySet(Set<UserAddressDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }

    public void updateEntityFromDto(UserAddressDto dto, UserAddress existingUserAddress) {
        if (dto == null || existingUserAddress == null) {
            return;
        }

        modelMapper.map(dto, existingUserAddress);
    }
}
