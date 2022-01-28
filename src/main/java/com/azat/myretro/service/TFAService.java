package com.azat.myretro.service;

import com.azat.myretro.model.Response;
import com.azat.myretro.model.TfaToken;
import com.azat.myretro.model.TfaTokenValidate;

public interface TFAService {
	public Response send(String username, String type);
    public Response validate(TfaTokenValidate tfaTokenValidate);
    public Response validatePhoneNumberToken(TfaTokenValidate tfaTokenValidate);
    public Response validateEmailToken(TfaTokenValidate tfaTokenValidate);
}
