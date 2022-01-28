package com.azat.myretro.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azat.myretro.model.TeamUser;

@Repository
@Transactional
public interface TeamUserRepository extends JpaRepository<TeamUser, UUID>{
	
	@Query(value = "SELECT * FROM team_user WHERE team_id=?1", nativeQuery = true)
	TeamUser findByTeamId(@Param("teamId_id") UUID teamId);
	
	@Query(value = "SELECT * FROM team_user WHERE user_id=?1", nativeQuery = true)
	List<TeamUser> findByUserId(@Param("user_id") UUID userId);
	
	@Query(value = "SELECT * FROM team_user WHERE team_id=?1 and user_id=?2", nativeQuery = true)
	TeamUser findByTeamIdAndUserId(@Param("team_id") UUID teamId, @Param("user_id") UUID userId);

	@Query(value = "DELETE FROM team_user WHERE team_id=?1 and user_id=?2", nativeQuery = true)
	Long deleteByTeamIdAndUserId(@Param("team_id") UUID teamId, @Param("user_id") UUID userId);
}
