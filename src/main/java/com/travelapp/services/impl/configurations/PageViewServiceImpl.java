package com.travelapp.services.impl.configurations;

import com.travelapp.dto.configurations.PageViewDto;
import com.travelapp.helper.errorhandler.ResourceNotFoundException;
import com.travelapp.models.configurations.PageView;
import com.travelapp.models.users.UserBase;
import com.travelapp.repository.configurations.PageViewRepository;
import com.travelapp.repository.users.UserBaseRepository;
import com.travelapp.services.configurations.IPageViewService;
import com.travelapp.services.impl.configurations.mappers.PageViewMapperService;
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
@Transactional
public class PageViewServiceImpl implements IPageViewService {

    private final PageViewRepository pageViewRepository;
    private final UserBaseRepository userBaseRepository;
    private final PageViewMapperService pageViewMapperService;

    @Override
    public PageViewDto create(PageViewDto pageViewDto) {
        PageView pageView = pageViewMapperService.toEntity(pageViewDto);

        if (pageViewDto.getUserId() != null) {
            UserBase user = userBaseRepository.findById(pageViewDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: ", pageViewDto.getUserId().toString()));
            pageView.setUser(user);
        }

        PageView savedPageView = pageViewRepository.save(pageView);

        return pageViewMapperService.toDto(savedPageView);
    }

    @Override
    public PageViewDto update(Long id, PageViewDto pageViewDto) {
        PageView existingPageView = pageViewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PageView not found with id: ", id.toString()));

        pageViewMapperService.updateEntityFromDto(pageViewDto, existingPageView);

        if (pageViewDto.getUserId() != null && !pageViewDto.getUserId().equals(
                existingPageView.getUser() != null ? existingPageView.getUser().getId() : null)) {
            UserBase user = userBaseRepository.findById(pageViewDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: ", pageViewDto.getUserId().toString()));
            existingPageView.setUser(user);
        }

        PageView updatedPageView = pageViewRepository.save(existingPageView);

        return pageViewMapperService.toDto(updatedPageView);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PageViewDto> findById(Long id) {
        return pageViewRepository.findById(id)
                .map(pageViewMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageViewDto> findAll() {
        List<PageView> pageViews = pageViewRepository.findAll();
        return pageViewMapperService.toDtoList(pageViews);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PageViewDto> findAll(Pageable pageable) {
        Page<PageView> pageViews = pageViewRepository.findAll(pageable);
        return pageViews.map(pageViewMapperService::toDto);
    }

    @Override
    public void deleteById(Long id) {
        if (!pageViewRepository.existsById(id)) {
            throw new ResourceNotFoundException("PageView not found with id: ", id.toString());
        }

        pageViewRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return pageViewRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageViewDto> findByUserId(Long userId) {
        List<PageView> pageViews = pageViewRepository.findByUserId(userId);
        return pageViewMapperService.toDtoList(pageViews);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageViewDto> findBySessionId(String sessionId) {
        List<PageView> pageViews = pageViewRepository.findBySessionId(sessionId);
        return pageViewMapperService.toDtoList(pageViews);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageViewDto> findByUrl(String url) {
        List<PageView> pageViews = pageViewRepository.findByUrl(url);
        return pageViewMapperService.toDtoList(pageViews);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageViewDto> findByIpAddress(String ipAddress) {
        List<PageView> pageViews = pageViewRepository.findByIpAddress(ipAddress);
        return pageViewMapperService.toDtoList(pageViews);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageViewDto> findByViewedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<PageView> pageViews = pageViewRepository.findByViewedAtBetween(startDate, endDate);
        return pageViewMapperService.toDtoList(pageViews);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageViewDto> findByUserIdAndViewedAtBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<PageView> pageViews = pageViewRepository.findByUserIdAndViewedAtBetween(userId, startDate, endDate);
        return pageViewMapperService.toDtoList(pageViews);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PageViewDto> findByUserId(Long userId, Pageable pageable) {
        UserBase user = userBaseRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: ", userId.toString()));

        Page<PageView> pageViews = pageViewRepository.findByUser(user, pageable);
        return pageViews.map(pageViewMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PageViewDto> findBySessionId(String sessionId, Pageable pageable) {
        Page<PageView> pageViews = pageViewRepository.findBySessionId(sessionId, pageable);
        return pageViews.map(pageViewMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PageViewDto> findByUrl(String url, Pageable pageable) {
        Page<PageView> pageViews = pageViewRepository.findByUrl(url, pageable);
        return pageViews.map(pageViewMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PageViewDto> findByViewedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Page<PageView> pageViews = pageViewRepository.findByViewedAtBetween(startDate, endDate, pageable);
        return pageViews.map(pageViewMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByUrl(String url) {
        return pageViewRepository.countByUrl(url);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByUserId(Long userId) {
        return pageViewRepository.countByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countBySessionId(String sessionId) {
        return pageViewRepository.countBySessionId(sessionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findDistinctUrlsByUserId(Long userId) {
        return pageViewRepository.findDistinctUrlsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageViewDto> findRecentPageViewsByUserId(Long userId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<PageView> pageViews = pageViewRepository.findByUserIdOrderByViewedAtDesc(userId, pageable);
        return pageViewMapperService.toDtoList(pageViews);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PageViewDto> findLastPageViewByUserId(Long userId) {
        UserBase user = userBaseRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: ", userId.toString()));

        return pageViewRepository.findTopByUserOrderByViewedAtDesc(user)
                .map(pageViewMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PageViewDto> findLastPageViewBySessionId(String sessionId) {
        return pageViewRepository.findTopBySessionIdOrderByViewedAtDesc(sessionId)
                .map(pageViewMapperService::toDto);
    }

    @Override
    public void trackPageView(String url, String sessionId, Long userId, String referrer,
                              String ipAddress, String userAgent, String device,
                              String browser, String os) {
        PageView pageView = new PageView();
        pageView.setUrl(url);
        pageView.setSessionId(sessionId);
        pageView.setReferrer(referrer);
        pageView.setIpAddress(ipAddress);
        pageView.setUserAgent(userAgent);
        pageView.setDevice(device);
        pageView.setBrowser(browser);
        pageView.setOs(os);

        if (userId != null) {
            UserBase user = userBaseRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: ", userId.toString()));
            pageView.setUser(user);
        }

        pageViewRepository.save(pageView);
    }

    @Override
    public void updateViewDuration(Long pageViewId, Long duration) {
        PageView pageView = pageViewRepository.findById(pageViewId)
                .orElseThrow(() -> new ResourceNotFoundException("PageView not found with id: ", pageViewId.toString()));

        pageView.setViewDuration(duration);
        pageViewRepository.save(pageView);
    }
}
