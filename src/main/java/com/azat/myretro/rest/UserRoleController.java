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

import com.azat.myretro.model.Response;
import com.azat.myretro.model.RoleUser;
import com.azat.myretro.service.UserRoleService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;

	@PreAuthorize("hasAnyAuthority('can_assign_role_to_user')")
	@ApiOperation(value = "Create the User Role", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Role created, success") })
	@RequestMapping(value = "/{userId}/roles", method = RequestMethod.POST)
	@ResponseBody
	Response<RoleUser> create(@PathVariable("userId") String userId, @RequestBody RoleUser inputUserRole) {
		return userRoleService.create(inputUserRole);
	}

	@PreAuthorize("hasAnyAuthority('can_assign_role_to_user')")
	@ApiOperation(value = "Remove the role user by given id", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "User role removed, success"),
			@ApiResponse(code = 404, message = "User role not found, failed") })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	Response<RoleUser> delete(@PathVariable("id") UUID id) {
		return userRoleService.delete(id);
	}

	@PreAuthorize("hasAnyAuthority('can_assign_role_to_user')")
	@ApiOperation(value = "Remove the userrole by given id", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "RoleUser removed, success"),
			@ApiResponse(code = 404, message = "RoleUser not found, failed") })
	@RequestMapping(value = "/{userId}/roles/{roleId}", method = RequestMethod.DELETE)
	@ResponseBody
	Response<RoleUser> delete(@PathVariable("roleId") UUID roleId, @PathVariable("userId") UUID userId) {
		return userRoleService.deleteRoleUser(roleId, userId);
	}
}
