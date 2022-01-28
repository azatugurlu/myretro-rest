package com.azat.myretro.exception;

import javax.servlet.http.HttpServletResponse;

public class NotFoundException extends GeneralException {

	private static final long serialVersionUID = 4291167624870879375L;

	public NotFoundException() {
		super(HttpServletResponse.SC_NOT_FOUND);
	}

	public NotFoundException(String message) {
		super(HttpServletResponse.SC_NOT_FOUND, message);
	}

	public NotFoundException(com.azat.myretro.enums.Error error) {
		super(HttpServletResponse.SC_NOT_FOUND, error);
	}
}
