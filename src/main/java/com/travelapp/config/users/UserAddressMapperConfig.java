package com.travelapp.config.users;

import com.travelapp.dto.users.UserAddressDto;
import com.travelapp.models.users.UserAddress;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserAddressMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(UserAddress.class, UserAddressDto.class)
                .addMapping(src -> src.getUser() != null ? src.getUser().getId() : null, UserAddressDto::setUserId)
                .addMappings(mapping -> {
                    mapping.skip(UserAddressDto::setUserId);
                });

        modelMapper.createTypeMap(UserAddressDto.class, UserAddress.class)
                .addMappings(mapping -> {
                    mapping.skip(UserAddress::setId);
                    mapping.skip(UserAddress::setUser);
                    mapping.skip(UserAddress::setCreatedAt);
                    mapping.skip(UserAddress::setUpdatedAt);
                });
    }
}
