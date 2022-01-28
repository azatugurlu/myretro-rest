package com.azat.myretro.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.azat.myretro.model.base.BaseIdEntity;

@Entity
public class TeamUser extends BaseIdEntity {
	
	@Column(name = "team_id")
	private UUID team_id;
	
	@Column(name = "user_id")
	private UUID user_id;

	public UUID getTeam_id() {
		return team_id;
	}

	public void setTeam_id(UUID team_id) {
		this.team_id = team_id;
	}

	public UUID getUser_id() {
		return user_id;
	}

	public void setUser_id(UUID user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "TeamUser [team_id=" + team_id + ", user_id=" + user_id + "]";
	}

}
