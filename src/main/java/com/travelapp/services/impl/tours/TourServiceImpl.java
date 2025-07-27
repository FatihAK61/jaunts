package com.travelapp.services.impl.tours;

import com.travelapp.dto.tours.TourDto;
import com.travelapp.helper.errorhandler.ResourceNotFoundException;
import com.travelapp.models.tours.Category;
import com.travelapp.models.tours.Destination;
import com.travelapp.models.tours.Tour;
import com.travelapp.repository.tours.CategoryRepository;
import com.travelapp.repository.tours.DestinationRepository;
import com.travelapp.repository.tours.TourRepository;
import com.travelapp.services.impl.tours.mappers.TourMapperService;
import com.travelapp.services.tours.ITourService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TourServiceImpl implements ITourService {

    private final TourRepository tourRepository;
    private final CategoryRepository categoryRepository;
    private final DestinationRepository destinationRepository;
    private final TourMapperService tourMapperService;

    @Override
    @Transactional
    public TourDto createTour(TourDto tourDto) {
        Tour tour = tourMapperService.toEntity(tourDto);

        // İlişkisel entity'leri set et
        if (tourDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(tourDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: ", tourDto.getCategoryId().toString()));
            tour.setCategory(category);
        }

        if (tourDto.getDestinationId() != null) {
            Destination destination = destinationRepository.findById(tourDto.getDestinationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: ", tourDto.getDestinationId().toString()));
            tour.setDestination(destination);
        }

        Tour savedTour = tourRepository.save(tour);

        return tourMapperService.toDto(savedTour);
    }

    @Override
    @Transactional
    public TourDto updateTour(Long id, TourDto tourDto) {
        Tour existingTour = tourRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found with id: ", id.toString()));

        // Mevcut tour'u güncelle
        tourMapperService.updateEntityFromDto(tourDto, existingTour);

        // İlişkisel entity'leri güncelle
        if (tourDto.getCategoryId() != null && !tourDto.getCategoryId().equals(existingTour.getCategory().getId())) {
            Category category = categoryRepository.findById(tourDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: ", tourDto.getCategoryId().toString()));
            existingTour.setCategory(category);
        }

        if (tourDto.getDestinationId() != null && !tourDto.getDestinationId().equals(existingTour.getDestination().getId())) {
            Destination destination = destinationRepository.findById(tourDto.getDestinationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: ", tourDto.getDestinationId().toString()));
            existingTour.setDestination(destination);
        }

        Tour updatedTour = tourRepository.save(existingTour);

        return tourMapperService.toDto(updatedTour);
    }

    @Override
    public Set<TourDto> getAllTours() {
        Set<Tour> tours = tourRepository.findByActiveTrue();
        return tourMapperService.toDtoSet(tours);
    }

    @Override
    public TourDto getTourById(Long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found with id: ", id.toString()));
        return tourMapperService.toDto(tour);
    }

    @Override
    public TourDto getTourBySlug(String slug) {
        Tour tour = tourRepository.findBySlugAndActiveTrue(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found with slug: ", slug));

        // View count'u artır
        tour.setViewCount(tour.getViewCount() + 1);
        tourRepository.save(tour);

        return tourMapperService.toDto(tour);
    }

    @Override
    public Page<TourDto> getTours(Pageable pageable) {
        Page<Tour> tourPage = tourRepository.findByActiveTrue(pageable);
        return tourPage.map(tourMapperService::toDto);
    }

    @Override
    public List<TourDto> getToursByCategory(Long categoryId) {
        List<Tour> tours = tourRepository.findByCategoryIdAndActiveTrue(categoryId);
        return tourMapperService.toDtoList(tours);
    }

    @Override
    public List<TourDto> getToursByDestination(Long destinationId) {
        List<Tour> tours = tourRepository.findByDestinationIdAndActiveTrue(destinationId);
        return tourMapperService.toDtoList(tours);
    }

    @Override
    public List<TourDto> getFeaturedTours() {
        List<Tour> tours = tourRepository.findByFeaturedTrueAndActiveTrue();
        return tourMapperService.toDtoList(tours);
    }

    @Override
    public List<TourDto> searchTours(String keyword) {
        List<Tour> tours = tourRepository.findByTitleContainingIgnoreCaseAndActiveTrue(keyword);
        return tourMapperService.toDtoList(tours);
    }

    @Override
    @Transactional
    public void deleteTour(Long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found with id: ", id.toString()));

        tour.setActive(false);
        tourRepository.save(tour);
    }
}
