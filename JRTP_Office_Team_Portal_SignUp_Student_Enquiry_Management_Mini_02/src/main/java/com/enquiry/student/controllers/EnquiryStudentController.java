package com.enquiry.student.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enquiry.student.binding.DashboardUser;
import com.enquiry.student.binding.EnquiryFilterForm;
import com.enquiry.student.binding.EnquiryForm;
import com.enquiry.student.binding.ListOfEnquiriesResponse;
import com.enquiry.student.service.EnquiryStudentService;

import jakarta.servlet.http.HttpSession;

@RestController
public class EnquiryStudentController {
	
	@Autowired
	private EnquiryStudentService enquiryStudentService;

	@Autowired
	private HttpSession session;
	
	@GetMapping("/listOfCourses")
	public List<String> courseListNames(){
		
		List<String> listOfCourses = this.enquiryStudentService.getListOfCourses();
		
		return listOfCourses;
	}
	
	@GetMapping("/listOfEnquiryStatuses")
	public List<String> listOfEnquiryStatuses(){
		List<String> listOfEnquiryStatus = enquiryStudentService.getListOfEnquiryStatus();
		return listOfEnquiryStatus;
	}
	

	@GetMapping("/dashboard")
	public DashboardUser dashBoardOfUser(Integer userId) {
          		DashboardUser dashboardUser = this.enquiryStudentService.dashboardUser(userId);
          		
		return dashboardUser;
	}
	
	@PostMapping("/AddEnquiry")
	public String addEnquiryOfStudent(@RequestBody EnquiryForm enquiryForm) {
		boolean studentEnquiry = this.enquiryStudentService.addStudentEnquiry(enquiryForm);
		if(studentEnquiry) {
			return "Enquiry Successfully added";
		}else {
		return "Failed To add Enquiry Because With User Id User Entity Not Found";
	}
	
	}
	
	@GetMapping("/fetchEnquiries")
	public Object fetchEnquiries(@RequestParam("uid") Integer userId,@RequestBody() EnquiryFilterForm filterForm) {
		Object studentEnquiries = this.enquiryStudentService.getStudentEnquiries(userId, filterForm);
		return studentEnquiries;
		
	}
	
	
	
	
	
	
}
