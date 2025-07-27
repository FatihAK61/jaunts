package com.travelapp.controller.impl.hotels;

import com.travelapp.controller.hotels.IRoomController;
import com.travelapp.dto.hotels.RoomDto;
import com.travelapp.services.hotels.IRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomControllerImpl implements IRoomController {

    private final IRoomService roomService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<RoomDto> createRoom(@RequestBody @Valid RoomDto roomDto) {
        RoomDto createdRoom = roomService.createRoom(roomDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    @Override
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable(name = "id") Long id, @RequestBody @Valid RoomDto roomDto) {
        RoomDto updatedRoom = roomService.updateRoom(id, roomDto);
        return ResponseEntity.ok(updatedRoom);
    }

    @Override
    @GetMapping(path = "/all")
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        List<RoomDto> rooms = roomService.getActiveRooms();
        return ResponseEntity.ok(rooms);
    }

    @Override
    @GetMapping(path = "/all/paginated")
    public ResponseEntity<Page<RoomDto>> getAllRoomsWithPagination(Pageable pageable) {
        List<RoomDto> rooms = roomService.getActiveRooms();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), rooms.size());

        List<RoomDto> pageContent = rooms.subList(start, end);
        Page<RoomDto> roomPage = new PageImpl<>(pageContent, pageable, rooms.size());

        return ResponseEntity.ok(roomPage);
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable(name = "id") Long id) {
        return roomService.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping(path = "/hotel/{hotelId}")
    public ResponseEntity<List<RoomDto>> getRoomsByHotel(@PathVariable(name = "hotelId") Long hotelId) {
        List<RoomDto> rooms = roomService.getRoomsByHotel(hotelId);
        return ResponseEntity.ok(rooms);
    }

    @Override
    @GetMapping(path = "/hotel/{hotelId}/paginated")
    public ResponseEntity<Page<RoomDto>> getRoomsByHotelWithPagination(
            @PathVariable(name = "hotelId") Long hotelId, Pageable pageable) {
        List<RoomDto> rooms = roomService.getRoomsByHotel(hotelId);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), rooms.size());

        List<RoomDto> pageContent = rooms.subList(start, end);
        Page<RoomDto> roomPage = new PageImpl<>(pageContent, pageable, rooms.size());

        return ResponseEntity.ok(roomPage);
    }

    @Override
    @GetMapping(path = "/hotel/{hotelId}/active")
    public ResponseEntity<List<RoomDto>> getActiveRoomsByHotel(@PathVariable(name = "hotelId") Long hotelId) {
        List<RoomDto> rooms = roomService.getActiveRoomsByHotel(hotelId);
        return ResponseEntity.ok(rooms);
    }

    @Override
    @GetMapping(path = "/hotel/{hotelId}/room-number/{roomNumber}")
    public ResponseEntity<RoomDto> getRoomByHotelAndRoomNumber(
            @PathVariable(name = "hotelId") Long hotelId,
            @PathVariable(name = "roomNumber") String roomNumber) {
        return roomService.getRoomByHotelAndRoomNumber(hotelId, roomNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping(path = "/hotel/{hotelId}/capacity/{capacity}")
    public ResponseEntity<List<RoomDto>> getRoomsByCapacity(
            @PathVariable(name = "hotelId") Long hotelId,
            @PathVariable(name = "capacity") Integer capacity) {
        List<RoomDto> rooms = roomService.getAvailableRoomsByHotelAndCapacity(hotelId, capacity);
        return ResponseEntity.ok(rooms);
    }

    @Override
    @GetMapping(path = "/hotel/{hotelId}/price-range")
    public ResponseEntity<List<RoomDto>> getRoomsByPriceRange(
            @PathVariable(name = "hotelId") Long hotelId,
            @RequestParam(name = "minPrice") BigDecimal minPrice,
            @RequestParam(name = "maxPrice") BigDecimal maxPrice) {
        List<RoomDto> rooms = roomService.getRoomsByHotelAndPriceRange(hotelId, minPrice, maxPrice);
        return ResponseEntity.ok(rooms);
    }

    @Override
    @GetMapping(path = "/filter")
    public ResponseEntity<Page<RoomDto>> getRoomsWithFilters(
            @RequestParam(name = "hotelId", required = false) Long hotelId,
            @RequestParam(name = "minCapacity", required = false) Integer minCapacity,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "roomType", required = false) String roomType,
            @RequestParam(name = "active", required = false) Boolean active,
            Pageable pageable) {

        List<RoomDto> rooms;

        if (hotelId != null) {
            if (active != null && active) {
                rooms = roomService.getActiveRoomsByHotel(hotelId);
            } else {
                rooms = roomService.getRoomsByHotel(hotelId);
            }
        } else {
            if (active != null && active) {
                rooms = roomService.getActiveRooms();
            } else {
                rooms = roomService.getAllRooms();
            }
        }

        if (minCapacity != null) {
            rooms = rooms.stream()
                    .filter(room -> room.getCapacity() >= minCapacity)
                    .collect(Collectors.toList());
        }

        if (minPrice != null && maxPrice != null) {
            rooms = rooms.stream()
                    .filter(room -> room.getPricePerNight().compareTo(minPrice) >= 0 &&
                            room.getPricePerNight().compareTo(maxPrice) <= 0)
                    .collect(Collectors.toList());
        }

        if (roomType != null && !roomType.isEmpty()) {
            rooms = rooms.stream()
                    .filter(room -> room.getRoomType().toString().equalsIgnoreCase(roomType))
                    .collect(Collectors.toList());
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), rooms.size());

        List<RoomDto> pageContent = rooms.subList(start, end);
        Page<RoomDto> roomPage = new PageImpl<>(pageContent, pageable, rooms.size());

        return ResponseEntity.ok(roomPage);
    }

    @Override
    @GetMapping(path = "/count/hotel/{hotelId}")
    public ResponseEntity<Long> getRoomCountByHotel(@PathVariable(name = "hotelId") Long hotelId) {
        Long count = roomService.getRoomCountByHotel(hotelId);
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping(path = "/count/active/hotel/{hotelId}")
    public ResponseEntity<Long> getActiveRoomCountByHotel(@PathVariable(name = "hotelId") Long hotelId) {
        Long count = roomService.getActiveRoomCountByHotel(hotelId);
        return ResponseEntity.ok(count);
    }

    @Override
    @PatchMapping(path = "/toggle-status/{id}")
    public ResponseEntity<RoomDto> toggleRoomActiveStatus(@PathVariable(name = "id") Long id) {
        RoomDto room = roomService.getRoomById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (room.getActive()) {
            roomService.deactivateRoom(id);
            room.setActive(false);
        } else {
            roomService.activateRoom(id);
            room.setActive(true);
        }

        return ResponseEntity.ok(room);
    }

    @Override
    @GetMapping(path = "/check-availability")
    public ResponseEntity<Boolean> checkRoomAvailability(
            @RequestParam(name = "hotelId") Long hotelId,
            @RequestParam(name = "roomNumber") String roomNumber) {
        Boolean exists = roomService.existsByHotelAndRoomNumber(hotelId, roomNumber);
        return ResponseEntity.ok(exists);
    }

    @Override
    @DeleteMapping(path = "/delete/{id}")
    public void deleteRoom(@PathVariable(name = "id") Long id) {
        roomService.deleteRoom(id);
    }

    @Override
    @PatchMapping(path = "/deactivate/{id}")
    public ResponseEntity<Void> deactivateRoom(@PathVariable(name = "id") Long id) {
        roomService.deactivateRoom(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping(path = "/activate/{id}")
    public ResponseEntity<Void> activateRoom(@PathVariable(name = "id") Long id) {
        roomService.activateRoom(id);
        return ResponseEntity.noContent().build();
    }
}
