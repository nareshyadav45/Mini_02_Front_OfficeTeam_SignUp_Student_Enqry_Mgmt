package com.enquiry.student.binding;

import lombok.Data;

@Data
public class UnLockForm {
	
	private String email;
	private String temPassword;
	private String newPassword;
	private String confirmPassword;

}
