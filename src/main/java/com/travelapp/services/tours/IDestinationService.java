package com.travelapp.services.tours;

import com.travelapp.dto.tours.DestinationDto;

import java.util.Set;

public interface IDestinationService {

    DestinationDto createDestination(DestinationDto destinationDto);

    DestinationDto updateDestination(Long id, DestinationDto destinationDto);

    Set<DestinationDto> getAllDestinations();

    DestinationDto getDestinationById(Long id);

    void deleteDestination(Long id);
}
