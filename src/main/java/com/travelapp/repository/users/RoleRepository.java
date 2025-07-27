package com.travelapp.repository.users;

import com.travelapp.helper.enums.RoleName;
import com.travelapp.models.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    @Query("SELECT r FROM Role r WHERE r.id = :id")
    Optional<Role> findRoleById(Long id);

    Optional<Role> findByName(RoleName name);

    @Query("SELECT r FROM Role r WHERE r.description LIKE %:keyword%")
    List<Role> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT r FROM Role r WHERE r.name IN :names")
    List<Role> findByNameIn(@Param("names") List<RoleName> names);

    @Query("SELECT r FROM Role r JOIN r.rolePermissions rp WHERE rp.permission.id = :permissionId")
    List<Role> findByPermissionId(@Param("permissionId") Long permissionId);

    @Query("SELECT r FROM Role r JOIN r.rolePermissions rp WHERE rp.permission.name = :permissionName")
    List<Role> findByPermissionName(@Param("permissionName") String permissionName);

    @Query("SELECT r FROM Role r JOIN r.userRoles ur WHERE ur.user.id = :userId AND ur.active = true")
    List<Role> findActiveRolesByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(ur) FROM UserRole ur WHERE ur.role.id = :roleId AND ur.active = true")
    long countActiveUsersByRoleId(@Param("roleId") Long roleId);

    boolean existsByName(RoleName name);
}
