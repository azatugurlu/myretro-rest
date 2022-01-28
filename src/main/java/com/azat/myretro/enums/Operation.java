package com.azat.myretro.enums;

public enum Operation {

	ENTRY(1), CANCEL(2), UPDATE(3), NOOPER(0);
	
	private final int value;

	Operation(final int newValue) {
		value = newValue;
	}

	public int getValue() {
		return value;
	}
}
