package com.travelapp.services.impl.wishlists.mappers;

import com.travelapp.dto.wishlists.WishlistDto;
import com.travelapp.models.wishlists.Wishlist;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListMapperService {

    private final ModelMapper modelMapper;

    public WishlistDto toDto(Wishlist wishlist) {
        if (wishlist == null) {
            return null;
        }

        WishlistDto dto = modelMapper.map(wishlist, WishlistDto.class);

        if (wishlist.getUser() != null) {
            dto.setUserId(wishlist.getUser().getId());
        }

        if (wishlist.getTour() != null) {
            dto.setTourId(wishlist.getTour().getId());
        }

        return dto;
    }

    public Wishlist toEntity(WishlistDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, Wishlist.class);
    }

    public List<WishlistDto> toDtoList(List<Wishlist> wishlists) {
        if (wishlists == null) {
            return null;
        }

        return wishlists.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<WishlistDto> toDtoSet(Set<Wishlist> wishlists) {
        if (wishlists == null) {
            return null;
        }

        return wishlists.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<Wishlist> toEntityList(List<WishlistDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public Set<Wishlist> toEntitySet(Set<WishlistDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }

    public void updateEntityFromDto(WishlistDto dto, Wishlist existingWishlist) {
        if (dto == null || existingWishlist == null) {
            return;
        }
        
        modelMapper.map(dto, existingWishlist);
    }
}
