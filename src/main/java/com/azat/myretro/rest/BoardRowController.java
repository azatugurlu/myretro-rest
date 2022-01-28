package com.azat.myretro.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.azat.myretro.model.Board;
import com.azat.myretro.model.BoardRow;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.VoteUser;
import com.azat.myretro.service.BoardRowService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/boards/rows")
public class BoardRowController {
	@Autowired
	private BoardRowService boardRowService;

	@PreAuthorize("hasAnyAuthority('can_read_board')")
	@ApiOperation(value = "Create the board row", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Board Row created, success") })
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	Response<BoardRow> create(@RequestBody BoardRow inputBoardRow) {
		return boardRowService.create(inputBoardRow);
	}
	
	@PreAuthorize("hasAnyAuthority('can_read_board')")
	@ApiOperation(value = "Update given board row", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Board row updated, success"),
			@ApiResponse(code = 404, message = "Board row not found, failed") })
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	Response<BoardRow> update(@RequestBody BoardRow inputBoardRow) {
		return boardRowService.update(inputBoardRow);
	}
	
	@PreAuthorize("hasAnyAuthority('can_read_board')")
	@ApiOperation(value = "Remove the board row by given id", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Board row removed, success"),
			@ApiResponse(code = 404, message = "Board row not found, failed") })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	Response<BoardRow> delete(@PathVariable("id") UUID id) {
		return boardRowService.delete(id);
	}
	
	@PreAuthorize("hasAnyAuthority('can_read_board')")
	@ApiOperation(value = "Create vote", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "vote created, success") })
	@RequestMapping(value = "/votes", method = RequestMethod.POST)
	@ResponseBody
	Response<VoteUser> vote(@RequestBody VoteUser voteUser) {
		return boardRowService.vote(voteUser);
	}
	
	@PreAuthorize("hasAnyAuthority('can_read_board')")
	@ApiOperation(value = "Remove the vote by given id", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Vote removed, success"),
			@ApiResponse(code = 404, message = "Vote not found, failed") })
	@RequestMapping(value = "/votes/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	Response<VoteUser> unVote(@PathVariable("id") UUID id) {
		return boardRowService.unVote(id);
	}
}
