package com.travelapp.controller.impl.wishlists;

import com.travelapp.controller.wishlists.IWishlistController;
import com.travelapp.dto.wishlists.WishlistDto;
import com.travelapp.services.wishlists.IWishlistService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/wish-list")
@RequiredArgsConstructor
public class WishlistControllerImpl implements IWishlistController {

    private final IWishlistService wishlistService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<WishlistDto> createWishlist(@RequestBody @Valid @NotNull WishlistDto wishlistDto) {
        WishlistDto createdWishlist = wishlistService.createWishlist(wishlistDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWishlist);
    }

    @Override
    public WishlistDto updateWishlist(Long id, WishlistDto wishlistDto) {
        return null;
    }

    @Override
    public Set<WishlistDto> getAllWishlists() {
        return Set.of();
    }

    @Override
    public WishlistDto getWishlistById(Long id) {
        return null;
    }

    @Override
    public void deleteWishlist(Long id) {

    }
}
