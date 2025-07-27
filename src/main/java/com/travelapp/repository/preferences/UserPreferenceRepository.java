package com.travelapp.repository.preferences;

import com.travelapp.helper.enums.PreferenceType;
import com.travelapp.models.preferences.UserPreference;
import com.travelapp.models.users.UserBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long>, JpaSpecificationExecutor<UserPreference> {

    List<UserPreference> findByUser(UserBase user);

    List<UserPreference> findByUserId(Long userId);

    Page<UserPreference> findByUserId(Long userId, Pageable pageable);

    List<UserPreference> findByUserIdAndType(Long userId, PreferenceType type);

    Optional<UserPreference> findByUserIdAndPreferenceKey(Long userId, String preferenceKey);

    @Query("SELECT up FROM UserPreference up WHERE up.user.id = :userId AND up.preferenceKey = :preferenceKey AND up.type = :type")
    Optional<UserPreference> findByUserIdAndPreferenceKeyAndType(@Param("userId") Long userId, @Param("preferenceKey") String preferenceKey, @Param("type") PreferenceType type);

    boolean existsByUserIdAndPreferenceKey(Long userId, String preferenceKey);

    void deleteByUserIdAndPreferenceKey(Long userId, String preferenceKey);

    void deleteByUserId(Long userId);

    @Query("SELECT COUNT(up) FROM UserPreference up WHERE up.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

}
