package com.azat.myretro.service;

import com.azat.myretro.model.User;

public interface ResourceAuthorization {
	boolean hasAccess(User user);
	boolean hasAccess(String role);
	
	void assertAccess(User user);
	void assertAccess(String role);
	
	boolean hasGrant(String role);

}
