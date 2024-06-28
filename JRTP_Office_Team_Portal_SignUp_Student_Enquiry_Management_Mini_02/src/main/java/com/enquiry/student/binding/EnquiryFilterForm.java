package com.enquiry.student.binding;

import lombok.Data;

@Data
public class EnquiryFilterForm {

	private String courseName;
	private String enquiryStatus;
	private String classMode;
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getEnquiryStatus() {
		return enquiryStatus;
	}
	public void setEnquiryStatus(String enquiryStatus) {
		this.enquiryStatus = enquiryStatus;
	}
	public String getClassMode() {
		return classMode;
	}
	public void setClassMode(String classMode) {
		this.classMode = classMode;
	} 
	
	
	
}
