package com.azat.myretro.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azat.myretro.model.Permission;

@Repository
@Transactional
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
	
	@Query(value = "SELECT * FROM permission WHERE id=?1", nativeQuery = true)
	Permission getOne(@Param("id") UUID id);
	
	@Query(value = "SELECT * FROM permission WHERE name=?1", nativeQuery = true)
	Permission getPermissionByName(String name);
	
	@Query(value = "SELECT * FROM permission ORDER BY id LIMIT 100", nativeQuery = true)
	List<Permission> getPermissions();
	
	@Query(value = "SELECT * FROM permission WHERE id IN (SELECT PERMISSION_ID from permission_role where ROLE_ID=?1)", nativeQuery = true)
	List<Permission> getRolePermissions(UUID roleId);
}
