package com.enquiry.student.binding;

import lombok.Data;


public class EnquiryForm {
	
	private String studentName;
	private String courseName;
	private String enquiryStatus;
	private String classMode;
	private Integer phnNumber;
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
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
	public Integer getPhnNumber() {
		return phnNumber;
	}
	public void setPhnNumber(Integer phnNumber) {
		this.phnNumber = phnNumber;
	}
	
	
}
