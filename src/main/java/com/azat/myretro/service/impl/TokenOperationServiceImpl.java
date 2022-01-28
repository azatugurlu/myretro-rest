package com.azat.myretro.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.azat.myretro.constant.ServerAdress;
import com.azat.myretro.email.EmailService;
import com.azat.myretro.enums.Error;
import com.azat.myretro.enums.MessageType;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.TokenOperation;
import com.azat.myretro.model.TokenOperationType;
import com.azat.myretro.model.TokenValidate;
import com.azat.myretro.model.User;
import com.azat.myretro.repository.TokenOperationRepository;
import com.azat.myretro.repository.UserRepository;
import com.azat.myretro.service.TokenOperationService;
import com.azat.myretro.utils.AppEncryptor;

@Service
public class TokenOperationServiceImpl implements TokenOperationService{
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AppEncryptor appEncryptor;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenOperationRepository tokenOperationRepository;
	
	
	private static final Logger logger = LoggerFactory.getLogger(TokenOperationServiceImpl.class);

	@Override
	public Response reset(TokenOperationType tokenOperation) {
		
		Response response = Response.create();
		
		User user = userRepository.findByUsername(tokenOperation.getUsername());
		if (user == null) {
			response.message(TokenOperationServiceImpl.class.getName(), Error.ERR0001.name(), Error.ERR0001.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
		
		String token = UUID.randomUUID().toString();
		TokenOperation myToken = new TokenOperation();
		myToken.setToken(token);
		myToken.setUser(user);
		myToken.setOperation_type("reset");
		Date date = new Date();
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    Date expirationDate = cal.getTime();
		myToken.setExpiry_date(expirationDate);
		tokenOperationRepository.save(myToken);
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("user", user);
		context.put("link", ServerAdress.SERVER_IP_ADRESS+ "/#/password-reset-validate?id=" + user.getUsername() + "&token=" + token);
		context.put("year", Calendar.getInstance().get(Calendar.YEAR));
		emailService.send(user.getUsername(), "Password reset", "password-reset", context);
		
		response.message(TokenOperationServiceImpl.class.getName(), Error.ERR0036.name(), Error.ERR0036.getErrorMessage(), MessageType.INFO.getValue())
		.code(HttpStatus.OK);
		logger.info(response.printMessages());
		return response;
	}

	@Override
	public Response validate(TokenValidate passwordResetToken) {
		
		Response response = Response.create();
		
		User user = userRepository.findByUsername(passwordResetToken.getId());
		if (user == null) {
			response.message(TokenOperationServiceImpl.class.getName(), Error.ERR0001.name(), Error.ERR0001.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
		
		TokenOperation myToken = tokenOperationRepository.findByTokenAndOperationType(passwordResetToken.getToken(),passwordResetToken.getOperationType());
		if(myToken == null || !myToken.getUser().getId().equals(user.getId())) {
			response.message(TokenOperationServiceImpl.class.getName(), Error.ERR0037.name(), Error.ERR0037.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
		Calendar cal = Calendar.getInstance();
		
		if ((myToken.getExpiry_date().getTime() - cal.getTime().getTime()) <= 0) {
			response.message(TokenOperationServiceImpl.class.getName(), Error.ERR0038.name(), Error.ERR0038.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
		if(passwordResetToken.getOperationType().equals("reset")) {
			String password = passwordResetToken.getPassword();
			user.setPassword("{bcrypt}" + appEncryptor.encode(password));
			userRepository.save(user);
			response.message(TokenOperationServiceImpl.class.getName(), Error.ERR0039.name(), Error.ERR0039.getErrorMessage(), MessageType.INFO.getValue())
			.code(HttpStatus.OK);
			logger.info(response.printMessages());
			return response;
		}else if(passwordResetToken.getOperationType().equals("register")) {
			user.getUserAccountInformation().setEmail_approve_date(cal.getTime());
			user.getUserAccountInformation().setEmail_verification_status(1);
			userRepository.save(user);
			tokenOperationRepository.delete(myToken);
			response.message(TokenOperationServiceImpl.class.getName(), Error.ERR0040.name(), Error.ERR0040.getErrorMessage(), MessageType.INFO.getValue())
			.code(HttpStatus.OK);
			logger.info(response.printMessages());
			return response;
		}else {
			response.message(TokenOperationServiceImpl.class.getName(), Error.ERR0041.name(), Error.ERR0041.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_ACCEPTABLE);
			logger.error(response.printMessages());
			return response;
		}
		
		
	}
	
	@Override
	public Response register(TokenOperationType tokenOperation) {
		Response response = Response.create();
		
		User user = userRepository.findByUsername(tokenOperation.getUsername());
		if (user == null) {
			response.message(TokenOperationServiceImpl.class.getName(), Error.ERR0001.name(), Error.ERR0001.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
		
		String token = UUID.randomUUID().toString();
		TokenOperation myToken = new TokenOperation();
		myToken.setToken(token);
		myToken.setUser(user);
		myToken.setOperation_type(tokenOperation.getOperationType());
		Date date = new Date();
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    Date expirationDate = cal.getTime();
		myToken.setExpiry_date(expirationDate);
		tokenOperationRepository.save(myToken);
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("user", user);
		context.put("link", ServerAdress.SERVER_IP_ADRESS + "/#/register-validate?id=" + user.getUsername() + "&token=" + token);
		context.put("year", Calendar.getInstance().get(Calendar.YEAR));
		emailService.send(user.getUsername(), "Email verification", "registration-verification", context);
		
		
		response.message(TokenOperationServiceImpl.class.getName(), Error.ERR0042.name(), Error.ERR0042.getErrorMessage(), MessageType.INFO.getValue())
		.code(HttpStatus.OK);
		logger.info(response.printMessages());
		return response;
	}
	
	

}
