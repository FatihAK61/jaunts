package com.travelapp.repository.users;

import com.travelapp.models.users.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    @Query("SELECT p FROM Permission p WHERE p.id = :id")
    Optional<Permission> findPermissionById(Long id);

    Optional<Permission> findByName(String name);

    List<Permission> findByResource(String resource);

    List<Permission> findByAction(String action);

    List<Permission> findByResourceAndAction(String resource, String action);

    boolean existsByName(String name);

    @Query("SELECT p FROM Permission p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Permission> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT p FROM Permission p WHERE p.resource IN :resources")
    List<Permission> findByResourceIn(@Param("resources") List<String> resources);

    @Query("SELECT p FROM Permission p WHERE p.action IN :actions")
    List<Permission> findByActionIn(@Param("actions") List<String> actions);

    @Query("SELECT DISTINCT p.resource FROM Permission p ORDER BY p.resource")
    List<String> findAllDistinctResources();

    @Query("SELECT DISTINCT p.action FROM Permission p ORDER BY p.action")
    List<String> findAllDistinctActions();
}
