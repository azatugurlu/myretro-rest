package com.azat.myretro.auth;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.StringUtils;

import com.azat.myretro.model.Response;
import com.azat.myretro.model.Role;
import com.azat.myretro.model.TfaTokenValidate;
import com.azat.myretro.model.User;
import com.azat.myretro.service.TFAService;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {
	
	@Autowired
	private TFAService tfaService;
	private String grantType;
	
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		User user = (User) authentication.getPrincipal();

		Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());
		String grantType = authentication.getOAuth2Request().getGrantType();
		info.put("email", user.getUsername());
		info.put("phone", user.getPhone());
		if(user.getIs_tfa_enabled().equals("y")) {
			info.put("is_tfa_enabled", user.getIs_tfa_enabled());
			info.put("tfa_default_type", user.getTfa_default_type());
			info.put("is_tfa_verified", false);	
		}else {
			info.put("is_tfa_enabled", user.getIs_tfa_enabled());
		}
		

		DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
		customAccessToken.setAdditionalInformation(info);
		
		String clientId = authentication.getOAuth2Request().getClientId();
		
		if("USER_MOBILE_APP".equals(clientId)) {

		}	
		
		
		if(user.getIs_tfa_enabled().equals("y") && "password".equals(grantType)) {
			LinkedHashMap<String,String> details =(LinkedHashMap<String,String>) authentication.getUserAuthentication().getDetails();
			String code = details.get("code");
			if(code ==null || StringUtils.isEmpty(code)) {
				throw new BadCredentialsException("Login Failed, code can not be null or empty");
			}else {
				TfaTokenValidate tfaTokenValidate = new TfaTokenValidate();
				tfaTokenValidate.setPhone(user.getPhone());
				tfaTokenValidate.setToken(code);
				Response validate = tfaService.validate(tfaTokenValidate);
				if(validate.hasErrors()) {
					throw new BadCredentialsException("Login Failed, invalid code");
				}else {
					//success login
				}
			}
		}

		return super.enhance(customAccessToken, authentication);
	}
}
