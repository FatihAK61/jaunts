package com.travelapp.services.preferences;

import com.travelapp.dto.preferences.UserPreferenceDto;
import com.travelapp.helper.enums.PreferenceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserPreferenceService {

    UserPreferenceDto createPreference(UserPreferenceDto userPreferenceDto);

    UserPreferenceDto updatePreference(Long id, UserPreferenceDto userPreferenceDto);

    Optional<UserPreferenceDto> getPreferenceById(Long id);

    List<UserPreferenceDto> getPreferencesByUserId(Long userId);

    Page<UserPreferenceDto> getPreferencesByUserId(Long userId, Pageable pageable);

    List<UserPreferenceDto> getPreferencesByUserIdAndType(Long userId, PreferenceType type);

    Optional<UserPreferenceDto> getPreferenceByUserIdAndKey(Long userId, String preferenceKey);

    Optional<UserPreferenceDto> getPreferenceByUserIdAndKeyAndType(Long userId, String preferenceKey, PreferenceType type);

    UserPreferenceDto saveOrUpdatePreference(Long userId, String preferenceKey, String preferenceValue, PreferenceType type);

    boolean deletePreference(Long id);

    boolean deletePreferenceByUserIdAndKey(Long userId, String preferenceKey);

    void deleteAllPreferencesByUserId(Long userId);

    boolean existsPreferenceByUserIdAndKey(Long userId, String preferenceKey);

    long countPreferencesByUserId(Long userId);

    List<UserPreferenceDto> getAllPreferences();

    Page<UserPreferenceDto> getAllPreferences(Pageable pageable);

}
