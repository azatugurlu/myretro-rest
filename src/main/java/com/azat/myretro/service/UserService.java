package com.azat.myretro.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.azat.myretro.dto.UserDto;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.User;
import com.azat.myretro.request.LoginRequest;
import com.azat.myretro.response.LoginResponse;
import com.azat.myretro.response.UserInfoResponse;

public interface UserService {
	public Response<User> findByUsername(String username);
	public Response<User> create(User user);
	public Response<UserDto> update(UserDto user);
	public Response<User> get(UUID userId);
	public Response<User> delete(String userId);
	public Response<User> list(String filter,  Pageable pageReq);
	public Response<User> listWaitingUsers(String filter,  Pageable pageReq);
	public Response<User> register(User user);
	public Response<UserInfoResponse> getUserInfoByUsernameOrPhone(String usernameOrPhone);
	public Response<LoginResponse> getLoginCode(LoginRequest request);
}