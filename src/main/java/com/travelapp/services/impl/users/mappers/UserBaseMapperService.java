package com.travelapp.services.impl.users.mappers;

import com.travelapp.dto.users.UserBaseDto;
import com.travelapp.models.users.UserBase;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserBaseMapperService {

    private final ModelMapper modelMapper;

    public UserBaseDto toDto(UserBase userBase) {
        if (userBase == null) {
            return null;
        }

        UserBaseDto dto = modelMapper.map(userBase, UserBaseDto.class);

        dto.setPassword(null);
        dto.setEmailVerificationToken(null);
        dto.setPhoneVerificationCode(null);
        dto.setPasswordResetToken(null);

        return dto;
    }

    public UserBaseDto toDtoSafe(UserBase userBase) {
        if (userBase == null) {
            return null;
        }

        UserBaseDto dto = new UserBaseDto();
        dto.setId(userBase.getId());
        dto.setEmail(userBase.getEmail());
        dto.setFirstName(userBase.getFirstName());
        dto.setLastName(userBase.getLastName());
        dto.setPhoneNumber(userBase.getPhoneNumber());
        dto.setStatus(userBase.getStatus());
        dto.setGender(userBase.getGender());
        dto.setDateOfBirth(userBase.getDateOfBirth());
        dto.setNationality(userBase.getNationality());
        dto.setProfileImageUrl(userBase.getProfileImageUrl());
        dto.setEmailVerified(userBase.getEmailVerified());
        dto.setPhoneVerified(userBase.getPhoneVerified());
        dto.setCreatedAt(userBase.getCreatedAt());
        dto.setUpdatedAt(userBase.getUpdatedAt());
        dto.setLastLoginAt(userBase.getLastLoginAt());

        return dto;
    }

    public UserBase toEntity(UserBaseDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, UserBase.class);
    }

    public List<UserBaseDto> toDtoList(List<UserBase> userBases) {
        if (userBases == null) {
            return null;
        }

        return userBases.stream()
                .map(this::toDtoSafe)
                .collect(Collectors.toList());
    }

    public List<UserBaseDto> toDtoListFull(List<UserBase> userBases) {
        if (userBases == null) {
            return null;
        }

        return userBases.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<UserBaseDto> toDtoSet(Set<UserBase> userBases) {
        if (userBases == null) {
            return null;
        }

        return userBases.stream()
                .map(this::toDtoSafe)
                .collect(Collectors.toSet());
    }

    public Set<UserBaseDto> toDtoSetFull(Set<UserBase> userBases) {
        if (userBases == null) {
            return null;
        }

        return userBases.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<UserBase> toEntityList(List<UserBaseDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public Set<UserBase> toEntitySet(Set<UserBaseDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }

    public void updateEntityFromDto(UserBaseDto dto, UserBase existingUser) {
        if (dto == null || existingUser == null) {
            return;
        }

        if (dto.getFirstName() != null) {
            existingUser.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            existingUser.setLastName(dto.getLastName());
        }
        if (dto.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getGender() != null) {
            existingUser.setGender(dto.getGender());
        }
        if (dto.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(dto.getDateOfBirth());
        }
        if (dto.getNationality() != null) {
            existingUser.setNationality(dto.getNationality());
        }
        if (dto.getPassportNumber() != null) {
            existingUser.setPassportNumber(dto.getPassportNumber());
        }
        if (dto.getPassportExpiryDate() != null) {
            existingUser.setPassportExpiryDate(dto.getPassportExpiryDate());
        }
        if (dto.getProfileImageUrl() != null) {
            existingUser.setProfileImageUrl(dto.getProfileImageUrl());
        }
    }

    public void updateEntityFromDtoAdmin(UserBaseDto dto, UserBase existingUser) {
        if (dto == null || existingUser == null) {
            return;
        }

        modelMapper.map(dto, existingUser);
    }
}
