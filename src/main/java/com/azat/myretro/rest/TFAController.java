package com.azat.myretro.rest;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/v1/tfa")
public class TFAController {
	
	@Autowired
	TFAService TFAService;
	
	@ApiOperation(value = "Validates tfa token", notes = "")
	@RequestMapping(value = "/validate", method=RequestMethod.POST)
	@ResponseBody
	Response resetPassword(@RequestBody TfaTokenValidate tfaTokenValidate) {
		return TFAService.validate(tfaTokenValidate);
	}
}
