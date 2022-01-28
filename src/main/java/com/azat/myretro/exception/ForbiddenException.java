package com.azat.myretro.exception;

import javax.servlet.http.HttpServletResponse;

public class ForbiddenException extends GeneralException {

	private static final long serialVersionUID = -8726247459147448419L;

	public ForbiddenException() {
		super(HttpServletResponse.SC_FORBIDDEN);
	}

	public ForbiddenException(String message) {
		super(HttpServletResponse.SC_FORBIDDEN, message);
	}

	public ForbiddenException(com.azat.myretro.enums.Error error) {
		super(HttpServletResponse.SC_FORBIDDEN, error);
	}

}
