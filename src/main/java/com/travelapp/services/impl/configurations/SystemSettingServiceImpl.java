package com.travelapp.services.impl.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelapp.dto.configurations.SystemSettingDto;
import com.travelapp.helper.enums.SettingType;
import com.travelapp.helper.errorhandler.InvalidOperationException;
import com.travelapp.helper.errorhandler.ResourceNotFoundException;
import com.travelapp.models.configurations.SystemSetting;
import com.travelapp.repository.configurations.SystemSettingRepository;
import com.travelapp.services.configurations.ISystemSettingService;
import com.travelapp.services.impl.configurations.mappers.SystemSettingMapperService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SystemSettingServiceImpl implements ISystemSettingService {

    private final SystemSettingRepository systemSettingRepository;
    private final SystemSettingMapperService systemSettingMapperService;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public SystemSettingDto createSetting(SystemSettingDto systemSettingDto) {
        if (systemSettingRepository.existsBySettingKey(systemSettingDto.getSettingKey())) {
            throw new EntityExistsException("Setting with key '" + systemSettingDto.getSettingKey() + "' already exists.");
        }

        SystemSetting systemSetting = systemSettingMapperService.toEntity(systemSettingDto);
        SystemSetting savedSetting = systemSettingRepository.save(systemSetting);

        return systemSettingMapperService.toDto(savedSetting);
    }

    @Override
    @Transactional
    public SystemSettingDto updateSetting(Long id, SystemSettingDto systemSettingDto) {
        SystemSetting existingSetting = systemSettingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("System setting not found with id: ", id.toString()));

        if (!existingSetting.getIsEditable()) {
            throw new InvalidOperationException("Setting with id '" + id + "' is not editable");
        }

        systemSettingMapperService.updateEntityFromDto(systemSettingDto, existingSetting);
        SystemSetting updatedSetting = systemSettingRepository.save(existingSetting);

        return systemSettingMapperService.toDto(updatedSetting);
    }

    @Override
    @Transactional
    public SystemSettingDto updateSettingByKey(String settingKey, SystemSettingDto systemSettingDto) {
        SystemSetting existingSetting = systemSettingRepository.findBySettingKey(settingKey)
                .orElseThrow(() -> new RuntimeException("System setting not found with key: " + settingKey));

        if (!existingSetting.getIsEditable()) {
            throw new RuntimeException("Setting with key '" + settingKey + "' is not editable");
        }

        systemSettingMapperService.updateEntityFromDto(systemSettingDto, existingSetting);
        SystemSetting updatedSetting = systemSettingRepository.save(existingSetting);

        return systemSettingMapperService.toDto(updatedSetting);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SystemSettingDto> getSettingById(Long id) {
        return systemSettingRepository.findById(id)
                .map(systemSettingMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SystemSettingDto> getSettingByKey(String settingKey) {
        return systemSettingRepository.findBySettingKey(settingKey)
                .map(systemSettingMapperService::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public String getSettingValueByKey(String settingKey) {
        return systemSettingRepository.findBySettingKey(settingKey)
                .map(SystemSetting::getSettingValue)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T getSettingValueByKey(String settingKey, Class<T> targetType) {
        SystemSetting setting = systemSettingRepository.findBySettingKey(settingKey)
                .orElse(null);

        if (setting == null) {
            return null;
        }

        String value = setting.getSettingValue();
        SettingType type = setting.getType();

        try {
            return convertValue(value, type, targetType);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SystemSettingDto> getAllSettings() {
        List<SystemSetting> settings = systemSettingRepository.findAll();
        return systemSettingMapperService.toDtoList(settings);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SystemSettingDto> getPublicSettings() {
        List<SystemSetting> settings = systemSettingRepository.findByIsPublicTrue();
        return systemSettingMapperService.toDtoList(settings);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SystemSettingDto> getEditableSettings() {
        List<SystemSetting> settings = systemSettingRepository.findByIsEditableTrue();
        return systemSettingMapperService.toDtoList(settings);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SystemSettingDto> getPublicAndEditableSettings() {
        List<SystemSetting> settings = systemSettingRepository.findPublicAndEditableSettings();
        return systemSettingMapperService.toDtoList(settings);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SystemSettingDto> searchSettings(String keyword) {
        List<SystemSetting> settings = systemSettingRepository.findByKeywordInKeyOrDescription(keyword);
        return systemSettingMapperService.toDtoList(settings);
    }

    @Override
    @Transactional
    public void deleteSetting(Long id) {
        SystemSetting setting = systemSettingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("System setting not found with id: ", id.toString()));

        if (!setting.getIsEditable()) {
            throw new InvalidOperationException("Setting with id '" + id + "' is not editable and cannot be deleted");
        }

        systemSettingRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteSettingByKey(String settingKey) {
        SystemSetting setting = systemSettingRepository.findBySettingKey(settingKey)
                .orElseThrow(() -> new ResourceNotFoundException("System setting not found with key: ", settingKey));

        if (!setting.getIsEditable()) {
            throw new InvalidOperationException("Setting with key '" + settingKey + "' is not editable and cannot be deleted");
        }

        systemSettingRepository.delete(setting);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByKey(String settingKey) {
        return systemSettingRepository.existsBySettingKey(settingKey);
    }

    @Override
    @Transactional
    public void updateSettingValue(String settingKey, String value) {
        SystemSetting setting = systemSettingRepository.findBySettingKey(settingKey)
                .orElseThrow(() -> new ResourceNotFoundException("System setting not found with key: ", settingKey));

        if (!setting.getIsEditable()) {
            throw new InvalidOperationException("Setting with key '" + settingKey + "' is not editable");
        }

        setting.setSettingValue(value);
        systemSettingRepository.save(setting);
    }

    @Override
    @Transactional
    public void updateSettingValue(String settingKey, Object value, SettingType type) {
        SystemSetting setting = systemSettingRepository.findBySettingKey(settingKey)
                .orElseThrow(() -> new ResourceNotFoundException("System setting not found with key: ", settingKey));

        if (!setting.getIsEditable()) {
            throw new InvalidOperationException("Setting with key '" + settingKey + "' is not editable");
        }

        String stringValue = convertObjectToString(value, type);
        setting.setSettingValue(stringValue);
        setting.setType(type);
        systemSettingRepository.save(setting);
    }

    @SuppressWarnings("unchecked")
    private <T> T convertValue(String value, SettingType type, Class<T> targetType) {
        if (value == null) {
            return null;
        }

        try {
            switch (type) {
                case INTEGER:
                    if (targetType == Integer.class || targetType == int.class) {
                        return (T) Integer.valueOf(value);
                    } else if (targetType == Long.class || targetType == long.class) {
                        return (T) Long.valueOf(value);
                    }
                    break;
                case BOOLEAN:
                    if (targetType == Boolean.class || targetType == boolean.class) {
                        return (T) Boolean.valueOf(value);
                    }
                    break;
                case DECIMAL:
                    if (targetType == Double.class || targetType == double.class) {
                        return (T) Double.valueOf(value);
                    } else if (targetType == Float.class || targetType == float.class) {
                        return (T) Float.valueOf(value);
                    }
                    break;
                case JSON:
                    return objectMapper.readValue(value, targetType);
                default:
                    return (T) value;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }

        return (T) value;
    }

    private String convertObjectToString(Object value, SettingType type) {
        if (value == null) {
            return null;
        }

        try {
            if (Objects.requireNonNull(type) == SettingType.JSON) {
                return objectMapper.writeValueAsString(value);
            }
            return value.toString();
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
