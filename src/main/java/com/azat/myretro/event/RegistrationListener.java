package com.azat.myretro.event;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.azat.myretro.constant.ServerAdress;
import com.azat.myretro.email.EmailService;
import com.azat.myretro.model.RegistrationVerificationToken;
import com.azat.myretro.model.User;
import com.azat.myretro.repository.TokenOperationRepository;
import com.azat.myretro.service.UserService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
  
    @Autowired
    private UserService userService;
    
    @Autowired
	private EmailService emailService;
    
    @Autowired
	private TokenOperationRepository passwordResetTokenRepository;
 
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
 
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
    	User user = event.getUser();
    	
    	String token = UUID.randomUUID().toString();
    	RegistrationVerificationToken myToken = new RegistrationVerificationToken();
		myToken.setToken(token);
		myToken.setUser(user);
		Date date = new Date();
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    Date expirationDate = cal.getTime();
		myToken.setExpiry_date(expirationDate);
		passwordResetTokenRepository.save(myToken);
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("user", user);
		context.put("link", ServerAdress.SERVER_IP_ADRESS + "/#/registration-validate?id=" + user.getUsername() + "&token=" + token);
		context.put("year", Calendar.getInstance().get(Calendar.YEAR));
		emailService.send(user.getUsername(), "Password reset", "password-reset", context);
    }
}
