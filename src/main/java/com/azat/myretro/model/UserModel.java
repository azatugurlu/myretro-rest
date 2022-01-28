package com.azat.myretro.model;

import com.azat.myretro.model.base.BaseIdEntity;

public class UserModel extends BaseIdEntity{
	
	private static final long serialVersionUID = 1L;
	private String email;
	private String username;
	private String password;
	private boolean enabled;

}
