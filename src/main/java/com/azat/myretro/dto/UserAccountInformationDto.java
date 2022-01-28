package com.azat.myretro.dto;

import java.util.Date;
import java.util.UUID;

public class UserAccountInformationDto extends BaseDtoAudit {
	
	private static final long serialVersionUID = 1L;
	
	private Integer emailVerificationStatus;
	private Date emailApproveDate;
	private Integer phoneVerificationStatus;
	private Date phoneApproveDate;
	private Date membershipCreateDate;
	private Date membershipApproveDate;
	private UserDocumentDto userDocument;
	
	
	public UserAccountInformationDto() {
		userDocument = new UserDocumentDto();
	}


	public UserAccountInformationDto(Integer emailVerificationStatus, Date emailApproveDate,
			Integer phoneVerificationStatus, Date phoneApproveDate, Date membershipCreateDate,
			Date membershipApproveDate, UserDocumentDto userDocument) {
		this.emailVerificationStatus = emailVerificationStatus;
		this.emailApproveDate = emailApproveDate;
		this.phoneVerificationStatus = phoneVerificationStatus;
		this.phoneApproveDate = phoneApproveDate;
		this.membershipCreateDate = membershipCreateDate;
		this.membershipApproveDate = membershipApproveDate;
		this.userDocument = userDocument;
	}
	
	public UserAccountInformationDto(UUID id, Integer emailVerificationStatus, Date emailApproveDate,
			Integer phoneVerificationStatus, Date phoneApproveDate, Date membershipCreateDate,
			Date membershipApproveDate, UserDocumentDto userDocument) {
		this.setId(id);
		this.emailVerificationStatus = emailVerificationStatus;
		this.emailApproveDate = emailApproveDate;
		this.phoneVerificationStatus = phoneVerificationStatus;
		this.phoneApproveDate = phoneApproveDate;
		this.membershipCreateDate = membershipCreateDate;
		this.membershipApproveDate = membershipApproveDate;
		this.userDocument = userDocument;
	}


	public Integer getEmailVerificationStatus() {
		return emailVerificationStatus;
	}


	public void setEmailVerificationStatus(Integer emailVerificationStatus) {
		this.emailVerificationStatus = emailVerificationStatus;
	}


	public Date getEmailApproveDate() {
		return emailApproveDate;
	}


	public void setEmailApproveDate(Date emailApproveDate) {
		this.emailApproveDate = emailApproveDate;
	}


	public Integer getPhoneVerificationStatus() {
		return phoneVerificationStatus;
	}


	public void setPhoneVerificationStatus(Integer phoneVerificationStatus) {
		this.phoneVerificationStatus = phoneVerificationStatus;
	}


	public Date getPhoneApproveDate() {
		return phoneApproveDate;
	}


	public void setPhoneApproveDate(Date phoneApproveDate) {
		this.phoneApproveDate = phoneApproveDate;
	}


	public Date getMembershipCreateDate() {
		return membershipCreateDate;
	}


	public void setMembershipCreateDate(Date membershipCreateDate) {
		this.membershipCreateDate = membershipCreateDate;
	}


	public Date getMembershipApproveDate() {
		return membershipApproveDate;
	}


	public void setMembershipApproveDate(Date membershipApproveDate) {
		this.membershipApproveDate = membershipApproveDate;
	}


	public UserDocumentDto getUserDocument() {
		return userDocument;
	}


	public void setUserDocument(UserDocumentDto userDocument) {
		this.userDocument = userDocument;
	}


	@Override
	public String toString() {
		return "UserAccountInformationDto [emailVerificationStatus=" + emailVerificationStatus + ", emailApproveDate="
				+ emailApproveDate + ", phoneVerificationStatus=" + phoneVerificationStatus + ", phoneApproveDate="
				+ phoneApproveDate + ", membershipCreateDate=" + membershipCreateDate + ", membershipApproveDate="
				+ membershipApproveDate + ", userDocument=" + userDocument + "]";
	}

}
