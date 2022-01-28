package com.azat.myretro.dto;

import java.util.UUID;

public class UserDto extends BaseDtoAudit {

	@Override
	public String toString() {
		return "UserDto [username=" + username + ", firstname=" + firstname + ", lastname=" + lastname + ", phone="
				+ phone + ", enabled=" + enabled + ", countryId=" + countryId + ", isTfaEnabled=" + isTfaEnabled
				+ ", tfaDefaultType=" + tfaDefaultType + ", memberShipId=" + memberShipId + ", userAccountInformation="
				+ userAccountInformation + "]";
	}

	private static final long serialVersionUID = 2752150247871342654L;

	private String username;

	private String firstname;

	private String lastname;

	private String phone;

	private boolean enabled;

	private UUID countryId;
	
	private String isTfaEnabled;
	
	private String tfaDefaultType;
	
	private String memberShipId;
	
	private UserAccountInformationDto userAccountInformation;
	

	public UserDto() {
		userAccountInformation = new UserAccountInformationDto();
	}

	public UserDto(UUID id) {
		userAccountInformation = new UserAccountInformationDto();
		this.setId(id);
	}

	public UserDto(String username, String firstname, String lastname, String phone, boolean enabled, UUID countryId) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.enabled = enabled;
		this.phone = phone;
		this.countryId = countryId;
	}

	public UserDto(UUID id, String username, String firstname, String lastname, String phone, boolean enabled,
			UUID countryId) {
		this.setId(id);
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.enabled = enabled;
		this.phone = phone;
		this.countryId = countryId;
	}
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public UUID getCountryId() {
		return countryId;
	}

	public void setCountryId(UUID countryId) {
		this.countryId = countryId;
	}

	public String getIsTfaEnabled() {
		return isTfaEnabled;
	}

	public void setIsTfaEnabled(String isTfaEnabled) {
		this.isTfaEnabled = isTfaEnabled;
	}

	public String getTfaDefaultType() {
		return tfaDefaultType;
	}

	public void setTfaDefaultType(String tfaDefaultType) {
		this.tfaDefaultType = tfaDefaultType;
	}

	public UserAccountInformationDto getUserAccountInformation() {
		return userAccountInformation;
	}

	public void setUserAccountInformation(UserAccountInformationDto userAccountInformation) {
		this.userAccountInformation = userAccountInformation;
	}

	public String getMemberShipId() {
		return memberShipId;
	}

	public void setMemberShipId(String memberShipId) {
		this.memberShipId = memberShipId;
	}
	
}
