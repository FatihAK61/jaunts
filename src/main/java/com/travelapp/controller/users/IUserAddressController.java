package com.travelapp.controller.users;

import com.travelapp.dto.users.UserAddressDto;
import com.travelapp.helper.enums.AddressType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IUserAddressController {

    ResponseEntity<UserAddressDto> createAddress(@Valid @NotNull UserAddressDto addressDto);

    ResponseEntity<UserAddressDto> updateAddress(@NotNull Long id, @Valid @NotNull UserAddressDto addressDto);

    ResponseEntity<List<UserAddressDto>> getAllAddresses();

    Page<UserAddressDto> getAllAddresses(Pageable pageable);

    ResponseEntity<Optional<UserAddressDto>> getAddressById(Long id);

    ResponseEntity<Optional<UserAddressDto>> getUserAddressById(Long userId, Long addressId);

    ResponseEntity<List<UserAddressDto>> getUserAddresses(Long userId);

    ResponseEntity<List<UserAddressDto>> getUserAddressesByType(Long userId, AddressType type);

    ResponseEntity<Optional<UserAddressDto>> getUserDefaultAddress(Long userId);

    ResponseEntity<UserAddressDto> setDefaultAddress(Long userId, Long addressId);

    void deleteAddress(Long id);

    void deleteUserAddress(Long userId, Long addressId);

    ResponseEntity<Long> getUserAddressCount(Long userId);

    boolean existsById(Long id);

    boolean existsByUserIdAndAddressId(Long userId, Long addressId);
}
