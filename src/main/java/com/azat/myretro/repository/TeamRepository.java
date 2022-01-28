package com.azat.myretro.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azat.myretro.model.Team;

@Repository
@Transactional
public interface TeamRepository extends JpaRepository<Team, UUID> {

	@Query(value = "SELECT * FROM team WHERE name=?1", nativeQuery = true)
	Team findByTeamName(@Param("name") String name);
	
	@Query(value = "SELECT * FROM team ORDER BY id LIMIT 100", nativeQuery = true)
	List<Team> getTeams();
}
