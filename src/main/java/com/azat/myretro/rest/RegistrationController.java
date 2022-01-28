package com.azat.myretro.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.azat.myretro.model.Response;
import com.azat.myretro.model.TokenValidate;
import com.azat.myretro.model.User;
import com.azat.myretro.service.TokenOperationService;
import com.azat.myretro.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/registration/")
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private TokenOperationService tokenOperationService;
	
	@ApiOperation(value = "Register the user", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User created, success")})
	@RequestMapping(method = RequestMethod.POST, value="/register/public")
	@ResponseBody
	public Response<User> register(@RequestBody User user) {
		return userService.register(user);
	}
	
	@ApiOperation(value = "Validates register token", notes = "")
	@RequestMapping(value = "/validate-email", method=RequestMethod.POST)
	@ResponseBody
	Response resetPassword(@RequestBody TokenValidate tokenValidate) {
		tokenValidate.setOperationType("register");
		return tokenOperationService.validate(tokenValidate);
	}
}
