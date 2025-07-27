package com.travelapp.config.tours;

import com.travelapp.dto.tours.DestinationDto;
import com.travelapp.models.tours.Destination;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DestinationMapperConfig {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configureMappings() {
        // Destination -> DestinationDto mapping konfigürasyonu
        modelMapper.createTypeMap(Destination.class, DestinationDto.class)
                .addMappings(mapping -> {
                    // Lazy loaded collections için güvenli mapping
                    mapping.skip(DestinationDto::setTours);
                    mapping.skip(DestinationDto::setHotels);
                });

        // DestinationDto -> Destination mapping konfigürasyonu
        modelMapper.createTypeMap(DestinationDto.class, Destination.class)
                .addMappings(mapping -> {
                    // ID mapping'ini skip et (entity'de farklı şekilde handle edilecek)
                    mapping.skip(Destination::setId);
                    // Lazy loaded collections için güvenli mapping
                    mapping.skip(Destination::setTours);
                    mapping.skip(Destination::setHotels);
                });
    }
}
