package com.travelapp.controller.configurations;

import com.travelapp.dto.configurations.SearchLogDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface ISearchLogController {

    ResponseEntity<SearchLogDto> createSearchLog(@Valid @NotNull SearchLogDto searchLogDto);

    SearchLogDto updateSearchLog(Long id, SearchLogDto searchLogDto);

    List<SearchLogDto> getAllSearchLogs();

    Page<SearchLogDto> getAllSearchLogs(Pageable pageable);

    SearchLogDto getSearchLogById(Long id);

    void deleteSearchLog(Long id);

    List<SearchLogDto> getSearchLogsByUserId(Long userId);

    Page<SearchLogDto> getSearchLogsByUserId(Long userId, Pageable pageable);

    List<SearchLogDto> getSearchLogsBySessionId(String sessionId);

    List<SearchLogDto> getSearchLogsByQuery(String query);

    List<SearchLogDto> getSearchLogsByIpAddress(String ipAddress);

    List<SearchLogDto> getSearchLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<SearchLogDto> getSearchLogsByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    List<SearchLogDto> getRecentSearchLogs(int limit);

    List<SearchLogDto> getRecentSearchLogsByUserId(Long userId, int limit);

    List<Object[]> getPopularQueries(int limit);

    List<SearchLogDto> getSearchLogsByResultCountGreaterThan(Integer resultCount);

    List<SearchLogDto> getFilteredSearchLogs();

    Long getSearchLogCountByUserId(Long userId);

    List<Object[]> getDailySearchStatistics();

    ResponseEntity<SearchLogDto> logSearch(String query, String filters, Integer resultCount,
                                           String sessionId, Long userId, String ipAddress,
                                           String userAgent);

    ResponseEntity<Void> trackSearch(String query, String sessionId, Long userId, String filters,
                                     Integer resultCount, String ipAddress, String userAgent);
}
