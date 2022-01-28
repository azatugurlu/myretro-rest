package com.azat.myretro.repository;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azat.myretro.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, UUID>{
	
	@Query(value= "SELECT * FROM user WHERE username=?1", nativeQuery = true)
	User findByUsername(@Param("username") String username);
	
	@Query(value= "SELECT * FROM user WHERE username=?1 or phone=?1", nativeQuery = true)
	User findByUsernameOrPhone(@Param("username") String username);
	
	@Query(value= "SELECT * FROM user WHERE phone=?1", nativeQuery = true)
	User findByPhone(@Param("phone") String phone);
	
	@Query(value = "SELECT * FROM user WHERE ( username like %:filter% or firstname like %:filter% or lastname like %:filter% or phone like %:filter%)", nativeQuery = true)
	Page<User> getUsers(@Param("filter") String filter, Pageable pageReq);
	
	@Query(value = "SELECT * FROM user usr, role_user ru WHERE ( usr.username like %:filter% or usr.firstname like %:filter% or usr.lastname "
			+ "like %:filter% or usr.phone like %:filter%) and ru.user_id =usr.id and ru.role_id=:role_id", nativeQuery = true)
	Page<User> getWaitingUsers(@Param("role_id") UUID role_id, @Param("filter") String filter, Pageable pageReq);
	
}
