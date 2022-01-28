package com.azat.myretro.request;

public class LoginRequest extends BaseRequest {
	private String usernameOrPhone;
	private String password;

	public String getUsernameOrPhone() {
		return usernameOrPhone;
	}

	public void setUsernameOrPhone(String usernameOrPhone) {
		this.usernameOrPhone = usernameOrPhone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
