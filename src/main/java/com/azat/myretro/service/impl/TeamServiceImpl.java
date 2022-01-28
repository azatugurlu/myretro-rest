package com.azat.myretro.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.azat.myretro.enums.Error;
import com.azat.myretro.enums.MessageType;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.Team;
import com.azat.myretro.model.TeamUser;
import com.azat.myretro.repository.TeamRepository;
import com.azat.myretro.repository.TeamUserRepository;
import com.azat.myretro.service.TeamService;
import com.azat.myretro.validator.TeamValidator;

@Service
public class TeamServiceImpl implements TeamService {
	
	private static final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);

	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private TeamUserRepository teamUserRepository;

	@Autowired
	TeamValidator teamValidator;
	
	
	@Override
	public Response<Team> create(Team team) {
		Response<Team> response = Response.create(Team.class);
		
		teamValidator.validateInsert(team, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}
		
		try {
			Team exist = teamRepository.findByTeamName(team.getName());
			if (exist == null) {
				Team newItem = teamRepository.save(team);
				return Response.create(Team.class).item(newItem).code(HttpStatus.OK);
			} else {
				response.message(TeamServiceImpl.class.getName(), Error.ERR0046.name(), Error.ERR0046.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.CONFLICT);
				logger.error(response.printMessages());
				return response;
			}
		}catch (DuplicateKeyException e) {
			response.message(TeamServiceImpl.class.getName(), Error.ERR0046.name(), Error.ERR0046.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}
	}
	
	@Override
	public Response<Team> update(Team team) {
		Response<Team> response = Response.create(Team.class);
		teamValidator.validateUpdate(team, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}
		
		try {
			Team exist = teamRepository.getOne(team.getId());
			if (exist == null) {
				response.message(TeamServiceImpl.class.getName(), Error.ERR0045.name(), Error.ERR0045.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.NOT_FOUND);
				logger.error(response.printMessages());
				return response;
			} else {
				exist.setName(team.getName());
				exist.setDescription(team.getDescription());
				
				Team newItem = teamRepository.save(exist);
				return Response.create(Team.class).item(newItem).code(HttpStatus.OK);
			}
		}catch (DuplicateKeyException e) {
			response.message(TeamServiceImpl.class.getName(), Error.ERR0047.name(), Error.ERR0047.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}catch (DataIntegrityViolationException e) {
			response.message(TeamServiceImpl.class.getName(), Error.ERR0047.name(), Error.ERR0047.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}
	}
	
	@Override
	public Response<Team> delete(UUID teamId) {
		Response<Team> response = Response.create(Team.class);
		teamValidator.validateDelete(teamId, response);

		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}

		try {
			teamRepository.deleteById(teamId);
			logger.info(TeamServiceImpl.class.getName() + "Team deleted by id: " + teamId);
			return Response.create(Team.class).code(HttpStatus.NO_CONTENT);
		}catch(Exception e){
			response.code(HttpStatus.NOT_FOUND);
			logger.error("Team with id: " + teamId + " not found.");
			return response;
		}
	}

	@Override
	public Response<Team> list(String filter) {
		
		Response<Team> response = Response.create(Team.class);
		
		if (StringUtils.isEmpty(filter)) {
			filter = "%";
		}
		// TODO add filter to search query
		response.items(teamRepository.getTeams()).code(HttpStatus.OK);
		logger.info(response.toString());
		return response;
	}
	
	@Override
	public Response<Team> findByTeamName(String teamName) {
		Response<Team> response = Response.create(Team.class);
		teamValidator.validateTeamName(teamName, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}

		try {
			Team team = teamRepository.findByTeamName(teamName);
			if (team == null) {
				response.message(TeamServiceImpl.class.getName(), Error.ERR0045.name(), Error.ERR0045.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.NOT_FOUND);
				logger.error(response.printMessages());
				return response;
			} else {
				return Response.create(Team.class).item(team).code(HttpStatus.OK);
			}
		} catch (EmptyResultDataAccessException e) {
			response.message(TeamServiceImpl.class.getName(), Error.ERR0045.name(), Error.ERR0045.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<Team> getByTeamId(UUID teamId) {
		Response<Team> response = Response.create(Team.class);
		Optional<Team> findById = teamRepository.findById(teamId);
		Team team = findById.get();
		response.addItem(team);
		logger.info(response.toString());
		return response;
	}


	@Override
	public Response<TeamUser> createTeamUser(TeamUser teamUser) {
		Response<TeamUser> response = Response.create(TeamUser.class);	
		teamValidator.validateInsertTeamUser(teamUser, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}
		
		try {
			TeamUser exist = teamUserRepository.findByTeamIdAndUserId(teamUser.getTeam_id(), teamUser.getUser_id());
			if (exist == null) {
				TeamUser newItem = teamUserRepository.save(teamUser);
				response.item(newItem).code(HttpStatus.OK);
				logger.info(response.toString());
				return response;
			} else {
				response.message(TeamServiceImpl.class.getName(), Error.ERR0048.name(), Error.ERR0048.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.CONFLICT);
				logger.error(response.printMessages());
				return response;
			}
		}catch (DuplicateKeyException e) {
			response.message(RoleServiceImpl.class.getName(), Error.ERR0048.name(), Error.ERR0048.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<TeamUser> deleteTeamUser(UUID teamId, UUID userId) {
		Response<TeamUser> response = Response.create(TeamUser.class);
		teamValidator.validateDeleteTeamUser(teamId, userId, response);

		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}

		try {
			TeamUser teamUserItem =  teamUserRepository.findByTeamIdAndUserId(teamId,userId);
			teamUserRepository.delete(teamUserItem);
			response.code(HttpStatus.NO_CONTENT);
			logger.info(TeamServiceImpl.class.getName() + " deleteTeamUser: " + teamUserItem.toString());
			return response;
		}catch(Exception e){
			response.code(HttpStatus.BAD_REQUEST);
			logger.error(TeamServiceImpl.class.getName() + "Bad request. Could not delete... ");
			return response;
		}
	}

	@Override
	public Response<TeamUser> getTeamUserByTeamId(UUID teamId) {
		Response<TeamUser> response = Response.create(TeamUser.class);
		response.items(teamUserRepository.findAll()).code(HttpStatus.OK);
		logger.info(response.toString());
		return response;
	}
}
