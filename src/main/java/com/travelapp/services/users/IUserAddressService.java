package com.travelapp.services.users;

import com.travelapp.dto.users.UserAddressDto;
import com.travelapp.helper.enums.AddressType;

import java.util.List;

public interface IUserAddressService {

    UserAddressDto createAddress(UserAddressDto addressDto);

    UserAddressDto updateAddress(Long addressId, UserAddressDto addressDto);

    void deleteAddress(Long addressId);

    UserAddressDto getAddressById(Long addressId);

    UserAddressDto getUserAddressById(Long userId, Long addressId);

    List<UserAddressDto> getUserAddresses(Long userId);

    List<UserAddressDto> getUserAddressesByType(Long userId, AddressType type);

    UserAddressDto getUserDefaultAddress(Long userId);

    UserAddressDto setDefaultAddress(Long userId, Long addressId);

    void validateAddressOwnership(Long userId, Long addressId);

    long getUserAddressCount(Long userId);
}
