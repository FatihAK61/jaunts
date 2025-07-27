package com.travelapp.controller.impl.tours;

import com.travelapp.controller.tours.IDestinationController;
import com.travelapp.dto.tours.DestinationDto;
import com.travelapp.services.tours.IDestinationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/destination")
@RequiredArgsConstructor
public class DestinationControllerImpl implements IDestinationController {

    private final IDestinationService destinationService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<DestinationDto> createDestination(@RequestBody @Valid DestinationDto destinationDto) {
        return ResponseEntity.ok(destinationService.createDestination(destinationDto));
    }

    @Override
    public DestinationDto updateDestination(Long id, DestinationDto destinationDto) {
        return null;
    }

    @Override
    public Set<DestinationDto> getAllDestinations() {
        return null;
    }

    @Override
    @GetMapping(path = "/get/{id}")
    public DestinationDto getDestinationById(@PathVariable(name = "id") Long id) {
        return destinationService.getDestinationById(id);
    }

    @Override
    public void deleteDestination(Long id) {

    }
}
