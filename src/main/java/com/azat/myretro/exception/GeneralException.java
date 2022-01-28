package com.azat.myretro.exception;

import javax.servlet.http.HttpServletResponse;

import com.azat.myretro.enums.Error;

public class GeneralException extends RuntimeException {

	private static final long serialVersionUID = -404705726286821824L;

	private int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
	private Error error;

	public GeneralException(int status) {
		super();
		this.status = status;
	}

	public GeneralException(int status, String message) {
		super(message);
		this.status = status;
	}

	public GeneralException(int status, Error error) {
		super(error.getErrorMessage());
		this.error = error;
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public Error getError() {
		return error;
	}
}
