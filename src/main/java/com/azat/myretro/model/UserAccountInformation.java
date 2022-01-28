package com.azat.myretro.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.azat.myretro.model.base.BaseModelAudit;

@Entity
public class UserAccountInformation extends BaseModelAudit {

	@Column(name = "email_verification_status")
	private Integer email_verification_status;

	@Column(name = "email_approve_date")
	private Date email_approve_date;

	@Column(name = "phone_verification_status")
	private Integer phone_verification_status;

	@Column(name = "phone_approve_date")
	private Date phone_approve_date;

	public Integer getEmail_verification_status() {
		return email_verification_status;
	}

	public void setEmail_verification_status(Integer email_verification_status) {
		this.email_verification_status = email_verification_status;
	}

	public Date getEmail_approve_date() {
		return email_approve_date;
	}

	public void setEmail_approve_date(Date email_approve_date) {
		this.email_approve_date = email_approve_date;
	}

	public Integer getPhone_verification_status() {
		return phone_verification_status;
	}

	public void setPhone_verification_status(Integer phone_verification_status) {
		this.phone_verification_status = phone_verification_status;
	}

	public Date getPhone_approve_date() {
		return phone_approve_date;
	}

	public void setPhone_approve_date(Date phone_approve_date) {
		this.phone_approve_date = phone_approve_date;
	}

	@Override
	public String toString() {
		return "UserAccountInformation [email_verification_status=" + email_verification_status
				+ ", email_approve_date=" + email_approve_date + ", phone_verification_status="
				+ phone_verification_status + ", phone_approve_date=" + phone_approve_date + "]";
	}
	
	

	

}
