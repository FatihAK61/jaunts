package com.travelapp.controller.impl.configuration;

import com.travelapp.controller.configurations.ISystemSettingsController;
import com.travelapp.dto.configurations.SystemSettingDto;
import com.travelapp.helper.enums.SettingType;
import com.travelapp.helper.errorhandler.InvalidOperationException;
import com.travelapp.helper.errorhandler.ResourceNotFoundException;
import com.travelapp.services.configurations.ISystemSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/system-settings")
@RequiredArgsConstructor
public class SystemSettingsControllerImpl implements ISystemSettingsController {

    private final ISystemSettingService systemSettingService;

    @Override
    @PostMapping("/save")
    public ResponseEntity<SystemSettingDto> createSystemSetting(@RequestBody SystemSettingDto systemSettingDto) {
        SystemSettingDto createdSetting = systemSettingService.createSetting(systemSettingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSetting);
    }

    @Override
    @PutMapping("/{id}")
    public SystemSettingDto updateSystemSetting(@PathVariable Long id, @RequestBody SystemSettingDto systemSettingDto) {
        return systemSettingService.updateSetting(id, systemSettingDto);
    }

    @Override
    @PutMapping("/key/{settingKey}")
    public SystemSettingDto updateSystemSettingByKey(@PathVariable String settingKey, @RequestBody SystemSettingDto systemSettingDto) {
        return systemSettingService.updateSettingByKey(settingKey, systemSettingDto);
    }

    @Override
    @GetMapping
    public List<SystemSettingDto> getAllSystemSettings() {
        return systemSettingService.getAllSettings();
    }

    @Override
    @GetMapping("/pageable")
    public Page<SystemSettingDto> getAllSystemSettings(Pageable pageable) {
        List<SystemSettingDto> allSettings = systemSettingService.getAllSettings();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allSettings.size());
        List<SystemSettingDto> pageContent = allSettings.subList(start, end);

