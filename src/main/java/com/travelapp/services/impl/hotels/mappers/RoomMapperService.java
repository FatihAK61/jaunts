package com.travelapp.services.impl.hotels.mappers;

import com.travelapp.dto.hotels.RoomDto;
import com.travelapp.models.hotels.Room;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomMapperService {

    private final ModelMapper modelMapper;

    public RoomDto toDto(Room room) {
        if (room == null) {
            return null;
        }

        RoomDto dto = modelMapper.map(room, RoomDto.class);

        if (room.getHotel() != null) {
            dto.setHotelId(room.getHotel().getId());
        }

        return dto;
    }

    public Room toEntity(RoomDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, Room.class);
    }

    public List<RoomDto> toDtoList(List<Room> rooms) {
        if (rooms == null) {
            return null;
        }

        return rooms.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Set<RoomDto> toDtoSet(Set<Room> rooms) {
        if (rooms == null) {
            return null;
        }

        return rooms.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<Room> toEntityList(List<RoomDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(RoomDto dto, Room existingRoom) {
        if (dto == null || existingRoom == null) {
            return;
        }

        modelMapper.map(dto, existingRoom);
    }
}
