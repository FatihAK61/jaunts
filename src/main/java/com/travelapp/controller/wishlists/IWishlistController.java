package com.travelapp.controller.wishlists;

import com.travelapp.dto.wishlists.WishlistDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface IWishlistController {

    ResponseEntity<WishlistDto> createWishlist(@Valid @NotNull WishlistDto wishlistDto);

    WishlistDto updateWishlist(Long id, WishlistDto wishlistDto);

    Set<WishlistDto> getAllWishlists();

    WishlistDto getWishlistById(Long id);

    void deleteWishlist(Long id);
}
