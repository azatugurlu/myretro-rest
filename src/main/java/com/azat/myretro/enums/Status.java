package com.azat.myretro.enums;

public enum Status {

	ACTIVE(1), CANCEL(2), PASSIVE(3);

	private final int value;

	Status(final int newValue) {
		value = newValue;
	}

	public int getValue() {
		return value;
	}
}
