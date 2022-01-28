package com.azat.myretro.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.azat.myretro.email.EmailService;
import com.azat.myretro.enums.Error;
import com.azat.myretro.enums.MessageType;
import com.azat.myretro.model.Response;
import com.azat.myretro.model.TfaToken;
import com.azat.myretro.model.TfaTokenValidate;
import com.azat.myretro.model.User;
import com.azat.myretro.repository.TfaTokenRepository;
import com.azat.myretro.repository.UserRepository;
import com.azat.myretro.service.SMSService;
import com.azat.myretro.service.TFAService;

@Service
public class TFAServiceImpl implements TFAService{
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private SMSService smsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TfaTokenRepository tfaTokenRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	

	@Override
	public Response send(String username, String type) {
		
		Response response = Response.create();
		
		User user = userRepository.findByUsername(username);
		if (user == null) {
			response.message(TFAServiceImpl.class.getName(), Error.ERR0001.name(), Error.ERR0001.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
		
		String token = String.valueOf(new Random().nextInt(9999) + 1000);
		TfaToken tfaToken = new TfaToken();
		tfaToken.setToken(token);
		tfaToken.setUser(user);
		Date date = new Date();
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    Date expirationDate = cal.getTime();
		tfaToken.setExpiry_date(expirationDate);
		tfaTokenRepository.save(tfaToken);
		
		
		
		if(type.equals("sms")) {
			smsService.send(user.getPhone(), token);
		} else {
			Map<String, Object> context = new HashMap<String, Object>();
			context.put("user", user);
			context.put("code", token);
			context.put("year", Calendar.getInstance().get(Calendar.YEAR));
			emailService.send(user.getUsername(), "One Time Password", "otp", context);
		}
		
		response.message(TFAServiceImpl.class.getName(), Error.ERR0043.name(), Error.ERR0043.getErrorMessage(), MessageType.INFO.getValue())
		.code(HttpStatus.OK);
		logger.info(response.printMessages());
		return response;
	}

	@Override
	public Response validate(TfaTokenValidate tfaTokenValidate) {
		
		Response response = Response.create();
		
		User user = userRepository.findByPhone(tfaTokenValidate.getPhone());
		if (user == null) {
			response.message(TFAServiceImpl.class.getName(), Error.ERR0001.name(), Error.ERR0001.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
		
		TfaToken myToken = tfaTokenRepository.findByToken(tfaTokenValidate.getToken());
		if(myToken == null || !myToken.getUser().getId().equals(user.getId())) {
			response.message(TFAServiceImpl.class.getName(), Error.ERR0004.name(), Error.ERR0004.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
		
		Calendar cal = Calendar.getInstance();
		
		if ((myToken.getExpiry_date().getTime() - cal.getTime().getTime()) <= 0) {
			response.message(TFAServiceImpl.class.getName(), Error.ERR0005.name(), Error.ERR0005.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
		
		tfaTokenRepository.deleteById(myToken.getId());
		
		response.message(TFAServiceImpl.class.getName(), Error.ERR0044.name(), Error.ERR0044.getErrorMessage(), MessageType.INFO.getValue())
		.code(HttpStatus.OK);
		logger.info(response.printMessages());
		return response;
	}
	
	@Override
	public Response validatePhoneNumberToken(TfaTokenValidate tfaTokenValidate) {
		
		Response response = Response.create();
		
		User user = userRepository.findByPhone(tfaTokenValidate.getPhone());
		if (user == null) {
			response.message(TFAServiceImpl.class.getName(), Error.ERR0001.name(), Error.ERR0001.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
		
		TfaToken myToken = tfaTokenRepository.findByToken(tfaTokenValidate.getToken());
		if(myToken == null || !myToken.getUser().getId().equals(user.getId())) {
			response.message(TFAServiceImpl.class.getName(), Error.ERR0004.name(), Error.ERR0004.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
		
		Calendar cal = Calendar.getInstance();
		
		if ((myToken.getExpiry_date().getTime() - cal.getTime().getTime()) <= 0) {
			response.message(TFAServiceImpl.class.getName(), Error.ERR0005.name(), Error.ERR0005.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
		
		user.getUserAccountInformation().setPhone_approve_date(new Date());
		user.getUserAccountInformation().setPhone_verification_status(1);
		
		userRepository.save(user);
		
		tfaTokenRepository.deleteById(myToken.getId());
		
		response.message(TFAServiceImpl.class.getName(), Error.ERR0044.name(), Error.ERR0044.getErrorMessage(), MessageType.INFO.getValue())
		.code(HttpStatus.OK);
		logger.info(response.printMessages());
		return response;
	}
	
	@Override
	public Response validateEmailToken(TfaTokenValidate tfaTokenValidate) {
		
		Response response = Response.create();
		
		User user = userRepository.findByUsername(tfaTokenValidate.getEmail());
		if (user == null) {
			response.message(TFAServiceImpl.class.getName(), Error.ERR0001.name(), Error.ERR0001.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
		
		TfaToken myToken = tfaTokenRepository.findByToken(tfaTokenValidate.getToken());
		if(myToken == null || !myToken.getUser().getId().equals(user.getId())) {
			response.message(TFAServiceImpl.class.getName(), Error.ERR0004.name(), Error.ERR0004.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
		
		Calendar cal = Calendar.getInstance();
		
		if ((myToken.getExpiry_date().getTime() - cal.getTime().getTime()) <= 0) {
			response.message(TFAServiceImpl.class.getName(), Error.ERR0005.name(), Error.ERR0005.getErrorMessage(), MessageType.ERROR.getValue())
			.code(HttpStatus.NOT_FOUND);
			logger.error(response.printMessages());
			return response;
		}
		
		user.getUserAccountInformation().setEmail_approve_date(new Date());
		user.getUserAccountInformation().setEmail_verification_status(1);
		
		userRepository.save(user);
		
		tfaTokenRepository.deleteById(myToken.getId());
		
		response.message(TFAServiceImpl.class.getName(), Error.ERR0044.name(), Error.ERR0044.getErrorMessage(), MessageType.INFO.getValue())
		.code(HttpStatus.OK);
		logger.info(response.printMessages());
		return response;
	}

}
