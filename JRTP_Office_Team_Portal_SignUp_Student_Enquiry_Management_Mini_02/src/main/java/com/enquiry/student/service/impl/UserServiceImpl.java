package com.enquiry.student.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enquiry.student.binding.DashboardUser;
import com.enquiry.student.binding.LoginForm;
import com.enquiry.student.binding.RegistrationForm;
import com.enquiry.student.binding.UnLockForm;
import com.enquiry.student.constants.AppConstants;
import com.enquiry.student.controllers.EnquiryStudentController;
import com.enquiry.student.entity.UserEntity;
import com.enquiry.student.repositories.UserRepository;
import com.enquiry.student.service.UserService;
import com.enquiry.student.utility.EmailUtils;
import com.enquiry.student.utility.PwdUtils;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private EnquiryStudentController enquiryStudentController;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PwdUtils pwdUtils;

	@Override
	public Object login(LoginForm form) {
		//TODO : Check Logging user account is unlocked , only unlocked users can login, if not dispaly the message like please first unlock the account
		
		String email = form.getEmail();
		UserEntity userEntity = this.userRepository.findByEmail(email);
		
		if(userEntity!=null) {
			if(!userEntity.getAccountStatus().matches("LOCKED")) {
				if(form.getPassword().matches(userEntity.getPassword())) {
					//DashboardUser userDashboard=new DashboardUser();
					
					this.session.setAttribute(AppConstants.LOGGED_IN_USER_ID, userEntity.getUid());
					Object sessionUserId = session.getAttribute(AppConstants.LOGGED_IN_USER_ID);
					String string = sessionUserId.toString();
					int userId = Integer.parseInt(string);
					
					
					//TODO : Call the Enquiry Controller DashBoard Method and return the user dashboard based on id who successfully logged in 
					          // return dashboard of user for this method as return type
					DashboardUser dashBoardOfUser = enquiryStudentController.dashBoardOfUser(userId);
					
					//return "http://localhost:8080/dashboard?userid="+sessionUserId+"";
					return dashBoardOfUser;	
				}else {
					return AppConstants.USER_INVALID_PASSWORD_MSG;
				}
				
			}else {
				return AppConstants.USER_ACC_UNLOCK_MSG;
			}
			
		}else {
			return AppConstants.USER_INVALID_MAIL_ID_MSG;
		}
		
	}
  
	@Override
	public boolean registration(RegistrationForm form) {
		// TODO: Unique Mail Validation
		
		String email = form.getEmail();
		UserEntity userEntity = this.userRepository.findByEmail(email);
		
		if(userEntity!=null) {
			return false;
		}
		
	    // TODO :Temporary Pwd Should be generated by app adn set into entity filed password in dt table
		
		String pwd = this.pwdUtils.generatePwd(email);
		
		//TODO : Copy the properties from binding class and set it into Entity class and Save the entity in database
		UserEntity entity =new UserEntity();
		BeanUtils.copyProperties(form, entity);
		
		//TODO : Set Account status as LOCKED 
		
		entity.setAccountStatus("LOCKED");
		entity.setPassword(pwd);
		
		this.userRepository.save(entity);
		
		
		// TODO :Send  mail with Temporary pwd with link to unlock account
		
		String emailTo = form.getEmail();
		
		String subject=AppConstants.REGISTRATION_MAIL_SUBJECT;
		
		StringBuffer body=new StringBuffer();
		body.append("Temporary Pwd :"+pwd);
		body.append("<br>");
		body.append("<br>");
		body.append("<a href=http://localhost:8080/unlock/email?="+form.getEmail() +">Click here to unlcok the account</a>");
		
		this.emailUtils.generateMail(emailTo, subject,body.toString());
		
		return true;
	}

	@Override
	public String unLock(UnLockForm form) {	
		String response=null;
		UserEntity byEmail = this.userRepository.findByEmail(form.getEmail());
		if(byEmail!=null) {
		
			if(byEmail.getAccountStatus().matches(AppConstants.STR_UNLOCKED)) {
				return response=AppConstants.STR_UNLOCK_EXIST;
				
			}else {
				if(byEmail.getPassword().matches(form.getTemPassword())) {
					if(form.getNewPassword().matches(form.getConfirmPassword())) {
						byEmail.setPassword(form.getConfirmPassword());
						byEmail.setAccountStatus(AppConstants.STR_UNLOCKED);
						userRepository.save(byEmail);
					response=AppConstants.ACC_UNLOCK_SUCCESS_MSG;
					}else {
						response= AppConstants.ACC_NEW_AND_CONFIRMATION_PWD_NOT_MATCH;
					}
					
					}else {
						return AppConstants.ACC_TEMP_PWD_INVALID_MSG;
					}		
			}	
			
		}else {
			return response=AppConstants.USER_INVALID_MAIL_ID_MSG;
		}
		
		return response;
	}

	@Override
	public String forgotPwd(String email) {
		
		// TODO : Check With Given Mail Id User is there or not , if present send the pwd to user registered mail id
		UserEntity userEntity = this.userRepository.findByEmail(email);
		if(userEntity!=null) {
			String password = userEntity.getPassword();
			
			String to = userEntity.getEmail();
			String subject=AppConstants.FORGOT_PWD_SUBJECT;
			String body ="<h3>Dear  "+userEntity.getUserName()+" <br> your forgoted password is :"+ password+" </h3>";
			
			boolean mail = this.emailUtils.generateMail(to, subject,  body);
		    if(mail==true) {
		    	return AppConstants.FORGOT_PWD_SUCCESS_MSG;
		    } 
		    else {
		    	return AppConstants.SERVER_SIDE_PROBLEM_MAIL_SEND_MSG;
		    }
			
		}else {
			return AppConstants.USER_INVALID_MAIL_ID_MSG;
		}

	}

}
