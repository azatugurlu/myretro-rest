package com.azat.myretro.repository;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.azat.myretro.model.VoteUser;


@Repository
@Transactional
public interface VoteUserRepository extends JpaRepository<VoteUser, UUID> {
	@Query(value = "SELECT * FROM vote_user WHERE board_row_id=?1", nativeQuery = true)
	VoteUser findByBoardRowId(@Param("board_row_id") UUID boardRowId);

	@Query(value = "DELETE FROM vote_user WHERE board_row_id=?1 and user_id=?2", nativeQuery = true)
	Long deleteByRoleIdAndPermissionId(@Param("board_row_id") UUID boardRowId, @Param("user_id") UUID userId);

}
