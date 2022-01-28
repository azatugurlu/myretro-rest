package com.azat.myretro.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.azat.myretro.model.PermissionRole;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.Role;
import com.azat.myretro.service.RoleService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PreAuthorize("hasAuthority('can_manage_role')")
	@RequestMapping(value = "/byname/{roleName}", method = RequestMethod.GET)
	public Response<Role> getRoleByName(@PathVariable("roleName") String roleName) {
		return roleService.findByRoleName(roleName);
	}
	
	@PreAuthorize("hasAnyAuthority('can_manage_role')")
	@ApiOperation(value = "Create the role", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Role created, success")})
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	Response<Role> create(@RequestBody Role inputRole) {
		return roleService.create(inputRole);
	}
	
	@PreAuthorize("hasAnyAuthority('can_manage_role')")
	@ApiOperation(value = "Update given role", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Role updated, success"),
			@ApiResponse(code = 404, message = "Role not found, failed") })
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	Response<Role> update(@RequestBody Role inputRole) {
		return roleService.update(inputRole);
	}
	
	@PreAuthorize("hasAnyAuthority('can_manage_role')")
	@ApiOperation(value = "Remove the role by given id", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Role removed, success"),
			@ApiResponse(code = 404, message = "Role not found, failed") })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	Response<Role> delete(@PathVariable("id") UUID id) {
		return roleService.delete(id);
	}
	

	@RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
	public Response<Role> getById(@PathVariable("roleId") UUID roleId) {
		Response<Role> role = roleService.getByRoleId(roleId);
		role.code(HttpStatus.OK);
		return role;
	}

	
	@PreAuthorize("hasAnyAuthority('can_manage_role','can_read_role')")
	@ApiOperation(value = "Get roles", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Roles found, success"),
			@ApiResponse(code = 404, message = "Roles not found, failed") })
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Response<Role> list(@RequestParam(value = "filter", required = false) String filter) {
		return roleService.list(filter);
	}
	
	@PreAuthorize("hasAuthority('can_assign_permission_to_role')")
	@ApiOperation(value = "Get role permissions", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Role Permissions found, success"),
			@ApiResponse(code = 404, message = "Role Permissions not found, failed") })
	@RequestMapping(value = "/{roleId}/permissions", method = RequestMethod.GET)
	@ResponseBody
	public Response<Permission> getRolePermissions(@PathVariable("roleId") UUID roleId) {
		return roleService.getPermissionByRoleId(roleId);
	}
	
	
	@PreAuthorize("hasAnyAuthority('can_assign_permission_to_role')")
	@ApiOperation(value = "Create the rolePermission", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "RolePermission created, success")})
	@RequestMapping(value = "/{roleId}/permissions", method = RequestMethod.POST)
	@ResponseBody
	Response<PermissionRole> createRolePermission(@PathVariable("roleId") String roleId, @RequestBody PermissionRole inputRolePermission) {
		return roleService.createRolePermission(inputRolePermission);
	}
	
	@PreAuthorize("hasAnyAuthority('can_assign_permission_to_role')")
	@ApiOperation(value = "Remove the rolePermission by given id", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "RolePermission removed, success"),
			@ApiResponse(code = 404, message = "RolePermission not found, failed") })
	@RequestMapping(value = "/{roleId}/permissions/{permissionId}", method = RequestMethod.DELETE)
	@ResponseBody
	Response<PermissionRole> delete(@PathVariable("roleId") UUID roleId, @PathVariable("permissionId") UUID permissionId) {
		return roleService.deleteRolePermission(roleId, permissionId);
	}
	
	

}
