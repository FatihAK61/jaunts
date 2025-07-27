package com.travelapp.services.wishlists;

import com.travelapp.dto.wishlists.WishlistDto;

import java.util.Set;

public interface IWishlistService {

    WishlistDto createWishlist(WishlistDto wishlistDto);

    WishlistDto updateWishlist(Long id, WishlistDto wishlistDto);

    Set<WishlistDto> getAllWishlists();

    WishlistDto getWishlistById(Long id);

    void deleteWishlist(Long id);
}
