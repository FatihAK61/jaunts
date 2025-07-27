package com.travelapp.services.hotels;

import com.travelapp.dto.hotels.RoomDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IRoomService {

    List<RoomDto> getAllRooms();

    List<RoomDto> getActiveRooms();

    Optional<RoomDto> getRoomById(Long id);

    List<RoomDto> getRoomsByHotel(Long hotelId);

    List<RoomDto> getActiveRoomsByHotel(Long hotelId);

    Optional<RoomDto> getRoomByHotelAndRoomNumber(Long hotelId, String roomNumber);

    RoomDto createRoom(RoomDto roomDto);

    RoomDto updateRoom(Long id, RoomDto roomDto);

    void deleteRoom(Long id);

    void deactivateRoom(Long id);

    void activateRoom(Long id);

    List<RoomDto> getAvailableRoomsByHotelAndCapacity(Long hotelId, Integer capacity);

    List<RoomDto> getRoomsByHotelAndPriceRange(Long hotelId, BigDecimal minPrice, BigDecimal maxPrice);

    boolean existsByHotelAndRoomNumber(Long hotelId, String roomNumber);

    long getRoomCountByHotel(Long hotelId);

    long getActiveRoomCountByHotel(Long hotelId);

}
