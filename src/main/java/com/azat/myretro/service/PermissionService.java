package com.azat.myretro.service;

import java.util.UUID;

import com.azat.myretro.model.Permission;
import com.azat.myretro.model.Response;

public interface PermissionService {
	public Response<Permission> create(Permission permission);

	public Response<Permission> update(Permission permission);

	Response<Permission> delete(UUID permissionId);

	public Response<Permission> getByPermissionId(UUID permissionId);

	Response<Permission> list(String filter);
}
