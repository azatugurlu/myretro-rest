package com.azat.myretro.exception;

public class ResourceNotFoundException extends RuntimeException {

	private String code;

	public ResourceNotFoundException(String code, String msg) {
		super(msg);
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}
}
