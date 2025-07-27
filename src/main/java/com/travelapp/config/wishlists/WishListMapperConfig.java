package com.travelapp.config.wishlists;

import com.travelapp.dto.wishlists.WishlistDto;
import com.travelapp.models.wishlists.Wishlist;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WishListMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        modelMapper.createTypeMap(Wishlist.class, WishlistDto.class)
                .addMapping(src -> src.getUser() != null ? src.getUser().getId() : null, WishlistDto::setUserId)
                .addMapping(src -> src.getTour() != null ? src.getTour().getId() : null, WishlistDto::setTourId)
                .addMappings(mapping -> {
                    mapping.skip(WishlistDto::setUserId);
                    mapping.skip(WishlistDto::setTourId);
                });

        modelMapper.createTypeMap(WishlistDto.class, Wishlist.class)
                .addMappings(mapping -> {
                    mapping.skip(Wishlist::setId);
                    mapping.skip(Wishlist::setUser);
                    mapping.skip(Wishlist::setTour);
                    mapping.skip(Wishlist::setCreatedAt);
                });
    }
}
