package com.azat.myretro.exception;

import javax.servlet.http.HttpServletResponse;

public class ValidationException extends GeneralException {

	private static final long serialVersionUID = 4291167624870879375L;

	public ValidationException() {
		super(HttpServletResponse.SC_BAD_REQUEST);
	}

	public ValidationException(String message) {
		super(HttpServletResponse.SC_BAD_REQUEST, message);
	}

	public ValidationException(com.azat.myretro.enums.Error error) {
		super(HttpServletResponse.SC_BAD_REQUEST, error);
	}
}
