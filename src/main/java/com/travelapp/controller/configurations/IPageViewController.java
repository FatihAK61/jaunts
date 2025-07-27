package com.travelapp.controller.configurations;

import com.travelapp.dto.configurations.PageViewDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface IPageViewController {

    ResponseEntity<PageViewDto> createPageView(@Valid @NotNull PageViewDto pageViewDto);

    PageViewDto updatePageView(Long id, PageViewDto pageViewDto);

    List<PageViewDto> getAllPageViews();

    Page<PageViewDto> getAllPageViews(Pageable pageable);

    PageViewDto getPageViewById(Long id);

    void deletePageView(Long id);

    List<PageViewDto> getPageViewsByUserId(Long userId);

    Page<PageViewDto> getPageViewsByUserId(Long userId, Pageable pageable);

    List<PageViewDto> getPageViewsBySessionId(String sessionId);

    List<PageViewDto> getPageViewsByUrl(String url);

    List<PageViewDto> getPageViewsByIpAddress(String ipAddress);

    List<PageViewDto> getPageViewsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<PageViewDto> getPageViewsByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    Long getPageViewCountByUrl(String url);

    Long getPageViewCountByUserId(Long userId);

    Long getPageViewCountBySessionId(String sessionId);

    List<String> getDistinctUrlsByUserId(Long userId);

    List<PageViewDto> getRecentPageViewsByUserId(Long userId, int limit);

    PageViewDto getLastPageViewByUserId(Long userId);

    PageViewDto getLastPageViewBySessionId(String sessionId);

    ResponseEntity<Void> trackPageView(String url, String sessionId, Long userId, String referrer,
                                       String ipAddress, String userAgent, String device,
                                       String browser, String os);

    ResponseEntity<Void> updateViewDuration(Long pageViewId, Long duration);
}
