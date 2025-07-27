package com.travelapp.controller.impl.configuration;

import com.travelapp.controller.configurations.ISearchLogController;
import com.travelapp.dto.configurations.SearchLogDto;
import com.travelapp.services.configurations.ISearchLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/search-log")
@RequiredArgsConstructor
public class SearchLogControllerImpl implements ISearchLogController {

    private final ISearchLogService searchLogService;

    @Override
    @PostMapping("/save")
    public ResponseEntity<SearchLogDto> createSearchLog(@RequestBody SearchLogDto searchLogDto) {
        SearchLogDto createdSearchLog = searchLogService.save(searchLogDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSearchLog);
    }

    @Override
    @PutMapping("/{id}")
    public SearchLogDto updateSearchLog(@PathVariable Long id, @RequestBody SearchLogDto searchLogDto) {
        return searchLogService.update(id, searchLogDto);
    }

    @Override
    @GetMapping
    public List<SearchLogDto> getAllSearchLogs() {
        return searchLogService.findAll();
    }

    @Override
    @GetMapping("/pageable")
    public Page<SearchLogDto> getAllSearchLogs(Pageable pageable) {
        return searchLogService.findAll(pageable);
    }

    @Override
    @GetMapping("/{id}")
    public SearchLogDto getSearchLogById(@PathVariable Long id) {
        return searchLogService.findById(id)
                .orElseThrow(() -> new RuntimeException("SearchLog not found with id: " + id));
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteSearchLog(@PathVariable Long id) {
        searchLogService.deleteById(id);
    }

    @Override
    @GetMapping("/user/{userId}")
    public List<SearchLogDto> getSearchLogsByUserId(@PathVariable Long userId) {
        return searchLogService.findByUserId(userId);
    }

    @Override
    @GetMapping("/user/{userId}/pageable")
    public Page<SearchLogDto> getSearchLogsByUserId(@PathVariable Long userId, Pageable pageable) {
        return searchLogService.findByUserId(userId, pageable);
    }

    @Override
    @GetMapping("/session/{sessionId}")
    public List<SearchLogDto> getSearchLogsBySessionId(@PathVariable String sessionId) {
        return searchLogService.findBySessionId(sessionId);
    }

    @Override
    @GetMapping("/query")
    public List<SearchLogDto> getSearchLogsByQuery(@RequestParam String query) {
        return searchLogService.findByQueryContaining(query);
    }

    @Override
    @GetMapping("/ip/{ipAddress}")
    public List<SearchLogDto> getSearchLogsByIpAddress(@PathVariable String ipAddress) {
        return searchLogService.findByIpAddress(ipAddress);
    }

    @Override
    @GetMapping("/date-range")
    public List<SearchLogDto> getSearchLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return searchLogService.findByDateRange(startDate, endDate);
    }

    @Override
    @GetMapping("/user/{userId}/date-range")
    public List<SearchLogDto> getSearchLogsByUserIdAndDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return searchLogService.findByUserIdAndDateRange(userId, startDate, endDate);
    }

    @Override
    @GetMapping("/recent")
    public List<SearchLogDto> getRecentSearchLogs(@RequestParam(defaultValue = "10") int limit) {
        return searchLogService.findRecentSearches(limit);
    }

    @Override
    @GetMapping("/user/{userId}/recent")
    public List<SearchLogDto> getRecentSearchLogsByUserId(@PathVariable Long userId, @RequestParam(defaultValue = "10") int limit) {
        return searchLogService.findRecentSearchesByUserId(userId, limit);
    }

    @Override
    @GetMapping("/popular-queries")
    public List<Object[]> getPopularQueries(@RequestParam(defaultValue = "10") int limit) {
        return searchLogService.findPopularQueries(limit);
    }

    @Override
    @GetMapping("/result-count-greater-than")
    public List<SearchLogDto> getSearchLogsByResultCountGreaterThan(@RequestParam Integer resultCount) {
        return searchLogService.findByResultCountGreaterThan(resultCount);
    }

    @Override
    @GetMapping("/filtered")
    public List<SearchLogDto> getFilteredSearchLogs() {
        return searchLogService.findFilteredSearches();
    }

    @Override
    @GetMapping("/count/user/{userId}")
    public Long getSearchLogCountByUserId(@PathVariable Long userId) {
        return searchLogService.countByUserId(userId);
    }

    @Override
    @GetMapping("/statistics/daily")
    public List<Object[]> getDailySearchStatistics() {
        return searchLogService.getDailySearchStatistics();
    }

    @Override
    @PostMapping("/log")
    public ResponseEntity<SearchLogDto> logSearch(
            @RequestParam String query,
            @RequestParam(required = false) String filters,
            @RequestParam(required = false) Integer resultCount,
            @RequestParam(required = false) String sessionId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String ipAddress,
            @RequestParam(required = false) String userAgent) {
        SearchLogDto searchLogDto = searchLogService.logSearch(query, filters, resultCount, sessionId, ipAddress, userAgent, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(searchLogDto);
    }

    @Override
    @PostMapping("/track")
    public ResponseEntity<Void> trackSearch(
            @RequestParam String query,
            @RequestParam(required = false) String sessionId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String filters,
            @RequestParam(required = false) Integer resultCount,
            @RequestParam(required = false) String ipAddress,
            @RequestParam(required = false) String userAgent) {
        searchLogService.logSearch(query, filters, resultCount, sessionId, ipAddress, userAgent, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
