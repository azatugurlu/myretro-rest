package com.azat.myretro.enums;

public enum BoardColors {
	GREEN("#67C23A"), RED("#F56C6C"), YELLOW("#E6A23C"), GREY("#909399");
	
	private final String value;

	BoardColors(final String newValue) {
		value = newValue;
	}

	public String getValue() {
		return value;
	}
}
