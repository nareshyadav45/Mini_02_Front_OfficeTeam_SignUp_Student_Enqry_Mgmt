package com.enquiry.student.utility;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class PwdUtils {
	
	
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-+=<>?";
    private static final int PASSWORD_LENGTH = 12;
    private final SecureRandom random = new SecureRandom();
	
	public  String generatePwd(String email) {
		 StringBuilder password = new StringBuilder(email.substring(0, Math.min(email.length(), 5))); // Take first 5 characters of email

	        for (int i = 0; i < PASSWORD_LENGTH - password.length(); i++) {
	            int index = random.nextInt(CHARACTERS.length());
	            password.append(CHARACTERS.charAt(index));
	        }

	        return password.toString();
		}
	
	

}
