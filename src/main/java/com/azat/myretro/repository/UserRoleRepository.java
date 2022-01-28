package com.azat.myretro.repository;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azat.myretro.model.RoleUser;

@Repository
@Transactional
public interface UserRoleRepository extends JpaRepository<RoleUser, UUID> {

	@Query(value = "SELECT * FROM role_user WHERE user_id=?1", nativeQuery = true)
	RoleUser findByRoleId(@Param("user_id") UUID userId);

	@Query(value = "SELECT * FROM role_user WHERE role_id=?1 and user_id=?2", nativeQuery = true)
	RoleUser findByRoleIdAndUserId(@Param("role_id") UUID roleId, @Param("user_id") UUID userId);

	@Query(value = "DELETE FROM role_user WHERE role_id=?1 and user_id=?2", nativeQuery = true)
	Long deleteByRoleIdAndUserId(@Param("role_id") UUID roleId, @Param("user_id") UUID userId);
}
