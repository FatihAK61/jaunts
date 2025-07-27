package com.travelapp.services.configurations;

import com.travelapp.dto.configurations.SystemSettingDto;
import com.travelapp.helper.enums.SettingType;

import java.util.List;
import java.util.Optional;

public interface ISystemSettingService {

    SystemSettingDto createSetting(SystemSettingDto systemSettingDto);

    SystemSettingDto updateSetting(Long id, SystemSettingDto systemSettingDto);

    SystemSettingDto updateSettingByKey(String settingKey, SystemSettingDto systemSettingDto);

    Optional<SystemSettingDto> getSettingById(Long id);

    Optional<SystemSettingDto> getSettingByKey(String settingKey);

    String getSettingValueByKey(String settingKey);

    <T> T getSettingValueByKey(String settingKey, Class<T> targetType);

    List<SystemSettingDto> getAllSettings();

    List<SystemSettingDto> getPublicSettings();

    List<SystemSettingDto> getEditableSettings();

    List<SystemSettingDto> getPublicAndEditableSettings();

    List<SystemSettingDto> searchSettings(String keyword);

    void deleteSetting(Long id);

    void deleteSettingByKey(String settingKey);

    boolean existsByKey(String settingKey);

    void updateSettingValue(String settingKey, String value);

    void updateSettingValue(String settingKey, Object value, SettingType type);
}
