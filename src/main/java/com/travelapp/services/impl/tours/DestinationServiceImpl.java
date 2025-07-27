package com.travelapp.services.impl.tours;

import com.travelapp.dto.tours.DestinationDto;
import com.travelapp.helper.errorhandler.DuplicateResourceException;
import com.travelapp.helper.errorhandler.ResourceNotFoundException;
import com.travelapp.models.tours.Destination;
import com.travelapp.repository.tours.DestinationRepository;
import com.travelapp.services.impl.tours.mappers.DestinationMapperService;
import com.travelapp.services.tours.IDestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DestinationServiceImpl implements IDestinationService {

    private final DestinationRepository destinationRepository;
    private final DestinationMapperService destinationMapperService;

    @Override
    @Transactional
    public DestinationDto createDestination(DestinationDto destinationDto) {
        if (destinationRepository.existsByName(destinationDto.getName())) {
            throw new DuplicateResourceException("Destination with name '" + destinationDto.getName() + "' already exists");
        }

        Destination destination = destinationMapperService.toEntity(destinationDto);
        Destination savedDestination = destinationRepository.save(destination);
        return destinationMapperService.toDto(savedDestination);
    }

    @Override
    public DestinationDto updateDestination(Long id, DestinationDto destinationDto) {
        return null;
    }

    @Override
    public Set<DestinationDto> getAllDestinations() {
        return Set.of();
    }

    @Override
    public DestinationDto getDestinationById(Long id) {
        Optional<Destination> optionalDestinationWithAll = Optional.ofNullable(destinationRepository
                .getDestinationWithAll(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination Not Found.", id.toString())));

        if (optionalDestinationWithAll.isEmpty()) {
            throw new ResourceNotFoundException("Destination, Images, Tours and Hotels Not Found.", id.toString());
        }

        Destination destinationWithAll = optionalDestinationWithAll.get();
        return destinationMapperService.toDto(destinationWithAll);
    }

    @Override
    public void deleteDestination(Long id) {

    }
}
