package com.azat.myretro.rest;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.azat.myretro.dto.UserDto;
import com.azat.myretro.exception.GeneralException;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.Role;
import com.azat.myretro.model.User;
import com.azat.myretro.request.LoginRequest;
import com.azat.myretro.response.LoginResponse;
import com.azat.myretro.response.UserInfoResponse;
import com.azat.myretro.service.RoleService;
import com.azat.myretro.service.UserService;
import com.azat.myretro.utils.Mapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	
	@RequestMapping(method = RequestMethod.POST,value = "/login-code")
	public Response<LoginResponse> login(@RequestBody LoginRequest req){
		return userService.getLoginCode(req);	
	}
	
	@GetMapping("/users/getInfo/{usernameOrPhone}")
	public Response<UserInfoResponse> getUserInfoByNameOrPhone(@PathVariable("usernameOrPhone") String usernameOrPhone) {
		return userService.getUserInfoByUsernameOrPhone(usernameOrPhone);
	}
	

	@GetMapping("/users/{username}")
	@PreAuthorize("hasAuthority('can_manage_user')")
	public Response<User> getUserByName(@PathVariable("username") String username) {
		return userService.findByUsername(username);
	}
	
	@ApiOperation(value = "Get users", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Users found, success"),
			@ApiResponse(code = 404, message = "Users not found, failed") })
	@PreAuthorize("hasAuthority('can_manage_user')")
	@RequestMapping(method = RequestMethod.GET,value = "/users")
	@ResponseBody
	public Response<User> list(@RequestParam(value = "filter", required = false) String filter, 
			@RequestParam Optional<Integer> page,
			@RequestParam Optional<Integer> size,
			@RequestParam Optional<String> sortBy) {
		return userService.list(filter, PageRequest.of(page.orElse(0), size.orElse(10), Sort.Direction.ASC, sortBy.orElse("username")));
	}
	
	@ApiOperation(value = "Get waiting for register users", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Users found, success"),
			@ApiResponse(code = 404, message = "Users not found, failed") })
	@PreAuthorize("hasAuthority('can_manage_user')")
	@RequestMapping(method = RequestMethod.GET,value = "/users/waiting")
	@ResponseBody
	public Response<User> listWaiting(@RequestParam(value = "filter", required = false) String filter, 
			@RequestParam Optional<Integer> page,
			@RequestParam Optional<Integer> size,
			@RequestParam Optional<String> sortBy) {
		return userService.listWaitingUsers(filter, PageRequest.of(page.orElse(0), size.orElse(10), Sort.Direction.ASC, sortBy.orElse("username")));
	}
	
	@ApiOperation(value = "Get user", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User found, success"),
			@ApiResponse(code = 404, message = "User not found, failed") })
	@RequestMapping(method = RequestMethod.GET,value = "/whoAmI")
	@ResponseBody
	public Response<UserDto> whoAmI() {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
		.getPrincipal();
		
		Response<User> userRes =  userService.findByUsername(principal.toString());
		User user = userRes.getItem();
		
		Response<UserDto> response = Response.create(UserDto.class).code(userRes.getStatus());
		
		if(user != null) {
			UserDto dto = Mapper.model2dto(user, null);
			response.item(dto);
		}
		
		return response;
	}
	
	@PreAuthorize("hasAnyAuthority('can_manage_user')")
	@ApiOperation(value = "Get user by id", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User found, success"),
			@ApiResponse(code = 404, message = "User not found, failed") })
	@RequestMapping(method = RequestMethod.GET,value = "/users/admin/{id}")
	@ResponseBody
	public Response<UserDto> getUser(@PathVariable("id") UUID id) {	
		Response<User> userRes =  userService.get(id);
		User user = userRes.getItem();
		Response<UserDto> response = Response.create(UserDto.class).code(userRes.getStatus());
		
		if(user != null) {
			UserDto dto = Mapper.model2dto(user, null);
			response.item(dto);
		}
		
		return response;
	}

	@RequestMapping(method = RequestMethod.POST,value = "/users")
	public Response<User> save(@RequestBody User req) {
		Response<Role> role = roleService.findByRoleName("role_user");//default user role
		
		Response<User> response = Response.create(User.class);
		if (req != null && req != null) {
			try {
				req.setRoles(role.getItems());
				response = userService.create(req);
				response.code(HttpStatus.OK);

			} catch (GeneralException e) {

				//TODO : exception alindi

			} catch (Exception e) {
				response.code(HttpStatus.INTERNAL_SERVER_ERROR);

			}
		} else {
			response.code(HttpStatus.BAD_REQUEST);

		}

		return response;
	}
	
	@PreAuthorize("hasAnyAuthority('can_manage_user')")
	@ApiOperation(value = "Updates user", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User updated, success"),
			@ApiResponse(code = 404, message = "User not found, failed") })
	@RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
	@ResponseBody
	Response<UserDto> update(@RequestBody UserDto inputUser) {
		return userService.update(inputUser);
	}
}
