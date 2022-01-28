package com.azat.myretro.utils;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class ServiceUtil {
	public static boolean userHasGivenRoleOrPerm(String autor) {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			String authority = grantedAuthority.getAuthority();
			if (autor.equals(authority)) {
				return true;
			}
		}
		return false;
	}
}
