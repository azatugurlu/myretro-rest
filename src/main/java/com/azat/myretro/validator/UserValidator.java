package com.azat.myretro.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.azat.myretro.dto.UserDto;
import com.azat.myretro.enums.Error;
import com.azat.myretro.enums.MessageType;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.User;
import com.azat.myretro.repository.UserRepository;
import com.azat.myretro.service.UserService;

@Component
public class UserValidator {

	@Autowired
	UserService userService;

	@Autowired
	private UserRepository userRepository;

	public void validateGetByUsername(String username, Response response) {
		if (username == null) {
			response.message(UserValidator.class.getName(), Error.ERR0017.name(), Error.ERR0017.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateInsert(User user, Response response) {
		if (StringUtils.isEmpty(user.getUsername())) {
			response.message(UserValidator.class.getName(), Error.ERR0017.name(), Error.ERR0017.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		} else {
			User exist = userRepository.findByUsername(user.getUsername());
			if (exist != null) {
				response.message(UserValidator.class.getName(), Error.ERR0018.name(), Error.ERR0018.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.CONFLICT);
			}
		}

		if (StringUtils.isEmpty(user.getPhone())) {
			response.message(UserValidator.class.getName(), Error.ERR0019.name(), Error.ERR0019.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		} else {
			User exist = userRepository.findByPhone(user.getPhone());
			if (exist != null) {
				response.message(UserValidator.class.getName(), Error.ERR0020.name(), Error.ERR0020.getErrorMessage(), MessageType.ERROR.getValue())
				.code(HttpStatus.CONFLICT);
			}
		}

		if (StringUtils.isEmpty(user.getPassword())) {
			response.message(UserValidator.class.getName(), Error.ERR0016.name(), Error.ERR0016.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}

		if (StringUtils.isEmpty(user.getFirstname())) {
			response.message(UserValidator.class.getName(), Error.ERR0021.name(), Error.ERR0021.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}

		if (StringUtils.isEmpty(user.getLastname())) {
			response.message(UserValidator.class.getName(), Error.ERR0022.name(), Error.ERR0022.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateUpdate(UserDto user, Response response) {
		if (StringUtils.isEmpty(user.getUsername())) {
			response.message(UserValidator.class.getName(), Error.ERR0017.name(), Error.ERR0017.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
		if (StringUtils.isEmpty(user.getFirstname())) {
			response.message(UserValidator.class.getName(), Error.ERR0021.name(), Error.ERR0021.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
		if (StringUtils.isEmpty(user.getLastname())) {
			response.message(UserValidator.class.getName(), Error.ERR0022.name(), Error.ERR0022.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	public void validateDelete(String userName, Response response) {
		if (StringUtils.isEmpty(userName)) {
			response.message(UserValidator.class.getName(), Error.ERR0017.name(), Error.ERR0017.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
