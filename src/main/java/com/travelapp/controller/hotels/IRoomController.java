package com.travelapp.controller.hotels;

import com.travelapp.dto.hotels.RoomDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface IRoomController {

    ResponseEntity<RoomDto> createRoom(RoomDto roomDto);

    ResponseEntity<RoomDto> updateRoom(Long id, RoomDto roomDto);

    ResponseEntity<List<RoomDto>> getAllRooms();

    ResponseEntity<Page<RoomDto>> getAllRoomsWithPagination(Pageable pageable);

    ResponseEntity<RoomDto> getRoomById(Long id);

    ResponseEntity<List<RoomDto>> getRoomsByHotel(Long hotelId);

    ResponseEntity<Page<RoomDto>> getRoomsByHotelWithPagination(Long hotelId, Pageable pageable);

    ResponseEntity<List<RoomDto>> getActiveRoomsByHotel(Long hotelId);

    ResponseEntity<RoomDto> getRoomByHotelAndRoomNumber(Long hotelId, String roomNumber);

    ResponseEntity<List<RoomDto>> getRoomsByCapacity(Long hotelId, Integer capacity);

    ResponseEntity<List<RoomDto>> getRoomsByPriceRange(Long hotelId, BigDecimal minPrice, BigDecimal maxPrice);

    ResponseEntity<Page<RoomDto>> getRoomsWithFilters(Long hotelId, Integer minCapacity,
                                                      BigDecimal minPrice, BigDecimal maxPrice,
                                                      String roomType, Boolean active, Pageable pageable);

    ResponseEntity<Long> getRoomCountByHotel(Long hotelId);

    ResponseEntity<Long> getActiveRoomCountByHotel(Long hotelId);

    ResponseEntity<RoomDto> toggleRoomActiveStatus(Long id);

    ResponseEntity<Boolean> checkRoomAvailability(Long hotelId, String roomNumber);

    void deleteRoom(Long id);

    ResponseEntity<Void> deactivateRoom(Long id);

    ResponseEntity<Void> activateRoom(Long id);

}
