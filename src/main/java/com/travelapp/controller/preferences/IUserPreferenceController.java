package com.travelapp.controller.preferences;

import com.travelapp.dto.preferences.UserPreferenceDto;
import com.travelapp.helper.enums.PreferenceType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserPreferenceController {

    ResponseEntity<UserPreferenceDto> createPreference(@Valid @NotNull UserPreferenceDto userPreferenceDto);

    ResponseEntity<UserPreferenceDto> updatePreference(Long id, @Valid @NotNull UserPreferenceDto userPreferenceDto);

    ResponseEntity<UserPreferenceDto> getPreferenceById(Long id);

    ResponseEntity<List<UserPreferenceDto>> getPreferencesByUserId(Long userId);

    ResponseEntity<Page<UserPreferenceDto>> getPreferencesByUserIdWithPagination(Long userId, Pageable pageable);

    ResponseEntity<List<UserPreferenceDto>> getPreferencesByUserIdAndType(Long userId, PreferenceType type);

    ResponseEntity<UserPreferenceDto> getPreferenceByUserIdAndKey(Long userId, String preferenceKey);

    ResponseEntity<UserPreferenceDto> getPreferenceByUserIdAndKeyAndType(Long userId, String preferenceKey, PreferenceType type);

    ResponseEntity<UserPreferenceDto> saveOrUpdatePreference(Long userId, String preferenceKey, String preferenceValue, PreferenceType type);

    ResponseEntity<Void> deletePreference(Long id);

    ResponseEntity<Void> deletePreferenceByUserIdAndKey(Long userId, String preferenceKey);

    ResponseEntity<Void> deleteAllPreferencesByUserId(Long userId);

    ResponseEntity<Boolean> existsPreferenceByUserIdAndKey(Long userId, String preferenceKey);

    ResponseEntity<Long> countPreferencesByUserId(Long userId);

    ResponseEntity<List<UserPreferenceDto>> getAllPreferences();

    ResponseEntity<Page<UserPreferenceDto>> getAllPreferencesWithPagination(Pageable pageable);

}
