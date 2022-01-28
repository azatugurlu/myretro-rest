package com.azat.myretro.rest;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.azat.myretro.model.Response;
import com.azat.myretro.model.TokenOperationType;
import com.azat.myretro.model.TokenValidate;
import com.azat.myretro.request.ChangePasswordRequest;
import com.azat.myretro.service.PasswordService;
import com.azat.myretro.service.TokenOperationService;

@RestController
@CrossOrigin()
@RequestMapping("/api/v1/password")
public class PasswordController {
    
    @Autowired
    private TokenOperationService passwordResetService;
    
    @Autowired
    private PasswordService passwordService;
    
    
    @ApiOperation(value = "Resets the given user's password and send an email to the user containing the instructions and new password.", notes = "")
	@RequestMapping(value = "/reset", method=RequestMethod.POST)
	@ResponseBody
	Response resetPassword(@RequestBody TokenOperationType passwordReset) {
		return passwordResetService.reset(passwordReset);
	}
    
    @ApiOperation(value = "Validates reset token", notes = "")
	@RequestMapping(value = "/reset/validate", method=RequestMethod.POST)
	@ResponseBody
	Response resetPassword(@RequestBody TokenValidate passwordResetTokenValidate) {
    	passwordResetTokenValidate.setOperationType("reset");
		return passwordResetService.validate(passwordResetTokenValidate);
	}
    
    @ApiOperation(value = "Change authenticated user password.", notes = "")
	@RequestMapping(value = "/change", method=RequestMethod.POST)
	@ResponseBody
	Response changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
		return passwordService.change(changePasswordRequest);
	}
    
    
}
