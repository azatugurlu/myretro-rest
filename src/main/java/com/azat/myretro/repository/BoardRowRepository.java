package com.azat.myretro.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azat.myretro.model.Board;
import com.azat.myretro.model.BoardRow;

@Repository
@Transactional
public interface BoardRowRepository  extends JpaRepository<BoardRow, UUID> {
	@Query(value = "SELECT * FROM board_row WHERE columnId=?1", nativeQuery = true)
	List<BoardRow> findByColumnId(@Param("columnId") UUID columnId);
}
