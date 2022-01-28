package com.azat.myretro.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azat.myretro.model.Board;

@Repository
@Transactional
public interface BoardRepository extends JpaRepository<Board, UUID> {

	@Query(value = "SELECT * FROM board WHERE name=?1", nativeQuery = true)
	Board findByBoardName(@Param("name") String name);
	
	@Query(value = "SELECT * FROM board WHERE teamId=?1", nativeQuery = true)
	List<Board> findByTeamId(@Param("teamId") UUID teamId);
}