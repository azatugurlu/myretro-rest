package com.azat.myretro.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.azat.myretro.dto.UserDto;
import com.azat.myretro.enums.Error;
import com.azat.myretro.enums.MessageType;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.Role;
import com.azat.myretro.model.TokenOperationType;
import com.azat.myretro.model.User;
import com.azat.myretro.model.UserAccountInformation;
import com.azat.myretro.repository.UserRepository;
import com.azat.myretro.request.LoginRequest;
import com.azat.myretro.response.LoginResponse;
import com.azat.myretro.response.UserInfoResponse;
import com.azat.myretro.service.RoleService;
import com.azat.myretro.service.TFAService;
import com.azat.myretro.service.TokenOperationService;
import com.azat.myretro.service.UserService;
import com.azat.myretro.utils.AppEncryptor;
import com.azat.myretro.utils.Mapper;
import com.azat.myretro.validator.UserValidator;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	UserValidator userValidator;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@Autowired
	private AppEncryptor appEncryptor;

	@Autowired
	private TokenOperationService tokenOperationService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private TFAService tfaService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public Response<User> findByUsername(String username) {
		Response<User> response = Response.create(User.class);

		userValidator.validateGetByUsername(username, response);

		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}

		try {
			User user = userRepository.findByUsername(username);
			if (user == null) {
				response.message(UserServiceImpl.class.getName(), Error.ERR0001.name(), Error.ERR0001.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.NOT_FOUND);
				logger.error(response.printMessages());
				return response;
			} else {
				response.item(user).code(HttpStatus.OK);
				logger.info(response.toString());
				return response;
			}
		} catch (EmptyResultDataAccessException e) {
			response.message(UserServiceImpl.class.getName(), Error.ERR0001.name(), Error.ERR0001.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<User> create(User user) {
		Response<User> response = Response.create(User.class);
		response.addItem(userRepository.save(user));
		logger.info(response.toString());
		return response;
	}

	@Override
	public Response<UserDto> update(UserDto userDto) {
		Response<UserDto> response = Response.create(UserDto.class);
		try {
			userValidator.validateUpdate(userDto, response);

			if (response.hasErrors()) {
				logger.error(response.printMessages());
				return response;
			}
			try {
				User existUser = userRepository.findByUsername(userDto.getUsername());
				if (existUser == null) {
					response.message(UserServiceImpl.class.getName(), Error.ERR0001.name(), Error.ERR0001.getErrorMessage(), MessageType.ERROR.getValue())
					.code(HttpStatus.NOT_FOUND);
					logger.error(response.printMessages());
					return response;
				} else {
					existUser.setEnabled(userDto.isEnabled());
					existUser.setFirstname(userDto.getFirstname());
					existUser.setLastname(userDto.getLastname());
					User newItem = userRepository.save(existUser);
					newItem.setPassword(null);
					UserDto newDto = Mapper.model2dto(newItem, userDto);
					response.item(newDto).code(HttpStatus.OK);
					logger.info(response.toString());
					return response;
				}
			} catch (IllegalArgumentException e) {
				response.message(UserServiceImpl.class.getName(), Error.ERR0001.name(), Error.ERR0001.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.NOT_FOUND);
				logger.error(response.printMessages());
				return response;
			}
		} catch (Exception e) {
			response.message(UserServiceImpl.class.getName(), Error.ERR0001.name(), Error.ERR0001.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<User> get(UUID userId) {
		Response<User> response = Response.create(User.class);
		
		try {
			User user = userRepository.getOne(userId);
			if (user == null) {
				response.message(UserServiceImpl.class.getName(), Error.ERR0001.name(), Error.ERR0001.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.NOT_FOUND);
				logger.error(response.printMessages());
				return response;
			} else {
				response.item(user).code(HttpStatus.OK);
				logger.info(response.toString());
				return response;
			}
		} catch (EmptyResultDataAccessException e) {
			response.message(UserServiceImpl.class.getName(), Error.ERR0001.name(), Error.ERR0001.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<User> delete(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<User> list(String filter, Pageable pageReq) {
		Page<User> users = userRepository.getUsers(filter, pageReq);
		//TODO Add log
		return Response.create(User.class).setData(users).code(HttpStatus.OK);
	}
	
	@Override
	public Response<User> listWaitingUsers(String filter, Pageable pageReq) {
		Response<Role> roleResponse = roleService.findByRoleName("role_registration");
		Role roleRegistration = roleResponse.getItem();
		Page<User> users = userRepository.getWaitingUsers(roleRegistration.getId(), filter, pageReq);
		//TODO Add log
		return Response.create(User.class).setData(users).code(HttpStatus.OK);
	}

	@Override
	public Response<User> register(User user) {
		Response<User> response = Response.create(User.class);
		userValidator.validateInsert(user, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}
		try {
			
			UserAccountInformation accountInformation = new UserAccountInformation();
			accountInformation.setEmail_verification_status(0);
			accountInformation.setPhone_verification_status(0);
			
			user.setUserAccountInformation(accountInformation);
			user.setEnabled(true);
			user.setTfa_default_type("email");
			user.setIs_tfa_enabled("y");
			user.setPassword("{bcrypt}" + appEncryptor.encode(user.getPassword()));
			Response<Role> role = roleService.findByRoleName("role_user");// default user role
			
			if (role != null) {
				List<Role> defaultRoles = new ArrayList<Role>();
				defaultRoles.add(role.getItem());
				user.setRoles(defaultRoles);
			}
			userRepository.save(user);
			TokenOperationType tokenOperationType = new TokenOperationType();
			tokenOperationType.setUsername(user.getUsername());
			tokenOperationType.setOperationType("register");
			return tokenOperationService.register(tokenOperationType);
		} catch (IllegalArgumentException e) {
			response.message(UserServiceImpl.class.getName(), Error.ERR0028.name(), Error.ERR0028.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.BAD_REQUEST);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<LoginResponse> getLoginCode(LoginRequest loginRequest) {
		Response<LoginResponse> response = Response.create(LoginResponse.class);
		if(loginRequest.getUsernameOrPhone() == null) {
			response.message(UserServiceImpl.class.getName(), Error.ERR0029.name(), Error.ERR0029.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
			logger.error(response.printMessages());
			return response;
		}
		
		if(loginRequest.getPassword() == null) {
			response.message(UserServiceImpl.class.getName(), Error.ERR0030.name(), Error.ERR0030.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
			logger.error(response.printMessages());
			return response;
		}	
		
		//get user
		User user = userRepository.findByUsernameOrPhone(loginRequest.getUsernameOrPhone());
		
		if(user == null) {
			response.message(UserServiceImpl.class.getName(), Error.ERR0001.name(), Error.ERR0001.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
			logger.error(response.printMessages());
			return response;
		}else {
			
			String encodedPwd = user.getPassword().substring(8);
			//check if user pass is match
			if(!appEncryptor.matches(loginRequest.getPassword(), encodedPwd )) {
				response.message(UserServiceImpl.class.getName(), Error.ERR0031.name(), Error.ERR0031.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.NOT_ACCEPTABLE);
				logger.error(response.printMessages());
				return response;
			}
			
			if(user.getIs_tfa_enabled().equals("y")) {
				tfaService.send(user.getUsername(), user.getTfa_default_type());
				//TODO Add log
				return response.code(HttpStatus.OK);
			}else {
				response.message(UserServiceImpl.class.getName(), Error.ERR0032.name(), Error.ERR0032.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.BAD_REQUEST);
				logger.error(response.printMessages());
				return response;
			}
		}
	}

	@Override
	public Response<UserInfoResponse> getUserInfoByUsernameOrPhone(String usernameOrPhone) {
		Response<UserInfoResponse> response = Response.create(UserInfoResponse.class);
		if(usernameOrPhone == null) {
			response.message(UserServiceImpl.class.getName(), Error.ERR0029.name(), Error.ERR0029.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
			logger.error(response.printMessages());
			return response;
		}
		
		User user = userRepository.findByUsernameOrPhone(usernameOrPhone);
		
		if(user == null) {
			response.message(UserServiceImpl.class.getName(), Error.ERR0001.name(), Error.ERR0001.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
			logger.error(response.printMessages());
			return response;
		}else {
			response.item(new UserInfoResponse(user.getIs_tfa_enabled())).code(HttpStatus.OK);
			logger.info(response.toString());
			return response;
		}
	}
}
