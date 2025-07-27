package com.travelapp.services.configurations;

import com.travelapp.dto.configurations.PageViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IPageViewService {

    PageViewDto create(PageViewDto pageViewDto);

    PageViewDto update(Long id, PageViewDto pageViewDto);

    Optional<PageViewDto> findById(Long id);

    List<PageViewDto> findAll();

    Page<PageViewDto> findAll(Pageable pageable);

    void deleteById(Long id);

    boolean existsById(Long id);

    List<PageViewDto> findByUserId(Long userId);

    List<PageViewDto> findBySessionId(String sessionId);

    List<PageViewDto> findByUrl(String url);

    List<PageViewDto> findByIpAddress(String ipAddress);

    List<PageViewDto> findByViewedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<PageViewDto> findByUserIdAndViewedAtBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    Page<PageViewDto> findByUserId(Long userId, Pageable pageable);

    Page<PageViewDto> findBySessionId(String sessionId, Pageable pageable);

    Page<PageViewDto> findByUrl(String url, Pageable pageable);

    Page<PageViewDto> findByViewedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Long countByUrl(String url);

    Long countByUserId(Long userId);

    Long countBySessionId(String sessionId);

    List<String> findDistinctUrlsByUserId(Long userId);

    List<PageViewDto> findRecentPageViewsByUserId(Long userId, int limit);

    Optional<PageViewDto> findLastPageViewByUserId(Long userId);

    Optional<PageViewDto> findLastPageViewBySessionId(String sessionId);

    void trackPageView(String url, String sessionId, Long userId, String referrer,
                       String ipAddress, String userAgent, String device,
                       String browser, String os);

    void updateViewDuration(Long pageViewId, Long duration);
}
