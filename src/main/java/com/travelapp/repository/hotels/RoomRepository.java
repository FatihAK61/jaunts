package com.travelapp.repository.hotels;

import com.travelapp.models.hotels.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    List<Room> findByHotelId(Long hotelId);

    List<Room> findByHotelIdAndActiveTrue(Long hotelId);

    Optional<Room> findByHotelIdAndRoomNumber(Long hotelId, String roomNumber);

    List<Room> findByActiveTrue();

    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId AND r.capacity >= :capacity AND r.active = true")
    List<Room> findAvailableRoomsByHotelAndCapacity(@Param("hotelId") Long hotelId, @Param("capacity") Integer capacity);

    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId AND r.pricePerNight BETWEEN :minPrice AND :maxPrice AND r.active = true")
    List<Room> findRoomsByHotelAndPriceRange(@Param("hotelId") Long hotelId, @Param("minPrice") java.math.BigDecimal minPrice, @Param("maxPrice") java.math.BigDecimal maxPrice);

    boolean existsByHotelIdAndRoomNumber(Long hotelId, String roomNumber);

    long countByHotelId(Long hotelId);

    long countByHotelIdAndActiveTrue(Long hotelId);

}
