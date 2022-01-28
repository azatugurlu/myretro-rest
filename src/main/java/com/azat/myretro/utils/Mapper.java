package com.azat.myretro.utils;


import com.azat.myretro.dto.UserDto;
import com.azat.myretro.model.User;


public class Mapper {

	public static UserDto model2dto(User model, UserDto dto) {
		if (dto == null) {
			dto = new UserDto();
		}
		dto.setId(model.getId());
		dto.setCreateBy(model.getCreateBy());
		dto.setCreateDate(model.getCreateDate());
		dto.setEnabled(model.isEnabled());
		dto.setFirstname(model.getFirstname());
		dto.setLastname(model.getLastname());
		dto.setPhone(model.getPhone());
		dto.setUpdateBy(model.getUpdateBy());
		dto.setUpdateDate(model.getUpdateDate());
		dto.setUsername(model.getUsername());
		dto.setIsTfaEnabled(model.getIs_tfa_enabled());
		dto.setTfaDefaultType(model.getTfa_default_type());
		
		if(model.getUserAccountInformation() != null) {
			dto.getUserAccountInformation().setEmailApproveDate(model.getUserAccountInformation().getEmail_approve_date());
			dto.getUserAccountInformation().setEmailVerificationStatus(model.getUserAccountInformation().getEmail_verification_status());
			dto.getUserAccountInformation().setPhoneApproveDate(model.getUserAccountInformation().getPhone_approve_date());
			dto.getUserAccountInformation().setPhoneVerificationStatus(model.getUserAccountInformation().getPhone_verification_status());
			dto.getUserAccountInformation().setId(model.getUserAccountInformation().getId());
		}
		
		return dto;
		
	}
}
