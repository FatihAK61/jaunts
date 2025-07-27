package com.travelapp.repository.configurations;

import com.travelapp.models.configurations.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemSettingRepository extends JpaRepository<SystemSetting, Long>, JpaSpecificationExecutor<SystemSetting> {

    Optional<SystemSetting> findBySettingKey(String settingKey);

    List<SystemSetting> findByIsPublicTrue();

    List<SystemSetting> findByIsEditableTrue();

    @Query("SELECT s FROM SystemSetting s WHERE s.settingKey LIKE %:keyword% OR s.description LIKE %:keyword%")
    List<SystemSetting> findByKeywordInKeyOrDescription(@Param("keyword") String keyword);

    boolean existsBySettingKey(String settingKey);

    @Query("SELECT s FROM SystemSetting s WHERE s.isPublic = true AND s.isEditable = true")
    List<SystemSetting> findPublicAndEditableSettings();
}
