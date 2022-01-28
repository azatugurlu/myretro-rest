package com.azat.myretro.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.azat.myretro.model.Response;
import com.azat.myretro.model.TfaTokenValidate;
import com.azat.myretro.service.TFAService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/validation")
public class ValidationController {
	
	@Autowired
	TFAService TFAService;
	
	@ApiOperation(value = "sends token to phone number", notes = "")
	@RequestMapping(value = "/sms", method=RequestMethod.POST)
	@ResponseBody
	Response sendSms() {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return TFAService.send(principal.toString(), "sms");
	}
	
	
	@ApiOperation(value = "Validates phone number token", notes = "")
	@RequestMapping(value = "/sms/validate", method=RequestMethod.POST)
	@ResponseBody
	Response validateSmsCode(@RequestBody TfaTokenValidate tfaTokenValidate) {
		return TFAService.validatePhoneNumberToken(tfaTokenValidate);
	}
	
	
	@ApiOperation(value = "sends token as email", notes = "")
	@RequestMapping(value = "/email", method=RequestMethod.POST)
	@ResponseBody
	Response sendEmail() {
		Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return TFAService.send(principal.toString(), "email");
	}
	
	
	@ApiOperation(value = "Validates email token", notes = "")
	@RequestMapping(value = "/email/validate", method=RequestMethod.POST)
	@ResponseBody
	Response validateEmailCode(@RequestBody TfaTokenValidate tfaTokenValidate) {
		return TFAService.validateEmailToken(tfaTokenValidate);
	}

}
