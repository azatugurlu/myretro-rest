package com.azat.myretro.model.base;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseModel implements Serializable {

	private static final long serialVersionUID = -2471711667232446587L;

}
