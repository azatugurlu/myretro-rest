package com.azat.myretro.enums;

public enum MessageType {
	ERROR("error"), INFO("info"),OTHER("other");

	private final String value;

	MessageType(final String newValue) {
		value = newValue;
	}

	public String getValue() {
		return value;
	}

}
