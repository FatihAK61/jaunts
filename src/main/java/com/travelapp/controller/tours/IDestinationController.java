package com.travelapp.controller.tours;

import com.travelapp.dto.tours.DestinationDto;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface IDestinationController {

    ResponseEntity<DestinationDto> createDestination(DestinationDto destinationDto);

    DestinationDto updateDestination(Long id, DestinationDto destinationDto);

    Set<DestinationDto> getAllDestinations();

    DestinationDto getDestinationById(Long id);

    void deleteDestination(Long id);
}
