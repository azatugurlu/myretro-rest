package com.azat.myretro.service;

import java.util.UUID;

import com.azat.myretro.model.BoardRow;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.VoteUser;

public interface BoardRowService {
	public Response<BoardRow> create(BoardRow boardRow);
	public Response<BoardRow> update(BoardRow boardRow);
	public Response<BoardRow> delete(UUID boardId);
	public Response<VoteUser> vote(VoteUser voteUser);
	public Response<VoteUser> unVote(UUID voteId);
}
