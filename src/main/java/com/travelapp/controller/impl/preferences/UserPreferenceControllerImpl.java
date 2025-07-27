package com.travelapp.controller.impl.preferences;

import com.travelapp.controller.preferences.IUserPreferenceController;
import com.travelapp.dto.preferences.UserPreferenceDto;
import com.travelapp.helper.enums.PreferenceType;
import com.travelapp.services.preferences.IUserPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user-preference")
@RequiredArgsConstructor
public class UserPreferenceControllerImpl implements IUserPreferenceController {

    private final IUserPreferenceService userPreferenceService;

    @Override
    @PostMapping(path = "/save")
    public ResponseEntity<UserPreferenceDto> createPreference(@RequestBody UserPreferenceDto userPreferenceDto) {
        UserPreferenceDto createdPreference = userPreferenceService.createPreference(userPreferenceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPreference);
    }

    @Override
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<UserPreferenceDto> updatePreference(@PathVariable(name = "id") Long id, @RequestBody UserPreferenceDto userPreferenceDto) {
        UserPreferenceDto updatedPreference = userPreferenceService.updatePreference(id, userPreferenceDto);
        return ResponseEntity.ok(updatedPreference);
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserPreferenceDto> getPreferenceById(@PathVariable(name = "id") Long id) {
        Optional<UserPreferenceDto> preference = userPreferenceService.getPreferenceById(id);
        return preference.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<UserPreferenceDto>> getPreferencesByUserId(@PathVariable(name = "userId") Long userId) {
        List<UserPreferenceDto> preferences = userPreferenceService.getPreferencesByUserId(userId);
        return ResponseEntity.ok(preferences);
    }

    @Override
    @GetMapping(path = "/user/{userId}/paginated")
    public ResponseEntity<Page<UserPreferenceDto>> getPreferencesByUserIdWithPagination(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        Page<UserPreferenceDto> preferences = userPreferenceService.getPreferencesByUserId(userId, pageable);
        return ResponseEntity.ok(preferences);
    }

    @Override
    @GetMapping(path = "/user/{userId}/type/{type}")
    public ResponseEntity<List<UserPreferenceDto>> getPreferencesByUserIdAndType(@PathVariable(name = "userId") Long userId, @PathVariable(name = "type") PreferenceType type) {
        List<UserPreferenceDto> preferences = userPreferenceService.getPreferencesByUserIdAndType(userId, type);
        return ResponseEntity.ok(preferences);
    }

    @Override
    @GetMapping(path = "/user/{userId}/key/{preferenceKey}")
    public ResponseEntity<UserPreferenceDto> getPreferenceByUserIdAndKey(@PathVariable(name = "userId") Long userId, @PathVariable(name = "preferenceKey") String preferenceKey) {
        Optional<UserPreferenceDto> preference = userPreferenceService.getPreferenceByUserIdAndKey(userId, preferenceKey);
        return preference.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping(path = "/user/{userId}/key/{preferenceKey}/type/{type}")
    public ResponseEntity<UserPreferenceDto> getPreferenceByUserIdAndKeyAndType(@PathVariable(name = "userId") Long userId, @PathVariable(name = "preferenceKey") String preferenceKey, @PathVariable(name = "type") PreferenceType type) {
        Optional<UserPreferenceDto> preference = userPreferenceService.getPreferenceByUserIdAndKeyAndType(userId, preferenceKey, type);
        return preference.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @PutMapping(path = "/user/{userId}/save-or-update")
    public ResponseEntity<UserPreferenceDto> saveOrUpdatePreference(@PathVariable(name = "userId") Long userId, @RequestParam String preferenceKey, @RequestParam String preferenceValue, @RequestParam PreferenceType type) {
        UserPreferenceDto preference = userPreferenceService.saveOrUpdatePreference(userId, preferenceKey, preferenceValue, type);
        return ResponseEntity.ok(preference);
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePreference(@PathVariable(name = "id") Long id) {
        boolean deleted = userPreferenceService.deletePreference(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @DeleteMapping(path = "/user/{userId}/key/{preferenceKey}")
    public ResponseEntity<Void> deletePreferenceByUserIdAndKey(@PathVariable(name = "userId") Long userId, @PathVariable(name = "preferenceKey") String preferenceKey) {
        boolean deleted = userPreferenceService.deletePreferenceByUserIdAndKey(userId, preferenceKey);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @DeleteMapping(path = "/user/{userId}/all")
    public ResponseEntity<Void> deleteAllPreferencesByUserId(@PathVariable(name = "userId") Long userId) {
        userPreferenceService.deleteAllPreferencesByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping(path = "/user/{userId}/key/{preferenceKey}/exists")
    public ResponseEntity<Boolean> existsPreferenceByUserIdAndKey(@PathVariable(name = "userId") Long userId, @PathVariable(name = "preferenceKey") String preferenceKey) {
        boolean exists = userPreferenceService.existsPreferenceByUserIdAndKey(userId, preferenceKey);
        return ResponseEntity.ok(exists);
    }

    @Override
    @GetMapping(path = "/user/{userId}/count")
    public ResponseEntity<Long> countPreferencesByUserId(@PathVariable(name = "userId") Long userId) {
        long count = userPreferenceService.countPreferencesByUserId(userId);
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping(path = "/all")
    public ResponseEntity<List<UserPreferenceDto>> getAllPreferences() {
        List<UserPreferenceDto> preferences = userPreferenceService.getAllPreferences();
        return ResponseEntity.ok(preferences);
    }

    @Override
    @GetMapping(path = "/all/paginated")
    public ResponseEntity<Page<UserPreferenceDto>> getAllPreferencesWithPagination(Pageable pageable) {
        Page<UserPreferenceDto> preferences = userPreferenceService.getAllPreferences(pageable);
        return ResponseEntity.ok(preferences);
    }
}
