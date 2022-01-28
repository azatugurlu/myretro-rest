package com.azat.myretro.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.azat.myretro.enums.Error;
import com.azat.myretro.enums.MessageType;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.User;
import com.azat.myretro.repository.UserRepository;
import com.azat.myretro.request.ChangePasswordRequest;
import com.azat.myretro.service.PasswordService;
import com.azat.myretro.service.UserService;
import com.azat.myretro.utils.AppEncryptor;


@Service
public class PasswordServiceImpl implements PasswordService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AppEncryptor appEncryptor;
	
	private static final Logger logger = LoggerFactory.getLogger(PasswordServiceImpl.class);

	@Override
	public Response change(ChangePasswordRequest changePasswordRequest) {
		Response response = Response.create();
		if(changePasswordRequest.getCurrentPassword() == null || changePasswordRequest.getCurrentPassword().isEmpty()) {
			response.message(PasswordServiceImpl.class.getName(), Error.ERR0052.name(), Error.ERR0052.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.BAD_REQUEST);
			logger.error(response.printMessages());
			return response;
		}
		
		if(changePasswordRequest.getNewPassword() == null || changePasswordRequest.getNewPassword().isEmpty()){
			response.message(PasswordServiceImpl.class.getName(), Error.ERR0053.name(), Error.ERR0053.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.BAD_REQUEST);
			logger.error(response.printMessages());
			return response;
		}
		
		if(changePasswordRequest.getNewPasswordValidate() == null || changePasswordRequest.getNewPasswordValidate().isEmpty()){
			response.message(PasswordServiceImpl.class.getName(), Error.ERR0054.name(), Error.ERR0054.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.BAD_REQUEST);
			logger.error(response.printMessages());
			return response;
		}
		
		if(changePasswordRequest.getNewPassword().length() < 8) {
			response.message(PasswordServiceImpl.class.getName(), Error.ERR0055.name(), Error.ERR0055.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.BAD_REQUEST);
			logger.error(response.printMessages());
			return response;
		}
		
		
		if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getNewPasswordValidate())) {
			response.message(PasswordServiceImpl.class.getName(), Error.ERR0056.name(), Error.ERR0056.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.BAD_REQUEST);
			logger.error(response.printMessages());
			return response;
		}
		
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Response<User> usr = userService.findByUsername(username);
		
		if(usr.getItem() != null) {
			User user = usr.getItem();
			String encodedPwd = user.getPassword().substring(8);
			
			//check if user pass is match
			if(!appEncryptor.matches(changePasswordRequest.getCurrentPassword(), encodedPwd )) {
				response.message(PasswordServiceImpl.class.getName(), Error.ERR0057.name(), Error.ERR0057.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.NOT_ACCEPTABLE);
				logger.error(response.printMessages());
				return response;
			}else {
				user.setPassword("{bcrypt}" + appEncryptor.encode(changePasswordRequest.getNewPassword()));
				userRepository.save(user);
				
				response.message(PasswordServiceImpl.class.getName(), Error.ERR0058.name(), Error.ERR0058.getErrorMessage(), MessageType.INFO.getValue())
						.code(HttpStatus.OK);
				logger.info(response.printMessages());
				return response;
			}			
			
		}else {
			response.message(PasswordServiceImpl.class.getName(), Error.ERR0059.name(), Error.ERR0059.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.BAD_REQUEST);
			logger.error(response.printMessages());
			return response;
		}
	}

}
