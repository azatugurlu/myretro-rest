package com.azat.myretro.email;

import java.util.Map;

import com.azat.myretro.model.Response;


public interface EmailService {

	Response send(String to, String subject, String template, Map<String, Object> context);

}
