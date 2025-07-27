package com.travelapp.controller.configurations;

import com.travelapp.dto.configurations.SystemSettingDto;
import com.travelapp.helper.enums.SettingType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ISystemSettingsController {

    ResponseEntity<SystemSettingDto> createSystemSetting(@Valid @NotNull SystemSettingDto systemSettingDto);

    SystemSettingDto updateSystemSetting(Long id, SystemSettingDto systemSettingDto);

    SystemSettingDto updateSystemSettingByKey(String settingKey, SystemSettingDto systemSettingDto);

    List<SystemSettingDto> getAllSystemSettings();

    Page<SystemSettingDto> getAllSystemSettings(Pageable pageable);

    SystemSettingDto getSystemSettingById(Long id);

    SystemSettingDto getSystemSettingByKey(String settingKey);

    ResponseEntity<String> getSystemSettingValueByKey(String settingKey);

    ResponseEntity<Object> getSystemSettingValueByKeyWithType(String settingKey, String targetType);

    void deleteSystemSetting(Long id);

    void deleteSystemSettingByKey(String settingKey);

    List<SystemSettingDto> getPublicSystemSettings();

    List<SystemSettingDto> getEditableSystemSettings();

    List<SystemSettingDto> getPublicAndEditableSystemSettings();

    List<SystemSettingDto> searchSystemSettings(String keyword);

    ResponseEntity<Boolean> existsByKey(String settingKey);

    ResponseEntity<Void> updateSettingValue(String settingKey, String value);

    ResponseEntity<Void> updateSettingValueWithType(String settingKey, Object value, SettingType type);

    List<SystemSettingDto> getSystemSettingsByType(SettingType type);

    List<SystemSettingDto> getSystemSettingsByIsPublic(Boolean isPublic);

    List<SystemSettingDto> getSystemSettingsByIsEditable(Boolean isEditable);

    ResponseEntity<SystemSettingDto> createOrUpdateSetting(String settingKey, String settingValue, String description, SettingType type, Boolean isEditable, Boolean isPublic);

    ResponseEntity<List<SystemSettingDto>> bulkUpdateSettings(List<SystemSettingDto> systemSettingDtos);

    ResponseEntity<Void> resetSettingToDefault(String settingKey);

    ResponseEntity<Object> getSettingValueAsType(@PathVariable String settingKey, @RequestParam String className);
}
