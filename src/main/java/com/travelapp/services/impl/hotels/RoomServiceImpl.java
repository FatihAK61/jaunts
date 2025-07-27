package com.travelapp.services.impl.hotels;

import com.travelapp.dto.hotels.RoomDto;
import com.travelapp.helper.errorhandler.ResourceNotFoundException;
import com.travelapp.models.hotels.Hotel;
import com.travelapp.models.hotels.Room;
import com.travelapp.repository.hotels.HotelRepository;
import com.travelapp.repository.hotels.RoomRepository;
import com.travelapp.services.hotels.IRoomService;
import com.travelapp.services.impl.hotels.mappers.RoomMapperService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapperService roomMapperService;

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return roomMapperService.toDtoList(rooms);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> getActiveRooms() {
        List<Room> rooms = roomRepository.findByActiveTrue();
        return roomMapperService.toDtoList(rooms);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoomDto> getRoomById(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        return room.map(roomMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> getRoomsByHotel(Long hotelId) {
        List<Room> rooms = roomRepository.findByHotelId(hotelId);
        return roomMapperService.toDtoList(rooms);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> getActiveRoomsByHotel(Long hotelId) {
        List<Room> rooms = roomRepository.findByHotelIdAndActiveTrue(hotelId);
        return roomMapperService.toDtoList(rooms);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoomDto> getRoomByHotelAndRoomNumber(Long hotelId, String roomNumber) {
        Optional<Room> room = roomRepository.findByHotelIdAndRoomNumber(hotelId, roomNumber);
        return room.map(roomMapperService::toDto);
    }

    @Override
    public RoomDto createRoom(RoomDto roomDto) {
        Hotel hotel = hotelRepository.findById(roomDto.getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: ", roomDto.getHotelId().toString()));

        if (roomRepository.existsByHotelIdAndRoomNumber(hotel.getId(), roomDto.getRoomNumber())) {
            throw new EntityExistsException("Room number " + roomDto.getRoomNumber() + " already exists for this hotel.");
        }

        Room room = roomMapperService.toEntity(roomDto);
        room.setHotel(hotel);

        Room savedRoom = roomRepository.save(room);

        return roomMapperService.toDto(savedRoom);
    }

    @Override
    public RoomDto updateRoom(Long id, RoomDto roomDto) {
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: ", id.toString()));

        if (!existingRoom.getRoomNumber().equals(roomDto.getRoomNumber()) &&
                roomRepository.existsByHotelIdAndRoomNumber(existingRoom.getHotel().getId(), roomDto.getRoomNumber())) {
            throw new IllegalArgumentException("Room number " + roomDto.getRoomNumber() + " already exists for this hotel");
        }

        roomMapperService.updateEntityFromDto(roomDto, existingRoom);

        Room updatedRoom = roomRepository.save(existingRoom);

        return roomMapperService.toDto(updatedRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new ResourceNotFoundException("Room not found with id: ", id.toString());
        }

        roomRepository.deleteById(id);
    }

    @Override
    public void deactivateRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: ", id.toString()));

        room.setActive(false);
        roomRepository.save(room);
    }

    @Override
    public void activateRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: ", id.toString()));

        room.setActive(true);
        roomRepository.save(room);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> getAvailableRoomsByHotelAndCapacity(Long hotelId, Integer capacity) {
        List<Room> rooms = roomRepository.findAvailableRoomsByHotelAndCapacity(hotelId, capacity);
        return roomMapperService.toDtoList(rooms);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> getRoomsByHotelAndPriceRange(Long hotelId, BigDecimal minPrice, BigDecimal maxPrice) {
        List<Room> rooms = roomRepository.findRoomsByHotelAndPriceRange(hotelId, minPrice, maxPrice);
        return roomMapperService.toDtoList(rooms);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByHotelAndRoomNumber(Long hotelId, String roomNumber) {
        return roomRepository.existsByHotelIdAndRoomNumber(hotelId, roomNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public long getRoomCountByHotel(Long hotelId) {
        return roomRepository.countByHotelId(hotelId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getActiveRoomCountByHotel(Long hotelId) {
        return roomRepository.countByHotelIdAndActiveTrue(hotelId);
    }
}
