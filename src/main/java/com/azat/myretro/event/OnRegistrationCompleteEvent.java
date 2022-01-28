package com.azat.myretro.event;

import org.springframework.context.ApplicationEvent;

import com.azat.myretro.model.User;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private User user;
 
    public OnRegistrationCompleteEvent(User user) {
        super(user);
        this.user = user;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

