package com.azat.myretro.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azat.myretro.model.Board;
import com.azat.myretro.model.BoardColumn;
import com.azat.myretro.model.PermissionRole;

@Repository
@Transactional
public interface BoardColumnRepository  extends JpaRepository<BoardColumn, UUID>  {
	@Query(value = "SELECT * FROM board_column WHERE board_id=?1", nativeQuery = true)
	List<BoardColumn> findByBoardId(@Param("board_id") UUID boardId);
}