        return new PageImpl<>(pageContent, pageable, allSettings.size());
    }

    @Override
    @GetMapping("/{id}")
    public SystemSettingDto getSystemSettingById(@PathVariable Long id) {
        return systemSettingService.getSettingById(id)
                .orElseThrow(() -> new ResourceNotFoundException("System setting not found with id: ", id.toString()));
    }

    @Override
    @GetMapping("/key/{settingKey}")
    public SystemSettingDto getSystemSettingByKey(@PathVariable String settingKey) {
        return systemSettingService.getSettingByKey(settingKey)
                .orElseThrow(() -> new ResourceNotFoundException("System setting not found with key: ", settingKey));
    }

    @Override
    @GetMapping("/value/{settingKey}")
    public ResponseEntity<String> getSystemSettingValueByKey(@PathVariable String settingKey) {
        String value = systemSettingService.getSettingValueByKey(settingKey);
        if (value != null) {
            return ResponseEntity.ok(value);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @GetMapping("/value/{settingKey}/type/{targetType}")
    public ResponseEntity<Object> getSystemSettingValueByKeyWithType(@PathVariable String settingKey, @PathVariable String targetType) {
        try {
            Class<?> clazz = getClassForType(targetType);
            Object value = systemSettingService.getSettingValueByKey(settingKey, clazz);
            if (value != null) {
                return ResponseEntity.ok(value);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteSystemSetting(@PathVariable Long id) {
        systemSettingService.deleteSetting(id);
    }

    @Override
    @DeleteMapping("/key/{settingKey}")
    public void deleteSystemSettingByKey(@PathVariable String settingKey) {
        systemSettingService.deleteSettingByKey(settingKey);
    }

    @Override
    @GetMapping("/public")
    public List<SystemSettingDto> getPublicSystemSettings() {
        return systemSettingService.getPublicSettings();
    }

    @Override
    @GetMapping("/editable")
    public List<SystemSettingDto> getEditableSystemSettings() {
        return systemSettingService.getEditableSettings();
    }

    @Override
    @GetMapping("/public-editable")
    public List<SystemSettingDto> getPublicAndEditableSystemSettings() {
        return systemSettingService.getPublicAndEditableSettings();
    }

    @Override
    @GetMapping("/search")
    public List<SystemSettingDto> searchSystemSettings(@RequestParam String keyword) {
        return systemSettingService.searchSettings(keyword);
    }

    @Override
    @GetMapping("/exists/{settingKey}")
    public ResponseEntity<Boolean> existsByKey(@PathVariable String settingKey) {
        boolean exists = systemSettingService.existsByKey(settingKey);
        return ResponseEntity.ok(exists);
    }

    @Override
    @PutMapping("/value/{settingKey}")
    public ResponseEntity<Void> updateSettingValue(@PathVariable String settingKey, @RequestParam String value) {
        systemSettingService.updateSettingValue(settingKey, value);
        return ResponseEntity.ok().build();
    }

    @Override
    @PutMapping("/value/{settingKey}/typed")
    public ResponseEntity<Void> updateSettingValueWithType(@PathVariable String settingKey, @RequestParam Object value, @RequestParam SettingType type) {
        systemSettingService.updateSettingValue(settingKey, value, type);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/type/{type}")
    public List<SystemSettingDto> getSystemSettingsByType(@PathVariable SettingType type) {
        return systemSettingService.getAllSettings().stream()
                .filter(setting -> setting.getType() == type)
                .collect(Collectors.toList());
    }

    @Override
    @GetMapping("/public/{isPublic}")
    public List<SystemSettingDto> getSystemSettingsByIsPublic(@PathVariable Boolean isPublic) {
        if (isPublic) {
            return systemSettingService.getPublicSettings();
        } else {
            return systemSettingService.getAllSettings().stream()
                    .filter(setting -> !setting.getIsPublic())
                    .collect(Collectors.toList());
        }
    }

    @Override
    @GetMapping("/editable/{isEditable}")
    public List<SystemSettingDto> getSystemSettingsByIsEditable(@PathVariable Boolean isEditable) {
        if (isEditable) {
            return systemSettingService.getEditableSettings();
        } else {
            return systemSettingService.getAllSettings().stream()
                    .filter(setting -> !setting.getIsEditable())
                    .collect(Collectors.toList());
        }
    }

    @Override
    @PostMapping("/create-or-update/{settingKey}")
    public ResponseEntity<SystemSettingDto> createOrUpdateSetting(@PathVariable String settingKey, @RequestParam String settingValue, @RequestParam(required = false) String description,
                                                                  @RequestParam(defaultValue = "STRING") SettingType type, @RequestParam(defaultValue = "true") Boolean isEditable, @RequestParam(defaultValue = "false") Boolean isPublic) {
        SystemSettingDto settingDto = new SystemSettingDto();
        settingDto.setSettingKey(settingKey);
        settingDto.setSettingValue(settingValue);
        settingDto.setDescription(description);
        settingDto.setType(type);
        settingDto.setIsEditable(isEditable);
        settingDto.setIsPublic(isPublic);

        if (systemSettingService.existsByKey(settingKey)) {
            SystemSettingDto updatedSetting = systemSettingService.updateSettingByKey(settingKey, settingDto);
            return ResponseEntity.ok(updatedSetting);
        } else {
            SystemSettingDto createdSetting = systemSettingService.createSetting(settingDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSetting);
        }
    }

    @Override
    @PutMapping("/bulk-update")
    public ResponseEntity<List<SystemSettingDto>> bulkUpdateSettings(@RequestBody List<SystemSettingDto> systemSettingDtos) {
        List<SystemSettingDto> updatedSettings = systemSettingDtos.stream()
                .map(dto -> {
                    if (dto.getId() != null) {
                        return systemSettingService.updateSetting(dto.getId(), dto);
                    } else if (dto.getSettingKey() != null) {
                        return systemSettingService.updateSettingByKey(dto.getSettingKey(), dto);
                    } else {
                        throw new InvalidOperationException("Setting must have either id or settingKey for bulk update.");
                    }
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(updatedSettings);
    }

    @Override
    @PostMapping("/reset/{settingKey}")
    public ResponseEntity<Void> resetSettingToDefault(@PathVariable String settingKey) {
        // This would require a default value mechanism in your system
        // For now, we'll throw an exception indicating this feature needs implementation
        throw new RuntimeException("Reset to default functionality not implemented yet. Please implement it in your system.");
    }

    @Override
    @GetMapping("/value-as-type/{settingKey}")
    public ResponseEntity<Object> getSettingValueAsType(@PathVariable String settingKey, @RequestParam String className) {
        try {
            Class<?> targetClass = Class.forName(className);
            Object value = systemSettingService.getSettingValueByKey(settingKey, targetClass);
            if (value != null) {
                return ResponseEntity.ok(value);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ClassNotFoundException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private Class<?> getClassForType(String targetType) throws ClassNotFoundException {
        return switch (targetType.toLowerCase()) {
            case "string" -> String.class;
            case "integer", "int" -> Integer.class;
            case "long" -> Long.class;
            case "boolean", "bool" -> Boolean.class;
            case "double" -> Double.class;
            case "float" -> Float.class;
            default -> Class.forName(targetType);
        };
    }
}
