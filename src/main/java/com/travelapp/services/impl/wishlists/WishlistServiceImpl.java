package com.travelapp.services.impl.wishlists;

import com.travelapp.dto.wishlists.WishlistDto;
import com.travelapp.helper.errorhandler.ResourceNotFoundException;
import com.travelapp.models.tours.Tour;
import com.travelapp.models.users.UserBase;
import com.travelapp.models.wishlists.Wishlist;
import com.travelapp.repository.tours.TourRepository;
import com.travelapp.repository.users.UserBaseRepository;
import com.travelapp.repository.wishlists.WishlistRepository;
import com.travelapp.services.impl.wishlists.mappers.WishListMapperService;
import com.travelapp.services.wishlists.IWishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements IWishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserBaseRepository userBaseRepository;
    private final TourRepository tourRepository;
    private final WishListMapperService wishListMapperService;

    @Override
    @Transactional
    public WishlistDto createWishlist(WishlistDto wishlistDto) {
        UserBase userBase = userBaseRepository.findUserById(wishlistDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found.", wishlistDto.getUserId().toString()));

        Tour tour = tourRepository.findTourById(wishlistDto.getTourId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour Not Found.", wishlistDto.getTourId().toString()));

        Wishlist wishlist = wishListMapperService.toEntity(wishlistDto);
        wishlist.setUser(userBase);
        wishlist.setTour(tour);
        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        return wishListMapperService.toDto(savedWishlist);
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
