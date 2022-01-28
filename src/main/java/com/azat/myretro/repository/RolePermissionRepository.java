package com.azat.myretro.repository;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azat.myretro.model.PermissionRole;

@Repository
@Transactional
public interface RolePermissionRepository extends JpaRepository<PermissionRole, UUID>{
	
	@Query(value = "SELECT * FROM permission_role WHERE role_id=?1", nativeQuery = true)
	PermissionRole findByRoleId(@Param("role_id") UUID roleId);
	
	@Query(value = "SELECT * FROM permission_role WHERE role_id=?1 and permission_id=?2", nativeQuery = true)
	PermissionRole findByRoleIdAndPermissionId(@Param("role_id") UUID roleId, @Param("permission_id") UUID permissionId);

	@Query(value = "DELETE FROM permission_role WHERE role_id=?1 and permission_id=?2", nativeQuery = true)
	Long deleteByRoleIdAndPermissionId(@Param("role_id") UUID roleId, @Param("permission_id") UUID permissionId);
}
