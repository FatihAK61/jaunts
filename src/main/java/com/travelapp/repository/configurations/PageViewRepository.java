package com.travelapp.repository.configurations;

import com.travelapp.models.configurations.PageView;
import com.travelapp.models.users.UserBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PageViewRepository extends JpaRepository<PageView, Long>, JpaSpecificationExecutor<PageView> {

    List<PageView> findByUser(UserBase user);

    List<PageView> findBySessionId(String sessionId);

    List<PageView> findByUrl(String url);

    List<PageView> findByIpAddress(String ipAddress);

    List<PageView> findByViewedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<PageView> findByUserAndViewedAtBetween(UserBase user, LocalDateTime startDate, LocalDateTime endDate);

    Page<PageView> findByUser(UserBase user, Pageable pageable);

    Page<PageView> findBySessionId(String sessionId, Pageable pageable);

    Page<PageView> findByUrl(String url, Pageable pageable);

    Page<PageView> findByViewedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query("SELECT pv FROM PageView pv WHERE pv.user.id = :userId")
    List<PageView> findByUserId(@Param("userId") Long userId);

    @Query("SELECT pv FROM PageView pv WHERE pv.user.id = :userId AND pv.viewedAt BETWEEN :startDate AND :endDate")
    List<PageView> findByUserIdAndViewedAtBetween(@Param("userId") Long userId,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(pv) FROM PageView pv WHERE pv.url = :url")
    Long countByUrl(@Param("url") String url);

    @Query("SELECT COUNT(pv) FROM PageView pv WHERE pv.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(pv) FROM PageView pv WHERE pv.sessionId = :sessionId")
    Long countBySessionId(@Param("sessionId") String sessionId);

    @Query("SELECT DISTINCT pv.url FROM PageView pv WHERE pv.user.id = :userId ORDER BY pv.viewedAt DESC")
    List<String> findDistinctUrlsByUserId(@Param("userId") Long userId);

    @Query("SELECT pv FROM PageView pv WHERE pv.user.id = :userId ORDER BY pv.viewedAt DESC")
    List<PageView> findByUserIdOrderByViewedAtDesc(@Param("userId") Long userId, Pageable pageable);

    Optional<PageView> findTopByUserOrderByViewedAtDesc(UserBase user);

    Optional<PageView> findTopBySessionIdOrderByViewedAtDesc(String sessionId);
}
