package com.azat.myretro.request;

public class ChangePasswordRequest extends BaseRequest {
	private String currentPassword;
	private String newPassword;
	private String newPasswordValidate;
	
	
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNewPasswordValidate() {
		return newPasswordValidate;
	}
	public void setNewPasswordValidate(String newPasswordValidate) {
		this.newPasswordValidate = newPasswordValidate;
	}
}
