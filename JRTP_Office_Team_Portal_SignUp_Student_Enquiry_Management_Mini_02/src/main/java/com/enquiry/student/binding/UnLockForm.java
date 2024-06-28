package com.enquiry.student.binding;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UnLockForm {
	
	private String email;
	
	private String temPassword;
	
	@NotNull(message = "Password Can't be Null")
	@Size(min = 2, max = 30, message = "Password must be between 2 and 30 AlphaNumeric")
	private String newPassword;
	 
	@NotNull(message = "Password Can't be Null")
	@Size(min = 2, max = 30, message = "Password must be between 2 and 30 AlphaNumeric")
	private String confirmPassword;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTemPassword() {
		return temPassword;
	}

	public void setTemPassword(String temPassword) {
		this.temPassword = temPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	
	
	
}
