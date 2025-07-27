package com.travelapp.repository.tours;

import com.travelapp.models.tours.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long>, JpaSpecificationExecutor<Destination> {

    @Query("SELECT d FROM Destination d " +
            "LEFT JOIN FETCH d.images " +
            "LEFT JOIN FETCH d.tours " +
            "LEFT JOIN FETCH d.hotels " +
            "WHERE d.id = :id")
    Optional<Destination> getDestinationWithAll(@Param("id") Long id);

    boolean existsByName(String name);
}
