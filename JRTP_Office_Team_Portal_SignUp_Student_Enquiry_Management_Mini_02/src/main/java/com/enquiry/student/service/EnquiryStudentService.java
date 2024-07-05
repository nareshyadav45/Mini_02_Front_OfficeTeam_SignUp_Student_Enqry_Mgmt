package com.enquiry.student.service;

import java.util.List;

import com.enquiry.student.binding.DashboardUser;
import com.enquiry.student.binding.EnquiryFilterForm;
import com.enquiry.student.binding.EnquiryForm;
import com.enquiry.student.binding.ListOfEnquiriesResponse;
import com.enquiry.student.entity.StudentEnquiry;


public interface EnquiryStudentService {
	
	public List<String> getListOfCourses();
	
	public List<String> getListOfEnquiryStatus();

	public DashboardUser dashboardUser(Integer userId);
	
	public boolean addStudentEnquiry(EnquiryForm enquiryForm);
	
	public Object getStudentEnquiries(Integer userId,EnquiryFilterForm enquiryFilterForm);
	
	public Object studentEnquiries(EnquiryFilterForm enquiryFilterForm);
	
	public String updateEnquiryByEnquiryId(Integer enquiryId, EnquiryForm enquiryFormUpdated);
	
	public EnquiryForm getEnquiryByEnquiryId(Integer eid);
	
	
}
