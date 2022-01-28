package com.azat.myretro.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.azat.myretro.enums.Error;
import com.azat.myretro.enums.MessageType;
import com.azat.myretro.model.BoardRow;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.VoteUser;
import com.azat.myretro.repository.BoardRowRepository;
import com.azat.myretro.repository.VoteUserRepository;
import com.azat.myretro.service.BoardRowService;
import com.azat.myretro.validator.BoardRowValidator;


@Service
public class BoardRowServiceImpl implements BoardRowService {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardRowServiceImpl.class);
	
	@Autowired
	private BoardRowRepository boardRowRepository;
	
	@Autowired
	private VoteUserRepository voteUserRepository;
	
	@Autowired
	private BoardRowValidator boardRowValidator;

	@Override
	public Response<BoardRow> create(BoardRow boardRow) {
		Response<BoardRow> response = Response.create(BoardRow.class);
		boardRowValidator.validateInsert(boardRow, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}
		
		try {
			BoardRow newItem = boardRowRepository.save(boardRow);
			return Response.create(BoardRow.class).item(newItem).code(HttpStatus.OK);
		} catch (DuplicateKeyException e) {
			response.message(BoardRowServiceImpl.class.getName(), Error.ERR0061.name(), Error.ERR0061.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<BoardRow> update(BoardRow boardRow) {
		Response<BoardRow> response = Response.create(BoardRow.class);
		boardRowValidator.validateInsert(boardRow, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}
		
		try {
			BoardRow exist = boardRowRepository.getOne(boardRow.getId());
			if (exist == null) {
				response.message(BoardRowServiceImpl.class.getName(), Error.ERR0062.name(), Error.ERR0062.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.NOT_FOUND);
				logger.error(response.printMessages());
				return response;
			} else {
				exist.setDescription(boardRow.getDescription());
				BoardRow newItem = boardRowRepository.save(exist);
				return Response.create(BoardRow.class).item(newItem).code(HttpStatus.OK);
			}
		}catch (DuplicateKeyException e) {
			response.message(BoardRowServiceImpl.class.getName(), Error.ERR0061.name(), Error.ERR0061.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}catch (DataIntegrityViolationException e) {
			response.message(BoardRowServiceImpl.class.getName(), Error.ERR0061.name(), Error.ERR0061.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<BoardRow> delete(UUID rowId) {
		Response<BoardRow> response = Response.create(BoardRow.class);
		boardRowValidator.validateDelete(rowId, response);

		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}

		try {
			boardRowRepository.deleteById(rowId);
			logger.info(BoardServiceImpl.class.getName() + "Row deleted by id: " + rowId);
			return Response.create(BoardRow.class).code(HttpStatus.NO_CONTENT);
		}catch(Exception e){
			response.code(HttpStatus.NOT_FOUND);
			logger.error("Row with id: " + rowId + " not found.");
			return response;
		}
	}

	@Override
	public Response<VoteUser> vote(VoteUser voteUser) {
		Response<VoteUser> response = Response.create(VoteUser.class);
		boardRowValidator.validateInsertRowVote(voteUser, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}
		
		try {
			VoteUser newItem = voteUserRepository.save(voteUser);
			return Response.create(VoteUser.class).item(newItem).code(HttpStatus.OK);
		} catch (DuplicateKeyException e) {
			response.message(BoardRowServiceImpl.class.getName(), Error.ERR0061.name(), Error.ERR0061.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<VoteUser> unVote(UUID voteId) {
		Response<VoteUser> response = Response.create(VoteUser.class);
		boardRowValidator.validateDelete(voteId, response);

		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}

		try {
			voteUserRepository.deleteById(voteId);
			logger.info(BoardServiceImpl.class.getName() + "vote deleted by id: " + voteId);
			return Response.create(VoteUser.class).code(HttpStatus.NO_CONTENT);
		}catch(Exception e){
			response.code(HttpStatus.NOT_FOUND);
			logger.error("Vote with id: " + voteId + " not found.");
			return response;
		}
	}

}
