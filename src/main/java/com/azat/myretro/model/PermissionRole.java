package com.azat.myretro.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.azat.myretro.model.base.BaseModelAudit;

@Entity
public class PermissionRole extends BaseModelAudit {

	@Column(name = "permission_id")
	private UUID permission_id;

	@Column(name = "role_id")
	private UUID role_id;

	public UUID getPermission_id() {
		return permission_id;
	}

	public void setPermission_id(UUID permission_id) {
		this.permission_id = permission_id;
	}

	public UUID getRole_id() {
		return role_id;
	}

	public void setRole_id(UUID role_id) {
		this.role_id = role_id;
	}

	@Override
	public String toString() {
		return "PermissionRole [permission_id=" + permission_id + ", role_id=" + role_id + "]";
	}
}
