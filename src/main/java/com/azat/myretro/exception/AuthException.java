package com.azat.myretro.exception;

import javax.servlet.http.HttpServletResponse;

public class AuthException extends GeneralException {

	private static final long serialVersionUID = -8726247459147448419L;

	public AuthException() {
		super(HttpServletResponse.SC_UNAUTHORIZED);
	}

	public AuthException(String message) {
		super(HttpServletResponse.SC_UNAUTHORIZED, message);
	}

	public AuthException(com.azat.myretro.enums.Error error) {
		super(HttpServletResponse.SC_UNAUTHORIZED, error);
	}

}
