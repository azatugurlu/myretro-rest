package com.azat.myretro.service;

import java.util.UUID;

import com.azat.myretro.model.Board;
import com.azat.myretro.model.Response;

public interface BoardService {
	public Response<Board> create(Board board);
	public Response<Board> update(Board board);
	public Response<Board> delete(UUID boardId);
	public Response<Board> getBoardsByTeamId(UUID teamId);
	public Response<Board> getBoardById(UUID boardId);
	Response<Board> list(String filter);

}
