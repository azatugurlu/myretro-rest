package com.azat.myretro.validator;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.azat.myretro.enums.Error;
import com.azat.myretro.enums.MessageType;
import com.azat.myretro.model.PermissionRole;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.Role;

@Component
public class RoleValidator {

	public void validateRoleName(String roleName, Response response) {
		if (roleName == null) {
			response.message(RoleValidator.class.getName(), Error.ERR0011.name(), Error.ERR0011.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateInsert(Role role, Response response) {

		if (StringUtils.isEmpty(role.getName())) {
			response.message(RoleValidator.class.getName(), Error.ERR0011.name(), Error.ERR0011.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);

		}

		if (StringUtils.isEmpty(role.getTitle())) {
			response.message(RoleValidator.class.getName(), Error.ERR0012.name(), Error.ERR0012.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateUpdate(Role role, Response response) {
		if (StringUtils.isEmpty(role.getName())) {
			response.message(RoleValidator.class.getName(), Error.ERR0011.name(), Error.ERR0011.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);

		}

		if (StringUtils.isEmpty(role.getTitle())) {
			response.message(RoleValidator.class.getName(), Error.ERR0012.name(), Error.ERR0012.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateDelete(UUID id, Response response) {
		if (StringUtils.isEmpty(id.toString())) {
			response.message(RoleValidator.class.getName(), Error.ERR0013.name(), Error.ERR0013.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateDeleteRolePermission(UUID roleId, UUID permissionId, Response response) {
		if (StringUtils.isEmpty(roleId.toString())) {
			response.message(RoleValidator.class.getName(), Error.ERR0013.name(), Error.ERR0013.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}

		if (StringUtils.isEmpty(permissionId.toString())) {
			
			response.message(RoleValidator.class.getName(), Error.ERR0010.name(), Error.ERR0010.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateInsertRolePermission(PermissionRole rolePermission, Response response) {
		if (StringUtils.isEmpty(rolePermission.getRole_id())) {
			response.message(RoleValidator.class.getName(), Error.ERR0013.name(), Error.ERR0013.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}

		if (StringUtils.isEmpty(rolePermission.getPermission_id())) {
			response.message(RoleValidator.class.getName(), Error.ERR0010.name(), Error.ERR0010.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
