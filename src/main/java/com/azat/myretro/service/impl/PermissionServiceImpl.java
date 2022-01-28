package com.azat.myretro.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.azat.myretro.enums.Error;
import com.azat.myretro.enums.MessageType;
import com.azat.myretro.model.Permission;
import com.azat.myretro.model.Response;
import com.azat.myretro.repository.PermissionRepository;
import com.azat.myretro.service.PermissionService;
import com.azat.myretro.validator.PermissionValidator;

@Service
public class PermissionServiceImpl implements PermissionService {
	
	private static final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private PermissionValidator permissionValidator;

	@Override
	public Response<Permission> create(Permission permission) {
		Response<Permission> response = Response.create(Permission.class);
		permissionValidator.validateInsert(permission, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}

		try {
			Permission exist = permissionRepository.getPermissionByName(permission.getName());
			if (exist == null) {
				Permission newItem = permissionRepository.save(permission);
				response.item(newItem).code(HttpStatus.OK);
				logger.info(response.toString());
				return response;
			} else {
				response.message(PermissionServiceImpl.class.getName(), Error.ERR0049.name(), Error.ERR0049.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.CONFLICT);
				logger.error(response.printMessages());
				return response;
			}
		} catch (DuplicateKeyException e) {
			response.message(PermissionServiceImpl.class.getName(), Error.ERR0049.name(), Error.ERR0049.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<Permission> update(Permission permission) {
		Response<Permission> response = Response.create(Permission.class);
		permissionValidator.validateUpdate(permission, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}

		try {
			Permission perm = permissionRepository.getOne(permission.getId());
			if (perm == null) {
				response.message(PermissionServiceImpl.class.getName(), Error.ERR0051.name(), Error.ERR0051.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.NOT_FOUND);
				logger.error(response.printMessages());
				return response;
			} else {
				perm.setName(permission.getName());
				perm.setTitle(permission.getTitle());
				Permission newItem = permissionRepository.save(perm);
				response.item(newItem).code(HttpStatus.OK);
				logger.info(response.toString() );
				return response;
			}
		} catch (DuplicateKeyException e) {
			response.message(PermissionServiceImpl.class.getName(), Error.ERR0050.name(), Error.ERR0050.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		} catch (DataIntegrityViolationException e) {
			response.message(PermissionServiceImpl.class.getName(), Error.ERR0050.name(), Error.ERR0050.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<Permission> getByPermissionId(UUID permissionId) {
		Response<Permission> response = Response.create(Permission.class);
		try {
			Permission permission = permissionRepository.getOne(permissionId);
			if (permission == null) {
				response.message(PermissionServiceImpl.class.getName(), Error.ERR0051.name(), Error.ERR0051.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.NOT_FOUND);
				logger.error(response.printMessages());
				return response;
			} else {
				response.item(permission).code(HttpStatus.OK);
				logger.info(response.toString());
				return response;
			}
		} catch (EmptyResultDataAccessException e) {
			response.message(PermissionServiceImpl.class.getName(), Error.ERR0051.name(), Error.ERR0051.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<Permission> list(String filter) {
		Response<Permission> response = Response.create(Permission.class);
		if (StringUtils.isEmpty(filter)) {
			filter = "%";
		}
		// TODO add filter to search query
		response.items(permissionRepository.getPermissions()).code(HttpStatus.OK);
		logger.info(response.toString());
		return response;

	}

	@Override
	public Response<Permission> delete(UUID permissionId) {
		Response<Permission> response = Response.create(Permission.class);
		permissionValidator.validateDelete(permissionId, response);

		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}

		try {
			permissionRepository.deleteById(permissionId);
			response.code(HttpStatus.NO_CONTENT);
			logger.info("permission deleted. ");
			return response;
		} catch (Exception e) {
			response.message(PermissionServiceImpl.class.getName(), Error.ERR0051.name(), Error.ERR0051.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}

	}

}
