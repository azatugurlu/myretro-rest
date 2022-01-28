package com.azat.myretro.service.impl;
import org.springframework.stereotype.Service;

import com.azat.myretro.service.SMSService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SMSServiceImpl implements SMSService {
	
	private final static String ACCOUNT_SID = "yourAccountSid";
	private final static String AUTH_ID = "AuthID";
	
	static {
		Twilio.init(ACCOUNT_SID, AUTH_ID);
	}

	@Override
	public boolean send(String phone, String message) {
		try {
			Message.creator(new PhoneNumber(phone), new PhoneNumber("yourNumber"),message).create();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	

}
