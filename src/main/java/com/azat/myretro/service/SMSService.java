package com.azat.myretro.service;

public interface SMSService {
	boolean send(String phone, String message);
}
