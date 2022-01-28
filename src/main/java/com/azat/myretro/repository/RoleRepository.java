package com.azat.myretro.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azat.myretro.model.Role;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, UUID> {

	@Query(value = "SELECT * FROM role WHERE name=?1", nativeQuery = true)
	Role findByRoleName(@Param("name") String name);
	
	@Query(value = "SELECT * FROM role ORDER BY id LIMIT 100", nativeQuery = true)
	List<Role> getRoles();
}
