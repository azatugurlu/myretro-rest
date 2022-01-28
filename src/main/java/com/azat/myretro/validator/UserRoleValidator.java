package com.azat.myretro.validator;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.azat.myretro.enums.Error;
import com.azat.myretro.enums.MessageType;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.RoleUser;

@Component
public class UserRoleValidator {

	public void validateInsert(RoleUser userRole, Response response) {

		if (StringUtils.isEmpty(userRole.getRole_id())) {
			response.message(UserRoleValidator.class.getName(), Error.ERR0013.name(), Error.ERR0013.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}

		if (StringUtils.isEmpty(userRole.getUser_id())) {
			response.message(UserRoleValidator.class.getName(), Error.ERR0007.name(), Error.ERR0007.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateUpdate(RoleUser userRole, Response response) {

		if (StringUtils.isEmpty(userRole.getRole_id())) {
			response.message(UserRoleValidator.class.getName(), Error.ERR0013.name(), Error.ERR0013.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}

		if (StringUtils.isEmpty(userRole.getUser_id())) {
			response.message(UserRoleValidator.class.getName(), Error.ERR0007.name(), Error.ERR0007.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateDelete(UUID id, Response response) {
		if (StringUtils.isEmpty(id.toString())) {
			response.message(UserRoleValidator.class.getName(), Error.ERR0013.name(), Error.ERR0013.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateDeleteRoleUser(UUID userId, UUID roleId, Response<RoleUser> response) {
		if (StringUtils.isEmpty(userId.toString())) {
			response.message(UserRoleValidator.class.getName(), Error.ERR0013.name(), Error.ERR0013.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
		if (StringUtils.isEmpty(roleId.toString())) {
			response.message(UserRoleValidator.class.getName(), Error.ERR0007.name(), Error.ERR0007.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}

	}

}
