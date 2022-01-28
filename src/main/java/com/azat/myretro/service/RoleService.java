package com.azat.myretro.service;

import java.util.UUID;

import com.azat.myretro.model.Permission;
import com.azat.myretro.model.PermissionRole;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.Role;

public interface RoleService {
	public Response<Role> findByRoleName(String roleName);

	public Response<Role> create(Role role);

	public Response<Role> update(Role role);

	public Response<Role> delete(UUID roleId);

	public Response<Role> getByRoleId(UUID roleId);

	Response<Role> list(String filter);

	public Response<Permission> getPermissionByRoleId(UUID roleId);

	public Response<PermissionRole> getRolePermissionByRoleId(UUID roleId);

	public Response<PermissionRole> createRolePermission(PermissionRole rolePermission);

	public Response<PermissionRole> deleteRolePermission(UUID roleId, UUID permissionId);

}