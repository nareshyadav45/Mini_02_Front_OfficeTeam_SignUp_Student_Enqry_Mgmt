package com.enquiry.student.service;

import com.enquiry.student.binding.LoginForm;
import com.enquiry.student.binding.RegistrationForm;
import com.enquiry.student.binding.UnLockForm;

public interface UserService {
	
	public String login(LoginForm form);
	
	public boolean registration(RegistrationForm form);
	
	public String unLock(UnLockForm form);
	public String forgotPwd(String email);

}
