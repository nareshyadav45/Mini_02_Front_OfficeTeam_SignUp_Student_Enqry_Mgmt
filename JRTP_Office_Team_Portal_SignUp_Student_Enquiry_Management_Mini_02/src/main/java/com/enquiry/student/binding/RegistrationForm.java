package com.enquiry.student.binding;

import lombok.Data;

@Data
public class RegistrationForm {
	
	private String userName;
	private String email;
	private Integer  phnNumber;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getPhnNumber() {
		return phnNumber;
	}
	public void setPhnNumber(Integer phnNumber) {
		this.phnNumber = phnNumber;
	}	
	
	
}
