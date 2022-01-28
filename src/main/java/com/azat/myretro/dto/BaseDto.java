package com.azat.myretro.dto;

import java.io.Serializable;
import java.util.UUID;

public abstract class BaseDto implements Serializable {
	private static final long serialVersionUID = -9188023538164226357L;
	private UUID id;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "BaseDto [id=" + id + "]";
	}
	

}
