package com.azat.myretro.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.azat.myretro.model.User;
import com.azat.myretro.service.ResourceAuthorization;

@Service
public class ResourceAuthorizationImpl implements ResourceAuthorization{

	@Override
	public boolean hasAccess(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasAccess(String role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void assertAccess(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assertAccess(String role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasGrant(String role) {
		 // get security context from thread local
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return false;
        }
        
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return false;
        }
        
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (role.equals(auth.getAuthority())) {
                return true;
            }
        }

        return false;
	}

}
