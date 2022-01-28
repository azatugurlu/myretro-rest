package com.azat.myretro.validator;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.azat.myretro.enums.Error;
import com.azat.myretro.enums.MessageType;
import com.azat.myretro.model.PermissionRole;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.Team;
import com.azat.myretro.model.TeamUser;


@Component
public class TeamValidator {

	public void validateTeamName(String teamName, Response response) {
		if (teamName == null) {
			response.message(TeamValidator.class.getName(), Error.ERR0011.name(), Error.ERR0011.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateInsert(Team team, Response response) {

		if (StringUtils.isEmpty(team.getName())) {
			response.message(TeamValidator.class.getName(), Error.ERR0011.name(), Error.ERR0011.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);

		}

		if (StringUtils.isEmpty(team.getDescription())) {
			response.message(TeamValidator.class.getName(), Error.ERR0012.name(), Error.ERR0012.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateUpdate(Team team, Response response) {
		if (StringUtils.isEmpty(team.getName())) {
			response.message(TeamValidator.class.getName(), Error.ERR0011.name(), Error.ERR0011.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);

		}

		if (StringUtils.isEmpty(team.getDescription())) {
			response.message(TeamValidator.class.getName(), Error.ERR0012.name(), Error.ERR0012.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateDelete(UUID id, Response response) {
		if (StringUtils.isEmpty(id.toString())) {
			response.message(TeamValidator.class.getName(), Error.ERR0013.name(), Error.ERR0013.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	public void validateDeleteTeamUser(UUID teamId, UUID userId, Response response) {
		if (StringUtils.isEmpty(teamId.toString())) {
			response.message(TeamValidator.class.getName(), Error.ERR0013.name(), Error.ERR0013.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}

		if (StringUtils.isEmpty(userId.toString())) {
			
			response.message(TeamValidator.class.getName(), Error.ERR0010.name(), Error.ERR0010.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateInsertTeamUser(TeamUser teamUser, Response response) {
		if (StringUtils.isEmpty(teamUser.getTeam_id())) {
			response.message(TeamValidator.class.getName(), Error.ERR0013.name(), Error.ERR0013.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}

		if (StringUtils.isEmpty(teamUser.getUser_id())) {
			response.message(TeamValidator.class.getName(), Error.ERR0010.name(), Error.ERR0010.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
