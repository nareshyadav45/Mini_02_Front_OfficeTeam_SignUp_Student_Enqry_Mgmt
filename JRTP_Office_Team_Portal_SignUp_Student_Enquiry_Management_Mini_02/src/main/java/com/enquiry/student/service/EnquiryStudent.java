package com.enquiry.student.service;

import java.util.List;

import com.enquiry.student.binding.DashboardUser;
import com.enquiry.student.binding.EnquiryFilterForm;
import com.enquiry.student.binding.EnquiryForm;


public interface EnquiryStudent {
	
	public List<String> getListOfCourses();
	
	public List<String> getListOfEnquiryStatus();

	public DashboardUser dashboardUser(Integer userId);
	
	public String addStudentEnquiry(EnquiryForm enquiryForm);
	
	public List<EnquiryForm> getStudentEnquiries(Integer UserId,EnquiryFilterForm enquiryFilterForm);
	
	public EnquiryForm getEnquiryByEnquiryId(Integer eid);
	
	
}
