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

import com.azat.myretro.model.Response;
import com.azat.myretro.model.Team;
import com.azat.myretro.model.TeamUser;
import com.azat.myretro.service.TeamService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/teams")
public class TeamController {
	
	@Autowired
	private TeamService teamService;
	
	@PreAuthorize("hasAnyAuthority('can_manage_team')")
	@ApiOperation(value = "Create the team", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Team created, success")})
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	Response<Team> create(@RequestBody Team inputTeam) {
		return teamService.create(inputTeam);
	}
	
	@PreAuthorize("hasAnyAuthority('can_manage_team')")
	@ApiOperation(value = "Update given team", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Team updated, success"),
			@ApiResponse(code = 404, message = "Team not found, failed") })
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	Response<Team> update(@RequestBody Team inputTeam) {
		return teamService.update(inputTeam);
	}
	
	@PreAuthorize("hasAnyAuthority('can_manage_team')")
	@ApiOperation(value = "Remove the team by given id", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Team removed, success"),
			@ApiResponse(code = 404, message = "Team not found, failed") })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	Response<Team> delete(@PathVariable("id") UUID id) {
		return teamService.delete(id);
	}
	

	@RequestMapping(value = "/{teamId}", method = RequestMethod.GET)
	public Response<Team> getById(@PathVariable("teamId") UUID teamId) {
		Response<Team> response = teamService.getByTeamId(teamId);
		response.code(HttpStatus.OK);
		return response;
	}

	
	@PreAuthorize("hasAnyAuthority('can_manage_team')")
	@ApiOperation(value = "Get teams", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Team found, success"),
			@ApiResponse(code = 404, message = "Team not found, failed") })
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Response<Team> list(@RequestParam(value = "filter", required = false) String filter) {
		return teamService.list(filter);
	}
	
	@PreAuthorize("hasAuthority('can_manage_team')")
	@RequestMapping(value = "/byname/{teamName}", method = RequestMethod.GET)
	public Response<Team> getTeamByName(@PathVariable("teamName") String teamName) {
		return teamService.findByTeamName(teamName);
	}
	
	@PreAuthorize("hasAuthority('can_manage_team')")
	@ApiOperation(value = "Get team users", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Team users found, success"),
			@ApiResponse(code = 404, message = "TEam Users not found, failed") })
	@RequestMapping(value = "/{teamId}/users", method = RequestMethod.GET)
	@ResponseBody
	public Response<TeamUser> getTeamUsers(@PathVariable("teamId") UUID teamId) {
		return teamService.getTeamUserByTeamId(teamId);
	}
	
	@PreAuthorize("hasAnyAuthority('can_manage_team')")
	@ApiOperation(value = "Create the teamUser", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "TeamUser created, success")})
	@RequestMapping(value = "/{teamId}/users", method = RequestMethod.POST)
	@ResponseBody
	Response<TeamUser> createRolePermission(@PathVariable("teamId") String teamId, @RequestBody TeamUser inputTeamUser) {
		return teamService.createTeamUser(inputTeamUser);
	}
	
	@PreAuthorize("hasAnyAuthority('can_manage_team')")
	@ApiOperation(value = "Remove the TeamUser by given id", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "TeamUser removed, success"),
			@ApiResponse(code = 404, message = "TeamUser not found, failed") })
	@RequestMapping(value = "/{teamId}/users/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	Response<TeamUser> delete(@PathVariable("teamId") UUID teamId, @PathVariable("userId") UUID userId) {
		return teamService.deleteTeamUser(teamId, userId);
	}

}
