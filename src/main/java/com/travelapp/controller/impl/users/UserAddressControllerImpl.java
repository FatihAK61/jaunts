package com.travelapp.controller.impl.users;

import com.travelapp.controller.users.IUserAddressController;
import com.travelapp.dto.users.UserAddressDto;
import com.travelapp.helper.enums.AddressType;
import com.travelapp.services.users.IUserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user-address")
@RequiredArgsConstructor
public class UserAddressControllerImpl implements IUserAddressController {

    private final IUserAddressService userAddressService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<UserAddressDto> createAddress(@RequestBody UserAddressDto addressDto) {
        UserAddressDto createdAddress = userAddressService.createAddress(addressDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @Override
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<UserAddressDto> updateAddress(@PathVariable(name = "id") Long id, @RequestBody UserAddressDto addressDto) {
        UserAddressDto updatedAddress = userAddressService.updateAddress(id, addressDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedAddress);
    }

    @Override
    @GetMapping(path = "/list")
    public ResponseEntity<List<UserAddressDto>> getAllAddresses() {
        return ResponseEntity.status(HttpStatus.OK).body(List.of());
    }

    @Override
    @GetMapping("/paginated")
    public Page<UserAddressDto> getAllAddresses(Pageable pageable) {
        return Page.empty();
    }

    @Override
    @GetMapping(path = "/get/{id}")
    public ResponseEntity<Optional<UserAddressDto>> getAddressById(@PathVariable(name = "id") Long id) {
        UserAddressDto address = userAddressService.getAddressById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Optional.of(address));
    }

    @Override
    @GetMapping(path = "/user/{userId}/address/{addressId}")
    public ResponseEntity<Optional<UserAddressDto>> getUserAddressById(@PathVariable(name = "userId") Long userId,
                                                                       @PathVariable(name = "addressId") Long addressId) {
        UserAddressDto address = userAddressService.getUserAddressById(userId, addressId);
        return ResponseEntity.status(HttpStatus.OK).body(Optional.of(address));
    }

    @Override
    @GetMapping(path = "/user/{userId}/addresses")
    public ResponseEntity<List<UserAddressDto>> getUserAddresses(@PathVariable(name = "userId") Long userId) {
        List<UserAddressDto> addresses = userAddressService.getUserAddresses(userId);
        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }

    @Override
    @GetMapping(path = "/user/{userId}/addresses/type/{type}")
    public ResponseEntity<List<UserAddressDto>> getUserAddressesByType(@PathVariable(name = "userId") Long userId,
                                                                       @PathVariable(name = "type") AddressType type) {
        List<UserAddressDto> addresses = userAddressService.getUserAddressesByType(userId, type);
        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }

    @Override
    @GetMapping(path = "/user/{userId}/default")
    public ResponseEntity<Optional<UserAddressDto>> getUserDefaultAddress(@PathVariable(name = "userId") Long userId) {
        try {
            UserAddressDto defaultAddress = userAddressService.getUserDefaultAddress(userId);
            return ResponseEntity.status(HttpStatus.OK).body(Optional.of(defaultAddress));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(Optional.empty());
        }
    }

    @Override
    @PutMapping(path = "/user/{userId}/address/{addressId}/set-default")
    public ResponseEntity<UserAddressDto> setDefaultAddress(@PathVariable(name = "userId") Long userId,
                                                            @PathVariable(name = "addressId") Long addressId) {
        UserAddressDto defaultAddress = userAddressService.setDefaultAddress(userId, addressId);
        return ResponseEntity.status(HttpStatus.OK).body(defaultAddress);
    }

    @Override
    @DeleteMapping(path = "/delete/{id}")
    public void deleteAddress(@PathVariable(name = "id") Long id) {
        userAddressService.deleteAddress(id);
    }

    @Override
    @DeleteMapping(path = "/user/{userId}/address/{addressId}")
    public void deleteUserAddress(@PathVariable(name = "userId") Long userId,
                                  @PathVariable(name = "addressId") Long addressId) {
        userAddressService.validateAddressOwnership(userId, addressId);
        userAddressService.deleteAddress(addressId);
    }

    @Override
    @GetMapping(path = "/user/{userId}/count")
    public ResponseEntity<Long> getUserAddressCount(@PathVariable(name = "userId") Long userId) {
        long count = userAddressService.getUserAddressCount(userId);
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

    @Override
    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable(name = "id") Long id) {
        try {
            userAddressService.getAddressById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @GetMapping("/user/{userId}/address/{addressId}/exists")
    public boolean existsByUserIdAndAddressId(@PathVariable(name = "userId") Long userId,
                                              @PathVariable(name = "addressId") Long addressId) {
        try {
            userAddressService.getUserAddressById(userId, addressId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
