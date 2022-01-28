package com.azat.myretro.service;

import com.azat.myretro.model.Response;
import com.azat.myretro.request.ChangePasswordRequest;

public interface PasswordService {
	public Response change(ChangePasswordRequest changePasswordRequest);
}
