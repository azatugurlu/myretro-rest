package com.azat.myretro.validator;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.azat.myretro.enums.Error;
import com.azat.myretro.enums.MessageType;
import com.azat.myretro.model.Permission;
import com.azat.myretro.model.Response;

@Component
public class PermissionValidator {

	public void validateInsert(Permission permission, Response response) {

		if (StringUtils.isEmpty(permission.getName())) {
			response.message(PermissionValidator.class.getName(), Error.ERR0008.name(), Error.ERR0008.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}

		if (StringUtils.isEmpty(permission.getTitle())) {
			response.message(PermissionValidator.class.getName(), Error.ERR0009.name(), Error.ERR0009.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateUpdate(Permission permission, Response response) {
		if (StringUtils.isEmpty(permission.getName())) {
			response.message(PermissionValidator.class.getName(), Error.ERR0008.name(), Error.ERR0008.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}

		if (StringUtils.isEmpty(permission.getTitle())) {
			response.message(PermissionValidator.class.getName(), Error.ERR0009.name(), Error.ERR0009.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateDelete(UUID id, Response response) {
		if (StringUtils.isEmpty(id.toString())) {
			response.message(PermissionValidator.class.getName(), Error.ERR0010.name(), Error.ERR0010.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
