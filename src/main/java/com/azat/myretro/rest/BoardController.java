package com.azat.myretro.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.azat.myretro.model.Board;
import com.azat.myretro.model.Response;
import com.azat.myretro.service.BoardService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/boards")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@PreAuthorize("hasAnyAuthority('can_read_board')")
	@ApiOperation(value = "Create the board", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Board created, success") })
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	Response<Board> create(@RequestBody Board inputBoard) {
		return boardService.create(inputBoard);
	}

	@PreAuthorize("hasAnyAuthority('can_read_board')")
	@ApiOperation(value = "Update given board", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Board updated, success"),
			@ApiResponse(code = 404, message = "Board not found, failed") })
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	Response<Board> update(@RequestBody Board inputBoard) {
		return boardService.update(inputBoard);
	}

	@PreAuthorize("hasAnyAuthority('can_read_board')")
	@ApiOperation(value = "Remove the board by given id", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Board removed, success"),
			@ApiResponse(code = 404, message = "Board not found, failed") })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	Response<Board> delete(@PathVariable("id") UUID id) {
		return boardService.delete(id);
	}

	@PreAuthorize("hasAnyAuthority('can_read_board')")
	@ApiOperation(value = "Get boards", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Board found, success"),
			@ApiResponse(code = 404, message = "Board not found, failed") })
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Response<Board> list(@RequestParam(value = "filter", required = false) String filter) {
		return boardService.list(filter);
	}

	@PreAuthorize("hasAuthority('can_read_board')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response<Board> getBoardById(@PathVariable("id") UUID id) {
		return boardService.getBoardById(id);
	}

}
