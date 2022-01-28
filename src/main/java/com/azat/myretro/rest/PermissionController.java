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

import com.azat.myretro.model.Permission;
import com.azat.myretro.model.Response;
import com.azat.myretro.service.PermissionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/permissions")
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	@PreAuthorize("hasAuthority('can_manage_permission')")
	@ApiOperation(value = "Get permissions", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Permissions found, success"),
			@ApiResponse(code = 404, message = "Permissions not found, failed") })
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Response<Permission> list(@RequestParam(value = "filter", required = false) String filter) {
		return permissionService.list(filter);
	}
	
	@PreAuthorize("hasAnyAuthority('can_manage_permission')")
	@ApiOperation(value = "Create the permision", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Permission created, success")})
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	Response<Permission> create(@RequestBody Permission inputPermission) {
		return permissionService.create(inputPermission);
	}
	
	@PreAuthorize("hasAnyAuthority('can_manage_permission')")
	@ApiOperation(value = "Update given permission", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Permission updated, success"),
			@ApiResponse(code = 404, message = "Permission not found, failed") })
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	Response<Permission> update(@RequestBody Permission inputPermission) {
		return permissionService.update(inputPermission);
	}
	
	@PreAuthorize("hasAnyAuthority('can_manage_permission')")
	@ApiOperation(value = "Remove the permission by given id", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Permission removed, success"),
			@ApiResponse(code = 404, message = "Permission not found, failed") })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	Response<Permission> delete(@PathVariable("id") UUID id) {
		return permissionService.delete(id);
	}
}
