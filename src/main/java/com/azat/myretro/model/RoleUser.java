package com.azat.myretro.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.azat.myretro.model.base.BaseIdEntity;

@Entity
public class RoleUser extends BaseIdEntity {

	@Column(name = "role_id")
	private UUID role_id;
	@Column(name = "user_id")
	private UUID user_id;

	public UUID getRole_id() {
		return role_id;
	}

	public void setRole_id(UUID role_id) {
		this.role_id = role_id;
	}

	public UUID getUser_id() {
		return user_id;
	}

	public void setUser_id(UUID user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "RoleUser [role_id=" + role_id + ", user_id=" + user_id + "]";
	}
}
