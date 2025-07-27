package com.travelapp.services.impl.configurations;

import com.travelapp.dto.configurations.SearchLogDto;
import com.travelapp.helper.errorhandler.ResourceNotFoundException;
import com.travelapp.models.configurations.SearchLog;
import com.travelapp.models.users.UserBase;
import com.travelapp.repository.configurations.SearchLogRepository;
import com.travelapp.repository.users.UserBaseRepository;
import com.travelapp.services.configurations.ISearchLogService;
import com.travelapp.services.impl.configurations.mappers.SearchLogMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchLogServiceImpl implements ISearchLogService {

    private final SearchLogRepository searchLogRepository;
    private final UserBaseRepository userBaseRepository;
    private final SearchLogMapperService searchLogMapperService;

    @Override
    public SearchLogDto save(SearchLogDto searchLogDto) {
        SearchLog searchLog = searchLogMapperService.toEntity(searchLogDto);

        if (searchLogDto.getUserId() != null) {
            UserBase user = userBaseRepository.findById(searchLogDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: ", searchLogDto.getUserId().toString()));
            searchLog.setUser(user);
        }

        SearchLog savedSearchLog = searchLogRepository.save(searchLog);

        return searchLogMapperService.toDto(savedSearchLog);
    }

    @Override
    public SearchLogDto update(Long id, SearchLogDto searchLogDto) {
        SearchLog existingSearchLog = searchLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Search log not found with id: ", id.toString()));

        searchLogMapperService.updateEntityFromDto(searchLogDto, existingSearchLog);

        if (searchLogDto.getUserId() != null) {
            UserBase user = userBaseRepository.findById(searchLogDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + searchLogDto.getUserId()));
            existingSearchLog.setUser(user);
        }

        SearchLog updatedSearchLog = searchLogRepository.save(existingSearchLog);

        return searchLogMapperService.toDto(updatedSearchLog);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SearchLogDto> findById(Long id) {
        return searchLogRepository.findById(id)
                .map(searchLogMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchLogDto> findAll() {
        List<SearchLog> searchLogs = searchLogRepository.findAll();
        return searchLogMapperService.toDtoList(searchLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SearchLogDto> findAll(Pageable pageable) {
        Page<SearchLog> searchLogPage = searchLogRepository.findAll(pageable);
        return searchLogPage.map(searchLogMapperService::toDto);
    }

    @Override
    public void deleteById(Long id) {
        if (!searchLogRepository.existsById(id)) {
            throw new RuntimeException("Search log not found with id: " + id);
        }

        searchLogRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return searchLogRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchLogDto> findByUserId(Long userId) {
        List<SearchLog> searchLogs = searchLogRepository.findByUserId(userId);
        return searchLogMapperService.toDtoList(searchLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SearchLogDto> findByUserId(Long userId, Pageable pageable) {
        Page<SearchLog> searchLogPage = searchLogRepository.findByUserId(userId, pageable);
        return searchLogPage.map(searchLogMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchLogDto> findByUser(UserBase user) {
        List<SearchLog> searchLogs = searchLogRepository.findByUser(user);
        return searchLogMapperService.toDtoList(searchLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SearchLogDto> findByUser(UserBase user, Pageable pageable) {
        Page<SearchLog> searchLogPage = searchLogRepository.findByUser(user, pageable);
        return searchLogPage.map(searchLogMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchLogDto> findBySessionId(String sessionId) {
        List<SearchLog> searchLogs = searchLogRepository.findBySessionId(sessionId);
        return searchLogMapperService.toDtoList(searchLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchLogDto> findByQueryContaining(String query) {
        List<SearchLog> searchLogs = searchLogRepository.findByQueryContaining(query);
        return searchLogMapperService.toDtoList(searchLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchLogDto> findByIpAddress(String ipAddress) {
        List<SearchLog> searchLogs = searchLogRepository.findByIpAddress(ipAddress);
        return searchLogMapperService.toDtoList(searchLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchLogDto> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<SearchLog> searchLogs = searchLogRepository.findBySearchedAtBetween(startDate, endDate);
        return searchLogMapperService.toDtoList(searchLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchLogDto> findByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<SearchLog> searchLogs = searchLogRepository.findByUserIdAndSearchedAtBetween(userId, startDate, endDate);
        return searchLogMapperService.toDtoList(searchLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchLogDto> findRecentSearches(int limit) {
        List<SearchLog> searchLogs = searchLogRepository.findTop10ByOrderBySearchedAtDesc();
        return searchLogMapperService.toDtoList(searchLogs).stream()
                .limit(limit)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchLogDto> findRecentSearchesByUserId(Long userId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<SearchLog> searchLogs = searchLogRepository.findRecentSearchesByUserId(userId, pageable);
        return searchLogMapperService.toDtoList(searchLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findPopularQueries(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return searchLogRepository.findPopularQueries(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchLogDto> findByResultCountGreaterThan(Integer resultCount) {
        List<SearchLog> searchLogs = searchLogRepository.findByResultCountGreaterThan(resultCount);
        return searchLogMapperService.toDtoList(searchLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchLogDto> findFilteredSearches() {
        List<SearchLog> searchLogs = searchLogRepository.findFilteredSearches();
        return searchLogMapperService.toDtoList(searchLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByUserId(Long userId) {
        return searchLogRepository.countByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getDailySearchStatistics() {
        return searchLogRepository.getDailySearchStatistics();
    }

    @Override
    public SearchLogDto logSearch(String query, String filters, Integer resultCount,
                                  String sessionId, String ipAddress, String userAgent,
                                  Long userId) {

        SearchLogDto searchLogDto = new SearchLogDto();
        searchLogDto.setQuery(query);
        searchLogDto.setFilters(filters);
        searchLogDto.setResultCount(resultCount);
        searchLogDto.setSessionId(sessionId);
        searchLogDto.setIpAddress(ipAddress);
        searchLogDto.setUserAgent(userAgent);
        searchLogDto.setUserId(userId);
        searchLogDto.setSearchedAt(LocalDateTime.now());

        return save(searchLogDto);
    }
}
