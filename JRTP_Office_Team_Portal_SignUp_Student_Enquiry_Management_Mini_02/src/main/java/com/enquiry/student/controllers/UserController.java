package com.enquiry.student.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.enquiry.student.binding.DashboardUser;
import com.enquiry.student.binding.LoginForm;
import com.enquiry.student.binding.RegistrationForm;
import com.enquiry.student.binding.UnLockForm;
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
			return "User already registered with Given Mail Id  : "+ form.getEmail();
		}
	}
	
	@PatchMapping("/unlock")
	public String userUnLock(@RequestBody UnLockForm form) {
		String unLock = this.userService.unLock(form);
		return unLock;
	}
	
	@GetMapping("/login")
	public ResponseEntity<?> userLogin(@RequestBody LoginForm form){
		Object login = this.userService.login(form);
		if(login instanceof DashboardUser) {
			return ResponseEntity.ok(login);
		}else{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(login);
		}
		
	}
	
	@GetMapping("/forgotpwd")	
	public String forgotPassword(@RequestParam("emai") String email) {
		String forgotPwdResponse = this.userService.forgotPwd(email);
		return forgotPwdResponse;
	}
	
	
	
	
	
	
}

