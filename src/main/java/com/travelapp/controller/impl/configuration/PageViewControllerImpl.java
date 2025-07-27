package com.travelapp.controller.impl.configuration;

import com.travelapp.controller.configurations.IPageViewController;
import com.travelapp.dto.configurations.PageViewDto;
import com.travelapp.services.configurations.IPageViewService;
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
@RequestMapping("/api/v1/page-view")
@RequiredArgsConstructor
public class PageViewControllerImpl implements IPageViewController {

    private final IPageViewService pageViewService;

    @Override
    @PostMapping("/save")
    public ResponseEntity<PageViewDto> createPageView(@RequestBody PageViewDto pageViewDto) {
        PageViewDto createdPageView = pageViewService.create(pageViewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPageView);
    }

    @Override
    @PutMapping("/{id}")
    public PageViewDto updatePageView(@PathVariable Long id, @RequestBody PageViewDto pageViewDto) {
        return pageViewService.update(id, pageViewDto);
    }

    @Override
    @GetMapping
    public List<PageViewDto> getAllPageViews() {
        return pageViewService.findAll();
    }

    @Override
    @GetMapping("/pageable")
    public Page<PageViewDto> getAllPageViews(Pageable pageable) {
        return pageViewService.findAll(pageable);
    }

    @Override
    @GetMapping("/{id}")
    public PageViewDto getPageViewById(@PathVariable Long id) {
        return pageViewService.findById(id)
                .orElseThrow(() -> new RuntimeException("PageView not found with id: " + id));
    }

    @Override
    @DeleteMapping("/{id}")
    public void deletePageView(@PathVariable Long id) {
        pageViewService.deleteById(id);
    }

    @Override
    @GetMapping("/user/{userId}")
    public List<PageViewDto> getPageViewsByUserId(@PathVariable Long userId) {
        return pageViewService.findByUserId(userId);
    }

    @Override
    @GetMapping("/user/{userId}/pageable")
    public Page<PageViewDto> getPageViewsByUserId(@PathVariable Long userId, Pageable pageable) {
        return pageViewService.findByUserId(userId, pageable);
    }

    @Override
    @GetMapping("/session/{sessionId}")
    public List<PageViewDto> getPageViewsBySessionId(@PathVariable String sessionId) {
        return pageViewService.findBySessionId(sessionId);
    }

    @Override
    @GetMapping("/url")
    public List<PageViewDto> getPageViewsByUrl(@RequestParam String url) {
        return pageViewService.findByUrl(url);
    }

    @Override
    @GetMapping("/ip/{ipAddress}")
    public List<PageViewDto> getPageViewsByIpAddress(@PathVariable String ipAddress) {
        return pageViewService.findByIpAddress(ipAddress);
    }

    @Override
    @GetMapping("/date-range")
    public List<PageViewDto> getPageViewsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return pageViewService.findByViewedAtBetween(startDate, endDate);
    }

    @Override
    @GetMapping("/user/{userId}/date-range")
    public List<PageViewDto> getPageViewsByUserIdAndDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return pageViewService.findByUserIdAndViewedAtBetween(userId, startDate, endDate);
    }

    @Override
    @GetMapping("/count/url")
    public Long getPageViewCountByUrl(@RequestParam String url) {
        return pageViewService.countByUrl(url);
    }

    @Override
    @GetMapping("/count/user/{userId}")
    public Long getPageViewCountByUserId(@PathVariable Long userId) {
        return pageViewService.countByUserId(userId);
    }

    @Override
    @GetMapping("/count/session/{sessionId}")
    public Long getPageViewCountBySessionId(@PathVariable String sessionId) {
        return pageViewService.countBySessionId(sessionId);
    }

    @Override
    @GetMapping("/user/{userId}/distinct-urls")
    public List<String> getDistinctUrlsByUserId(@PathVariable Long userId) {
        return pageViewService.findDistinctUrlsByUserId(userId);
    }

    @Override
    @GetMapping("/user/{userId}/recent")
    public List<PageViewDto> getRecentPageViewsByUserId(@PathVariable Long userId, @RequestParam(defaultValue = "10") int limit) {
        return pageViewService.findRecentPageViewsByUserId(userId, limit);
    }

    @Override
    @GetMapping("/user/{userId}/last")
    public PageViewDto getLastPageViewByUserId(@PathVariable Long userId) {
        return pageViewService.findLastPageViewByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No page view found for user id: " + userId));
    }

    @Override
    @GetMapping("/session/{sessionId}/last")
    public PageViewDto getLastPageViewBySessionId(@PathVariable String sessionId) {
        return pageViewService.findLastPageViewBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("No page view found for session id: " + sessionId));
    }

    @Override
    @PostMapping("/track")
    public ResponseEntity<Void> trackPageView(
            @RequestParam String url,
            @RequestParam String sessionId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String referrer,
            @RequestParam(required = false) String ipAddress,
            @RequestParam(required = false) String userAgent,
            @RequestParam(required = false) String device,
            @RequestParam(required = false) String browser,
            @RequestParam(required = false) String os) {

        pageViewService.trackPageView(url, sessionId, userId, referrer, ipAddress, userAgent, device, browser, os);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @PutMapping("/{pageViewId}/duration")
    public ResponseEntity<Void> updateViewDuration(@PathVariable Long pageViewId, @RequestParam Long duration) {
        pageViewService.updateViewDuration(pageViewId, duration);
        return ResponseEntity.ok().build();
    }
}
