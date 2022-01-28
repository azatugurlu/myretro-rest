package com.azat.myretro.utils;

import java.security.SecureRandom;
import java.util.Random;

public class PasswordUtils {
	private static final Random RANDOM = new SecureRandom();

	/**
	 * Generate a random temporary password of given length
	 *
	 * @return String generated temporary password
	 */
	public static String generatePassword(int LENGTH) {
		String letters = "ABCDEFGHJKMNPQRSTUVWXYZ23456789abcdefghjkmnpqrstuvwxyz_-+=#!";

		String pw = "";
		for (int i = 0; i < LENGTH; i++) {
			int index = (int) (RANDOM.nextDouble() * letters.length());
			pw += letters.substring(index, index + 1);
		}
		return pw;
	}

}
