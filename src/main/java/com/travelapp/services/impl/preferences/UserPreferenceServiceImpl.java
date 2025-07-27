package com.travelapp.services.impl.preferences;

import com.travelapp.dto.preferences.UserPreferenceDto;
import com.travelapp.helper.enums.PreferenceType;
import com.travelapp.models.preferences.UserPreference;
import com.travelapp.models.users.UserBase;
import com.travelapp.repository.preferences.UserPreferenceRepository;
import com.travelapp.repository.users.UserBaseRepository;
import com.travelapp.services.impl.preferences.mappers.UserPreferenceMapperService;
import com.travelapp.services.preferences.IUserPreferenceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserPreferenceServiceImpl implements IUserPreferenceService {

    private final UserPreferenceRepository userPreferenceRepository;
    private final UserBaseRepository userBaseRepository;
    private final UserPreferenceMapperService mapperService;

    @Override
    public UserPreferenceDto createPreference(UserPreferenceDto userPreferenceDto) {
        UserBase user = userBaseRepository.findById(userPreferenceDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userPreferenceDto.getUserId()));

        UserPreference userPreference = mapperService.toEntity(userPreferenceDto);
        userPreference.setUser(user);

        UserPreference savedPreference = userPreferenceRepository.save(userPreference);

        return mapperService.toDto(savedPreference);
    }

    @Override
    public UserPreferenceDto updatePreference(Long id, UserPreferenceDto userPreferenceDto) {
        UserPreference existingPreference = userPreferenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User preference not found with ID: " + id));

        mapperService.updateEntityFromDto(userPreferenceDto, existingPreference);

        UserPreference updatedPreference = userPreferenceRepository.save(existingPreference);

        return mapperService.toDto(updatedPreference);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserPreferenceDto> getPreferenceById(Long id) {
        return userPreferenceRepository.findById(id)
                .map(mapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserPreferenceDto> getPreferencesByUserId(Long userId) {
        List<UserPreference> preferences = userPreferenceRepository.findByUserId(userId);
        return mapperService.toDtoList(preferences);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserPreferenceDto> getPreferencesByUserId(Long userId, Pageable pageable) {
        Page<UserPreference> preferencePage = userPreferenceRepository.findByUserId(userId, pageable);
        return preferencePage.map(mapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserPreferenceDto> getPreferencesByUserIdAndType(Long userId, PreferenceType type) {
        List<UserPreference> preferences = userPreferenceRepository.findByUserIdAndType(userId, type);
        return mapperService.toDtoList(preferences);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserPreferenceDto> getPreferenceByUserIdAndKey(Long userId, String preferenceKey) {
        return userPreferenceRepository.findByUserIdAndPreferenceKey(userId, preferenceKey)
                .map(mapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserPreferenceDto> getPreferenceByUserIdAndKeyAndType(Long userId, String preferenceKey, PreferenceType type) {
        return userPreferenceRepository.findByUserIdAndPreferenceKeyAndType(userId, preferenceKey, type)
                .map(mapperService::toDto);
    }

    @Override
    public UserPreferenceDto saveOrUpdatePreference(Long userId, String preferenceKey, String preferenceValue, PreferenceType type) {
        UserBase user = userBaseRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        Optional<UserPreference> existingPreference = userPreferenceRepository.findByUserIdAndPreferenceKey(userId, preferenceKey);

        UserPreference preference;
        if (existingPreference.isPresent()) {
            preference = existingPreference.get();
            preference.setPreferenceValue(preferenceValue);
            preference.setType(type);
        } else {
            preference = new UserPreference();
            preference.setUser(user);
            preference.setPreferenceKey(preferenceKey);
            preference.setPreferenceValue(preferenceValue);
            preference.setType(type);
        }

        UserPreference savedPreference = userPreferenceRepository.save(preference);
        return mapperService.toDto(savedPreference);
    }

    @Override
    public boolean deletePreference(Long id) {
        if (userPreferenceRepository.existsById(id)) {
            userPreferenceRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public boolean deletePreferenceByUserIdAndKey(Long userId, String preferenceKey) {
        if (userPreferenceRepository.existsByUserIdAndPreferenceKey(userId, preferenceKey)) {
            userPreferenceRepository.deleteByUserIdAndPreferenceKey(userId, preferenceKey);
            return true;
        }

        return false;
    }

    @Override
    public void deleteAllPreferencesByUserId(Long userId) {
        userPreferenceRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsPreferenceByUserIdAndKey(Long userId, String preferenceKey) {
        return userPreferenceRepository.existsByUserIdAndPreferenceKey(userId, preferenceKey);
    }

    @Override
    @Transactional(readOnly = true)
    public long countPreferencesByUserId(Long userId) {
        return userPreferenceRepository.countByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserPreferenceDto> getAllPreferences() {
        List<UserPreference> preferences = userPreferenceRepository.findAll();
        return mapperService.toDtoList(preferences);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserPreferenceDto> getAllPreferences(Pageable pageable) {
        Page<UserPreference> preferencePage = userPreferenceRepository.findAll(pageable);
        return preferencePage.map(mapperService::toDto);
    }
}
