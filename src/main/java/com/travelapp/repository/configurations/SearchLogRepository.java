package com.travelapp.repository.configurations;

import com.travelapp.models.configurations.SearchLog;
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

@Repository
public interface SearchLogRepository extends JpaRepository<SearchLog, Long>, JpaSpecificationExecutor<SearchLog> {

    List<SearchLog> findByUser(UserBase user);

    Page<SearchLog> findByUser(UserBase user, Pageable pageable);

    List<SearchLog> findByUserId(Long userId);

    Page<SearchLog> findByUserId(Long userId, Pageable pageable);

    List<SearchLog> findBySessionId(String sessionId);

    @Query("SELECT sl FROM SearchLog sl WHERE LOWER(sl.query) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<SearchLog> findByQueryContaining(@Param("query") String query);

    List<SearchLog> findByIpAddress(String ipAddress);

    @Query("SELECT sl FROM SearchLog sl WHERE sl.searchedAt BETWEEN :startDate AND :endDate")
    List<SearchLog> findBySearchedAtBetween(@Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT sl FROM SearchLog sl WHERE sl.user.id = :userId AND sl.searchedAt BETWEEN :startDate AND :endDate")
    List<SearchLog> findByUserIdAndSearchedAtBetween(@Param("userId") Long userId,
                                                     @Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate);

    List<SearchLog> findTop10ByOrderBySearchedAtDesc();

    @Query("SELECT sl FROM SearchLog sl WHERE sl.user.id = :userId ORDER BY sl.searchedAt DESC")
    List<SearchLog> findRecentSearchesByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT sl.query, COUNT(sl) as searchCount FROM SearchLog sl GROUP BY sl.query ORDER BY searchCount DESC")
    List<Object[]> findPopularQueries(Pageable pageable);

    List<SearchLog> findByResultCountGreaterThan(Integer resultCount);

    @Query("SELECT sl FROM SearchLog sl WHERE sl.filters IS NOT NULL AND sl.filters != ''")
    List<SearchLog> findFilteredSearches();

    @Query("SELECT COUNT(sl) FROM SearchLog sl WHERE sl.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT DATE(sl.searched_at) AS search_date, COUNT(sl.*) AS search_count " +
            "FROM search_logs sl " +
            "GROUP BY DATE(sl.searched_at) " +
            "ORDER BY search_date DESC", nativeQuery = true)
    List<Object[]> getDailySearchStatistics();

}
