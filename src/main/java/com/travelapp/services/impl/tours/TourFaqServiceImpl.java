package com.travelapp.services.impl.tours;

import com.travelapp.dto.tours.TourFaqDto;
import com.travelapp.helper.errorhandler.ResourceNotFoundException;
import com.travelapp.models.tours.Tour;
import com.travelapp.models.tours.TourFAQ;
import com.travelapp.repository.tours.TourFAQRepository;
import com.travelapp.repository.tours.TourRepository;
import com.travelapp.services.impl.tours.mappers.TourFaqMapperService;
import com.travelapp.services.tours.ITourFaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TourFaqServiceImpl implements ITourFaqService {

    private final TourFAQRepository tourFAQRepository;
    private final TourRepository tourRepository;
    private final TourFaqMapperService tourFaqMapperService;

    @Override
    @Transactional
    public TourFaqDto createTourFaq(TourFaqDto tourFaqDto) {
        Tour tour = tourRepository.findTourById(tourFaqDto.getTourId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour Not Found.", tourFaqDto.getTourId().toString()));

        TourFAQ tourFAQ = tourFaqMapperService.toEntity(tourFaqDto);
        tourFAQ.setTour(tour);
        TourFAQ savedTourFAQ = tourFAQRepository.save(tourFAQ);

        return tourFaqMapperService.toDto(savedTourFAQ);
    }

    @Override
    public TourFaqDto updateTourFaq(Long id, TourFaqDto tourFaqDto) {
        return null;
    }

    @Override
    public Set<TourFaqDto> getAllTourFaqs() {
        return Set.of();
    }

    @Override
    public TourFaqDto getTourFaqById(Long id) {
        return null;
    }

    @Override
    public void deleteTourFaq(Long id) {

    }
}
