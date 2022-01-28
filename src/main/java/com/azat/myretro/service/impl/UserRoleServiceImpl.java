package com.azat.myretro.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.azat.myretro.enums.Error;
import com.azat.myretro.enums.MessageType;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.RoleUser;
import com.azat.myretro.repository.UserRoleRepository;
import com.azat.myretro.service.UserRoleService;
import com.azat.myretro.validator.UserRoleValidator;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserRoleValidator userRoleValidator;

	@Override
	public Response<RoleUser> create(RoleUser userRole) {
		Response<RoleUser> response = Response.create(RoleUser.class);
		userRoleValidator.validateInsert(userRole, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}
		try {
			RoleUser userRoleExist = userRoleRepository.findByRoleIdAndUserId(userRole.getRole_id(),
					userRole.getUser_id());
			if (userRoleExist == null) {
				RoleUser newItem = userRoleRepository.save(userRole);
				response.item(newItem).code(HttpStatus.OK);
				logger.info(response.toString());
				return response;
			} else {
				response.message(UserRoleServiceImpl.class.getName(), Error.ERR0033.name(), Error.ERR0033.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.CONFLICT);
				logger.error(response.printMessages());
				return response;
			}
		} catch (DuplicateKeyException e) {
			response.message(UserRoleServiceImpl.class.getName(), Error.ERR0033.name(), Error.ERR0033.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<RoleUser> update(RoleUser UserRole) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<RoleUser> delete(UUID userRoleId) {
		Response<RoleUser> response = Response.create(RoleUser.class);
		try {
			userRoleValidator.validateDelete(userRoleId, response);
			if (response.hasErrors()) {
				logger.error(response.printMessages());
				return response;
			}

			try {
				userRoleRepository.deleteById(userRoleId);
				response.code(HttpStatus.NO_CONTENT);
				logger.info(UserRoleServiceImpl.class.getName() + "; RoleUser deleted with id: " + userRoleId);
				return response;
			} catch (IllegalArgumentException e) {
				response.message(UserRoleServiceImpl.class.getName(), Error.ERR0034.name(), Error.ERR0034.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.NOT_FOUND);
				logger.error(response.printMessages());
				return response;
			}
		} catch (Exception e) {
			response.message(UserRoleServiceImpl.class.getName(), Error.ERR0034.name(), Error.ERR0034.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<RoleUser> getRolesByUserId(UUID UserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<RoleUser> list(String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<RoleUser> deleteRoleUser( UUID roleId,UUID userId) {
		Response<RoleUser> response = Response.create(RoleUser.class);
		try {
			userRoleValidator.validateDeleteRoleUser(userId, roleId, response);

			if (response.hasErrors()) {
				logger.error(response.printMessages());
				return response;
			}

			try {
				RoleUser roleUser = userRoleRepository.findByRoleIdAndUserId(roleId,userId);
				userRoleRepository.delete(roleUser);
				response.code(HttpStatus.NO_CONTENT);
				logger.info(UserRoleServiceImpl.class.getName() + "; RoleUser deleted: " + roleUser.toString());
				return response;
			} catch (IllegalArgumentException e) {
				response.message(UserRoleServiceImpl.class.getName(), Error.ERR0034.name(), Error.ERR0034.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.NOT_FOUND);
				logger.error(response.printMessages());
				return response;
			}
		} catch (Exception e) {
			response.message(UserRoleServiceImpl.class.getName(), Error.ERR0034.name(), Error.ERR0034.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
			
		}
	}

}
