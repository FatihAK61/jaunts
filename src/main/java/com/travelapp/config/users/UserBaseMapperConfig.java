package com.travelapp.config.users;

import com.travelapp.dto.users.UserBaseDto;
import com.travelapp.models.users.UserBase;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserBaseMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(UserBase.class, UserBaseDto.class)
                .addMappings(mapping -> {
                });

        modelMapper.createTypeMap(UserBaseDto.class, UserBase.class)
                .addMappings(mapping -> {
                    mapping.skip(UserBase::setId);
                    mapping.skip(UserBase::setCreatedAt);
                    mapping.skip(UserBase::setUpdatedAt);

                    mapping.skip(UserBase::setEmailVerificationToken);
                    mapping.skip(UserBase::setEmailVerificationExpiry);
                    mapping.skip(UserBase::setPhoneVerificationCode);
                    mapping.skip(UserBase::setPhoneVerificationExpiry);
                    mapping.skip(UserBase::setPasswordResetToken);
                    mapping.skip(UserBase::setPasswordResetExpiry);
                    mapping.skip(UserBase::setLastLoginAt);
                    mapping.skip(UserBase::setLastLoginIp);
                    mapping.skip(UserBase::setLoginAttempts);
                    mapping.skip(UserBase::setLockedUntil);

                    mapping.skip(UserBase::setUserRoles);
                    mapping.skip(UserBase::setAddresses);
                    mapping.skip(UserBase::setPaymentMethods);
                    mapping.skip(UserBase::setBookings);
                    mapping.skip(UserBase::setReviews);
                    mapping.skip(UserBase::setPreferences);
                });
    }
}
