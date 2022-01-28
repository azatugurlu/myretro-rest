package com.azat.myretro.service;

import com.azat.myretro.model.Response;
import com.azat.myretro.model.TokenOperationType;
import com.azat.myretro.model.TokenValidate;
import com.azat.myretro.model.User;

public interface TokenOperationService {
    public Response reset(TokenOperationType passwordReset);
    public Response validate(TokenValidate passwordResetToken);
	public Response<User> register(TokenOperationType tokenOperation);
}
