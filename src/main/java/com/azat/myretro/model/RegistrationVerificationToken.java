package com.azat.myretro.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class RegistrationVerificationToken extends TokenOperation{
	private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
