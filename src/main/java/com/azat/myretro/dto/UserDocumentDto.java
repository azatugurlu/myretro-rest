package com.azat.myretro.dto;

import java.util.UUID;

public class UserDocumentDto extends BaseDtoAudit {

	private static final long serialVersionUID = 1L;
	
	private String idCard;
	private String invoice;
	
	public UserDocumentDto() {
		
	}

	public UserDocumentDto(String idCard, String invoice) {
		this.idCard = idCard;
		this.invoice = invoice;
	}

	public UserDocumentDto(UUID id, String idCard, String invoice) {
		this.setId(id);
		this.idCard = idCard;
		this.invoice = invoice;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	@Override
	public String toString() {
		return "UserDocumentDto [idCard=" + idCard + ", invoice=" + invoice + "]";
	}
}
