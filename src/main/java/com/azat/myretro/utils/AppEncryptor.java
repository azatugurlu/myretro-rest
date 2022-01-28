package com.azat.myretro.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppEncryptor implements PasswordEncoder {

	private PasswordEncoder hardEncoder;
	private PasswordEncoder quickEncoder;

	public AppEncryptor() {
		this.hardEncoder = new BCryptPasswordEncoder();
		this.quickEncoder = new BCryptPasswordEncoder(4);
	}

	@Override
	public String encode(CharSequence pwd) {
		return this.hardEncoder.encode(pwd);
	}

	@Override
	public boolean matches(CharSequence pwd, String encodedPwd) {
		// Legacy support for existing hard encoded passwords
		if (isHardEncoded(encodedPwd))
			return this.hardEncoder.matches(pwd, encodedPwd);
		else
			return this.quickEncoder.matches(pwd, encodedPwd);
	}

	public String sha256(String inStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(inStr.getBytes());

			byte[] hashBytes = md.digest();

			return new String(Hex.encode(hashBytes));
		} catch (NoSuchAlgorithmException se) {
			return inStr;
		}
	}

	public boolean isHardEncoded(String password) {
		return StringUtils.isNotEmpty(password) && password.startsWith("{bcrypt}$2a$10$");
	}
}
