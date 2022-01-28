package com.azat.myretro.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Message {

	MSG001("This contract will be registered"), 
	MSG002("This contract will be cancelled."),
	MSG003("The cash flow for this contract will be recalculated."), 
	MSG004("This contract will be updated."),
	MSG005("There are no changes to this contract.");

	private final String message;

	Message(final String message) {
		this.message = message;
	}

	@JsonValue
	public String getMessage() {
		return this.name() + " - " + message;
	}
}
