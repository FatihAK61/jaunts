package com.travelapp.services.impl.tours;

import com.travelapp.dto.tours.TourPriceDto;
import com.travelapp.helper.errorhandler.ResourceNotFoundException;
import com.travelapp.models.tours.Tour;
import com.travelapp.models.tours.TourPrice;
import com.travelapp.repository.tours.TourPriceRepository;
import com.travelapp.repository.tours.TourRepository;
import com.travelapp.services.impl.tours.mappers.TourPriceMapperService;
import com.travelapp.services.tours.ITourPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TourPriceServiceImpl implements ITourPriceService {

    private final TourPriceRepository tourPriceRepository;
    private final TourRepository tourRepository;
    private final TourPriceMapperService tourPriceMapperService;

    @Override
    @Transactional
    public TourPriceDto createTourPrice(TourPriceDto tourPriceDto) {
        Tour tour = tourRepository.findTourById(tourPriceDto.getTourId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour Not Found.", tourPriceDto.getTourId().toString()));

        TourPrice tourPrice = tourPriceMapperService.toEntity(tourPriceDto);
        tourPrice.setTour(tour);
        TourPrice savedTourPrice = tourPriceRepository.save(tourPrice);

        return tourPriceMapperService.toDto(savedTourPrice);
    }

    @Override
    public TourPriceDto updateTourPrice(Long id, TourPriceDto tourPriceDto) {
        return null;
    }

    @Override
    public Set<TourPriceDto> getAllTourPrices() {
        return Set.of();
    }

    @Override
    public TourPriceDto getTourPriceById(Long id) {
        return null;
    }

    @Override
    public void deleteTourPrice(Long id) {

    }
}
