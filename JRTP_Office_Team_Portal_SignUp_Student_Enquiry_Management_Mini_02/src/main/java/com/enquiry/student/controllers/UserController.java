package com.enquiry.student.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.enquiry.student.binding.RegistrationForm;
import com.enquiry.student.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/singup")
	public String userRegistration(@RequestBody RegistrationForm form) {
		
		boolean registration = this.userService.registration(form);
		if(registration==true) {
			return "User Registration Completed Successfully and You can Check Mail to Unlock the Account";
		}
		else {
			return "User already registered with Given Mail Id"+form.getEmail();
		}
	}
	
}

