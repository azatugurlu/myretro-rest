package com.azat.myretro.config;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.azat.myretro.exception.ResourceAccessDeniedException;
import com.azat.myretro.exception.ResourceNotFoundException;
import com.azat.myretro.model.Response;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

	private Random random = new Random();

	private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

	@ExceptionHandler(ResourceAccessDeniedException.class)
	@ResponseBody
	Response handleResourceAccessDeniedException(HttpServletRequest request, ResourceAccessDeniedException ex) {
		return Response.create().message(ex.getCode(), ex.getMessage(), "error").code(HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody
	Response handleResourceNotFoundException(HttpServletRequest request, ResourceNotFoundException ex) {
		return Response.create().message(ex.getCode(), ex.getMessage(), "error").code(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	Response handleControllerAccessDeniedException(HttpServletRequest request, Throwable ex) {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null) {
			return Response.create().message("You are not logged in.", "error").code(HttpStatus.UNAUTHORIZED);
		} else {
			return Response.create().message("You dont have rights to access.", "error").code(HttpStatus.FORBIDDEN);
		}
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	Response handleControllerException(HttpServletRequest request, Throwable ex) {
		HttpStatus status = getStatus(request);
		Integer errorNum = random.nextInt();
		String message = "An unexpected error happened. Instance# is " + errorNum + ". Instance message is "
				+ ex.getMessage() + ".";
		logger.error("---- Exception(" + errorNum + ") ----", ex);
		ex.printStackTrace();
		return Response.create().message(message, "error").code(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.valueOf(statusCode);
	}
}
