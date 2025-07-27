package com.travelapp.services.impl.users;

import com.travelapp.dto.users.UserAddressDto;
import com.travelapp.helper.enums.AddressType;
import com.travelapp.helper.errorhandler.InvalidOperationException;
import com.travelapp.helper.errorhandler.ResourceNotFoundException;
import com.travelapp.models.users.UserAddress;
import com.travelapp.models.users.UserBase;
import com.travelapp.repository.users.UserAddressRepository;
import com.travelapp.repository.users.UserBaseRepository;
import com.travelapp.services.impl.users.mappers.UserAddressMapperService;
import com.travelapp.services.users.IUserAddressService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAddressServiceImpl implements IUserAddressService {

    private final UserAddressRepository userAddressRepository;
    private final UserBaseRepository userBaseRepository;
    private final UserAddressMapperService userAddressMapperService;

    @Override
    public UserAddressDto createAddress(UserAddressDto addressDto) {

        UserBase user = userBaseRepository.findById(addressDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + addressDto.getUserId()));

        UserAddress address = userAddressMapperService.toEntity(addressDto);
        address.setUser(user);

        if (Boolean.TRUE.equals(addressDto.getIsDefault()) || !userAddressRepository.existsByUserIdAndIsDefaultTrue(user.getId())) {
            userAddressRepository.setAllNonDefaultForUser(user.getId());
            address.setIsDefault(true);
        }

        UserAddress savedAddress = userAddressRepository.save(address);

        return userAddressMapperService.toDto(savedAddress);
    }

    @Override
    public UserAddressDto updateAddress(Long addressId, UserAddressDto addressDto) {

        UserAddress existingAddress = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + addressId));

        if (addressDto.getUserId() != null && !existingAddress.getUser().getId().equals(addressDto.getUserId())) {
            throw new InvalidOperationException("You don't have permission to update this address.");
        }

        userAddressMapperService.updateEntityFromDto(addressDto, existingAddress);
        existingAddress.setUpdatedAt(LocalDateTime.now());

        if (Boolean.TRUE.equals(addressDto.getIsDefault()) && !existingAddress.getIsDefault()) {
            userAddressRepository.setAllNonDefaultForUser(existingAddress.getUser().getId(), existingAddress.getId());
            existingAddress.setIsDefault(true);
        }

        UserAddress updatedAddress = userAddressRepository.save(existingAddress);

        return userAddressMapperService.toDto(updatedAddress);
    }

    @Override
    public void deleteAddress(Long addressId) {

        UserAddress address = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: ", addressId.toString()));

        boolean wasDefault = address.getIsDefault();
        Long userId = address.getUser().getId();

        userAddressRepository.delete(address);

        if (wasDefault) {
            List<UserAddress> remainingAddresses = userAddressRepository.findByUserIdOrderByIsDefaultDescCreatedAtDesc(userId);
            if (!remainingAddresses.isEmpty()) {
                UserAddress newDefault = remainingAddresses.getFirst();
                newDefault.setIsDefault(true);
                userAddressRepository.save(newDefault);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserAddressDto getAddressById(Long addressId) {

        UserAddress address = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: ", addressId.toString()));

        return userAddressMapperService.toDto(address);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAddressDto getUserAddressById(Long userId, Long addressId) {
        UserAddress address = userAddressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: ", addressId.toString() + " for user: " + userId.toString()));

        return userAddressMapperService.toDto(address);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAddressDto> getUserAddresses(Long userId) {
        List<UserAddress> addresses = userAddressRepository.findByUserIdOrderByIsDefaultDescCreatedAtDesc(userId);
        return userAddressMapperService.toDtoList(addresses);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAddressDto> getUserAddressesByType(Long userId, AddressType type) {
        List<UserAddress> addresses = userAddressRepository.findByUserIdAndType(userId, type);
        return userAddressMapperService.toDtoList(addresses);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAddressDto getUserDefaultAddress(Long userId) {
        UserAddress defaultAddress = userAddressRepository.findByUserIdAndIsDefaultTrue(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No default address found for user: ", userId.toString()));

        return userAddressMapperService.toDto(defaultAddress);
    }

    @Override
    public UserAddressDto setDefaultAddress(Long userId, Long addressId) {

        UserAddress address = userAddressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: ", addressId.toString() + " for user: " + userId.toString()));

        userAddressRepository.setAllNonDefaultForUser(userId, addressId);

        address.setIsDefault(true);
        address.setUpdatedAt(LocalDateTime.now());

        UserAddress updatedAddress = userAddressRepository.save(address);

        return userAddressMapperService.toDto(updatedAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateAddressOwnership(Long userId, Long addressId) {
        boolean exists = userAddressRepository.findByIdAndUserId(addressId, userId).isPresent();
        if (!exists) {
            throw new InvalidOperationException("You don't have permission to access this address.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long getUserAddressCount(Long userId) {
        return userAddressRepository.countByUserId(userId);
    }
}
