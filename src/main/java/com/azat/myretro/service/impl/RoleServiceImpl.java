package com.azat.myretro.service.impl;

import java.util.Optional;
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
import com.azat.myretro.model.PermissionRole;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.Role;
import com.azat.myretro.repository.PermissionRepository;
import com.azat.myretro.repository.RolePermissionRepository;
import com.azat.myretro.repository.RoleRepository;
import com.azat.myretro.service.RoleService;
import com.azat.myretro.validator.RoleValidator;

@Service
public class RoleServiceImpl implements RoleService {
	
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Autowired
	RoleValidator roleValidator;

	@Override
	public Response<Role> findByRoleName(String roleName) {
		Response<Role> response = Response.create(Role.class);
		roleValidator.validateRoleName(roleName, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}

		try {
			Role role = roleRepository.findByRoleName(roleName);
			if (role == null) {
				response.message(RoleServiceImpl.class.getName(), Error.ERR0045.name(), Error.ERR0045.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.NOT_FOUND);
				logger.error(response.printMessages());
				return response;
			} else {
				return Response.create(Role.class).item(role).code(HttpStatus.OK);
			}
		} catch (EmptyResultDataAccessException e) {
			response.message(RoleServiceImpl.class.getName(), Error.ERR0045.name(), Error.ERR0045.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<Role> create(Role role) {
		Response<Role> response = Response.create(Role.class);	
		roleValidator.validateInsert(role, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}
		
		try {
			Role exist = roleRepository.findByRoleName(role.getName());
			if (exist == null) {
				Role newItem = roleRepository.save(role);
				return Response.create(Role.class).item(newItem).code(HttpStatus.OK);
			} else {
				response.message(RoleServiceImpl.class.getName(), Error.ERR0046.name(), Error.ERR0046.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.CONFLICT);
				logger.error(response.printMessages());
				return response;
			}
		}catch (DuplicateKeyException e) {
			response.message(RoleServiceImpl.class.getName(), Error.ERR0046.name(), Error.ERR0046.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<Role> update(Role role) {
		Response<Role> response = Response.create(Role.class);
		roleValidator.validateUpdate(role, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}
		
		try {
			Role exist = roleRepository.getOne(role.getId());
			if (exist == null) {
				response.message(RoleServiceImpl.class.getName(), Error.ERR0045.name(), Error.ERR0045.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.NOT_FOUND);
				logger.error(response.printMessages());
				return response;
			} else {
				exist.setName(role.getName());
				exist.setTitle(role.getTitle());
				
				if(role.getStatus() == 0 || role.getStatus() == 1) {
					exist.setStatus(role.getStatus());
				}
				Role newItem = roleRepository.save(exist);
				return Response.create(Role.class).item(newItem).code(HttpStatus.OK);
			}
		}catch (DuplicateKeyException e) {
			response.message(RoleServiceImpl.class.getName(), Error.ERR0047.name(), Error.ERR0047.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}catch (DataIntegrityViolationException e) {
			response.message(RoleServiceImpl.class.getName(), Error.ERR0047.name(), Error.ERR0047.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}
	}
	
	@Override
	public Response<Role> delete(UUID roleId) {
		Response<Role> response = Response.create(Role.class);
		roleValidator.validateDelete(roleId, response);

		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}

		try {
			roleRepository.deleteById(roleId);
			logger.info(RoleServiceImpl.class.getName() + "Role deleted by id: " + roleId);
			return Response.create(Role.class).code(HttpStatus.NO_CONTENT);
		}catch(Exception e){
			response.code(HttpStatus.NOT_FOUND);
			logger.error("Role with id: " + roleId + " not found.");
			return response;
		}
	}

	@Override
	public Response<Role> getByRoleId(UUID roleId) {
		Response<Role> response = Response.create(Role.class);
		Optional<Role> findById = roleRepository.findById(roleId);
		Role role = findById.get();
		response.addItem(role);
		logger.info(response.toString());
		return response;
	}

	@Override
	public Response<Role> list(String filter) {
		
		Response<Role> response = Response.create(Role.class);
		
		if (StringUtils.isEmpty(filter)) {
			filter = "%";
		}
		// TODO add filter to search query
		response.items(roleRepository.getRoles()).code(HttpStatus.OK);
		logger.info(response.toString());
		return response;
	}
	
	@Override
	public Response<Permission> getPermissionByRoleId(UUID roleId) {
		Response<Permission> response = Response.create(Permission.class);
		response.items(permissionRepository.getRolePermissions(roleId)).code(HttpStatus.OK);
		logger.info(response.toString());
		return response;
		
	}

	@Override
	public Response<PermissionRole> getRolePermissionByRoleId(UUID roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<PermissionRole> createRolePermission(PermissionRole rolePermission) {
		Response<PermissionRole> response = Response.create(PermissionRole.class);	
		roleValidator.validateInsertRolePermission(rolePermission, response);
		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}
		
		try {
			PermissionRole exist = rolePermissionRepository.findByRoleIdAndPermissionId(rolePermission.getRole_id(), rolePermission.getPermission_id());
			if (exist == null) {
				PermissionRole newItem = rolePermissionRepository.save(rolePermission);
				response.item(newItem).code(HttpStatus.OK);
				logger.info(response.toString());
				return response;
			} else {
				response.message(RoleServiceImpl.class.getName(), Error.ERR0048.name(), Error.ERR0048.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.CONFLICT);
				logger.error(response.printMessages());
				return response;
			}
		}catch (DuplicateKeyException e) {
			response.message(RoleServiceImpl.class.getName(), Error.ERR0048.name(), Error.ERR0048.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.CONFLICT);
			logger.error(response.printMessages());
			return response;
		}
	}

	@Override
	public Response<PermissionRole> deleteRolePermission(UUID roleId, UUID permissionId) {
		Response<PermissionRole> response = Response.create(PermissionRole.class);
		roleValidator.validateDeleteRolePermission(roleId, permissionId, response);

		if (response.hasErrors()) {
			logger.error(response.printMessages());
			return response;
		}

		try {
			PermissionRole pm =  rolePermissionRepository.findByRoleIdAndPermissionId(roleId,permissionId);
			rolePermissionRepository.delete(pm);
			response.code(HttpStatus.NO_CONTENT);
			logger.info(RoleServiceImpl.class.getName() + " deleteRolePermission: " + pm.toString());
			return response;
		}catch(Exception e){
			response.code(HttpStatus.BAD_REQUEST);
			logger.error(RoleServiceImpl.class.getName() + "Bad request. Could not delete... ");
			return response;
		}
	}
}
