package com.azat.myretro.service;

import java.util.UUID;

import com.azat.myretro.model.Response;
import com.azat.myretro.model.Team;
import com.azat.myretro.model.TeamUser;

public interface TeamService {
	public Response<Team> create(Team team);
	public Response<Team> update(Team team);
	public Response<Team> delete(UUID teamId);
	public Response<Team> findByTeamName(String roleName);
	public Response<Team> getByTeamId(UUID teamId);
	Response<Team> list(String filter);
	
	public Response<TeamUser> getTeamUserByTeamId(UUID teamId);
	public Response<TeamUser> createTeamUser(TeamUser teamUser);
	public Response<TeamUser> deleteTeamUser(UUID teamId, UUID usernId);
}
