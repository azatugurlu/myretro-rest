package com.azat.myretro.exception;

public class ResourceAccessDeniedException extends RuntimeException {

	private String code;

	public ResourceAccessDeniedException(String code, String msg) {
		super(msg);
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}
}
