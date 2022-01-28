package com.azat.myretro.service;

import java.util.UUID;

import com.azat.myretro.model.Response;
import com.azat.myretro.model.RoleUser;

public interface UserRoleService {
	public Response<RoleUser> create(RoleUser userRole);

	public Response<RoleUser> update(RoleUser userRole);

	Response<RoleUser> delete(UUID Id);

	public Response<RoleUser> getRolesByUserId(UUID UserId);

	Response<RoleUser> list(String filter);

	public Response<RoleUser> deleteRoleUser(UUID userId, UUID roleId);
}
