package com.azat.myretro.response;

public class UserInfoResponse {
	private String isTfaEnabled;

	public UserInfoResponse(String isTfaEnabled) {
		this.isTfaEnabled = isTfaEnabled;
	}

	public String getIsTfaEnabled() {
		return isTfaEnabled;
	}

	public void setIsTfaEnabled(String isTfaEnabled) {
		this.isTfaEnabled = isTfaEnabled;
	}
}
