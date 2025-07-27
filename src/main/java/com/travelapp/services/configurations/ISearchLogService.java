package com.travelapp.services.configurations;

import com.travelapp.dto.configurations.SearchLogDto;
import com.travelapp.models.users.UserBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ISearchLogService {

    SearchLogDto save(SearchLogDto searchLogDto);
    
    SearchLogDto update(Long id, SearchLogDto searchLogDto);

    Optional<SearchLogDto> findById(Long id);

    List<SearchLogDto> findAll();

    Page<SearchLogDto> findAll(Pageable pageable);

    void deleteById(Long id);

    boolean existsById(Long id);

    List<SearchLogDto> findByUserId(Long userId);

    Page<SearchLogDto> findByUserId(Long userId, Pageable pageable);

    List<SearchLogDto> findByUser(UserBase user);

    Page<SearchLogDto> findByUser(UserBase user, Pageable pageable);

    List<SearchLogDto> findBySessionId(String sessionId);

    List<SearchLogDto> findByQueryContaining(String query);

    List<SearchLogDto> findByIpAddress(String ipAddress);

    List<SearchLogDto> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<SearchLogDto> findByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    List<SearchLogDto> findRecentSearches(int limit);

    List<SearchLogDto> findRecentSearchesByUserId(Long userId, int limit);

    List<Object[]> findPopularQueries(int limit);

    List<SearchLogDto> findByResultCountGreaterThan(Integer resultCount);

    List<SearchLogDto> findFilteredSearches();

    Long countByUserId(Long userId);

    List<Object[]> getDailySearchStatistics();

    SearchLogDto logSearch(String query, String filters, Integer resultCount,
                           String sessionId, String ipAddress, String userAgent,
                           Long userId);
}
