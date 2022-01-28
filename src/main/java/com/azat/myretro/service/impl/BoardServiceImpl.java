package com.azat.myretro.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.azat.myretro.enums.BoardColors;
import com.azat.myretro.enums.Error;
import com.azat.myretro.enums.MessageType;
import com.azat.myretro.enums.RetroType;
import com.azat.myretro.model.Board;
import com.azat.myretro.model.BoardColumn;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.TeamUser;
import com.azat.myretro.model.User;
import com.azat.myretro.repository.BoardColumnRepository;
import com.azat.myretro.repository.BoardRepository;
import com.azat.myretro.repository.TeamRepository;
import com.azat.myretro.repository.TeamUserRepository;
import com.azat.myretro.repository.UserRepository;
import com.azat.myretro.service.BoardService;
import com.azat.myretro.validator.BoardValidator;

@Service
public class BoardServiceImpl implements BoardService {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private BoardColumnRepository boardColumnRepository;
	
	@Autowired
	private BoardValidator boardValidator;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TeamUserRepository teamUserRepository;

	@Override
	public Response<Board> create(Board board) {
		Response<Board> response = Response.create(Board.class);
		
		boardValidator.validateInsert(board, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}
		
		try {
			Board exist = boardRepository.findByBoardName(board.getName());
			if (exist == null) {
				Board newItem = boardRepository.save(board);
				
				Map<String, String> columns = new HashMap<String, String>();
				if (newItem.getRetroType().equals(RetroType.MAD_SAD_GLAD)) {
					columns.put("Mad", BoardColors.YELLOW.getValue());
					columns.put("Sad", BoardColors.RED.getValue());
					columns.put("Glad", BoardColors.GREEN.getValue());
				} else if (newItem.getRetroType().equals(RetroType.START_STOP_CONTINUE)) {
					columns.put("Start", BoardColors.GREEN.getValue());
					columns.put("Stop", BoardColors.RED.getValue());
					columns.put("Continue", BoardColors.GREY.getValue());
				} else if (newItem.getRetroType().equals(RetroType.WWW_WDGW)) {
					columns.put("What Went Well", BoardColors.GREEN.getValue());
					columns.put("What didn't go well", BoardColors.RED.getValue());
				}
				
				int index = 0;
				for (String key : columns.keySet()) {
					BoardColumn boardColumn = new BoardColumn();
					boardColumn.setBoard_id(newItem.getId());
					boardColumn.setName(key);
					boardColumn.setColor(columns.get(key));
					boardColumn.setColumnOrder(index);
					index++;
					boardColumnRepository.save(boardColumn);
				}
				
				return Response.create(Board.class).item(newItem).code(HttpStatus.OK);
			} else {
				response.message(BoardServiceImpl.class.getName(), Error.ERR0061.name(), Error.ERR0061.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.CONFLICT);
				logger.error(response.printMessages());
				return response;
			}
		}catch (DuplicateKeyException e) {
			response.message(BoardServiceImpl.class.getName(), Error.ERR0061.name(), Error.ERR0061.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<Board> update(Board board) {
		Response<Board> response = Response.create(Board.class);
		boardValidator.validateInsert(board, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}
		
		try {
			Board exist = boardRepository.getOne(board.getId());
			if (exist == null) {
				response.message(BoardServiceImpl.class.getName(), Error.ERR0062.name(), Error.ERR0062.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.NOT_FOUND);
				logger.error(response.printMessages());
				return response;
			} else {
				exist.setName(board.getName());
				exist.setDescription(board.getDescription());
				exist.setGoalAchieved(board.getGoalAchieved());
				exist.setRetroDate(board.getRetroDate());
				exist.setCountDownTimerStartDate(board.getCountDownTimerStartDate());
				exist.setCountDownTimerEndDate(board.getCountDownTimerEndDate());
				Board newItem = boardRepository.save(exist);
				return Response.create(Board.class).item(newItem).code(HttpStatus.OK);
			}
		}catch (DuplicateKeyException e) {
			response.message(BoardServiceImpl.class.getName(), Error.ERR0061.name(), Error.ERR0061.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}catch (DataIntegrityViolationException e) {
			response.message(BoardServiceImpl.class.getName(), Error.ERR0061.name(), Error.ERR0061.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<Board> delete(UUID boardId) {
		Response<Board> response = Response.create(Board.class);
		boardValidator.validateDelete(boardId, response);

		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}

		try {
			boardRepository.deleteById(boardId);
			//boardColumnRepository.deleteByBoardId(boardId);
			//boardRowRepository.deleteByBoardId(boardId);
			
			logger.info(BoardServiceImpl.class.getName() + "Board deleted by id: " + boardId);
			return Response.create(Board.class).code(HttpStatus.NO_CONTENT);
		}catch(Exception e){
			response.code(HttpStatus.NOT_FOUND);
			logger.error("Board with id: " + boardId + " not found.");
			return response;
		}
	}

	@Override
	public Response<Board> getBoardsByTeamId(UUID teamId) {
		Response<Board> response = Response.create(Board.class);
		response.items(boardRepository.findByTeamId(teamId)).code(HttpStatus.OK);
		logger.info(response.toString());
		return response;
	}
	
	@Override
	public Response<Board> getBoardById(UUID boardId) {
		Response<Board> response = Response.create(Board.class);
		Optional<Board> findById = boardRepository.findById(boardId);
		Board board = findById.get();
		
		response.item(board).code(HttpStatus.OK);
		logger.info(response.toString());
		return response;
	}

	@Override
	public Response<Board> list(String filter) {
		Response<Board> response = Response.create(Board.class);
		
		if (StringUtils.isEmpty(filter)) {
			filter = "%";
		}
		// TODO add filter to search query
		
		List<Board> boards =  boardRepository.findAll();
		
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userRepository.findByUsername(principal.toString());
		List<TeamUser> teamUsers = teamUserRepository.findByUserId(user.getId());
		
		List<Board> filteredBoards = new ArrayList<Board>();
		
		for (Board board: boards) {
			for (TeamUser teamUser: teamUsers) {
				if(board.getTeam_id().equals(teamUser.getTeam_id())) {
					filteredBoards.add(board);
				}
			}
		}
		
		
		response.items(filteredBoards).code(HttpStatus.OK);
		logger.info(response.toString());
		return response;
	}

}
