package com.enquiry.student.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.enquiry.student.binding.DashboardUser;
import com.enquiry.student.binding.EnquiryFilterForm;
import com.enquiry.student.binding.EnquiryForm;
import com.enquiry.student.binding.ListOfEnquiriesResponse;
import com.enquiry.student.constants.AppConstants;
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
			return AppConstants.ADD_ENQUIRY_SUCCESS_MSG;
		}else {
		return AppConstants.ADD_ENQUIRY_FAILURE_MSG;
	}
	
	}
	
	@GetMapping("/fetchEnquiries")
	public Object fetchEnquiries(@RequestParam(AppConstants.FETCH_ENQS_USER_ID) Integer userId,@RequestBody() EnquiryFilterForm filterForm) {
		Object studentEnquiries = this.enquiryStudentService.getStudentEnquiries(userId, filterForm);
		if(studentEnquiries instanceof ListOfEnquiriesResponse) {
			return studentEnquiries;
		}else {
			return studentEnquiries;
		}	
	}
	
	@GetMapping("/fetch")
	public Object getListOfEnquiries(@RequestParam(AppConstants.GET_ENQS_COURSE_NAME) String courseName,@RequestParam("enquiry") String enquiryStatus,@RequestParam("mode") String classMode) {
		
		EnquiryFilterForm form=new EnquiryFilterForm();
		form.setClassMode(classMode);
		form.setCourseName(courseName);
		form.setEnquiryStatus(enquiryStatus);
		
		Object studentEnquiries = this.enquiryStudentService.studentEnquiries(form);
		if(studentEnquiries instanceof String) {
			return studentEnquiries;
		}else {
			return studentEnquiries;
		}
		
	}
	
	@PatchMapping("/EnquiryUpdate")
	public String updateEnquiryByEquiryId(@RequestParam("EnquiryId") Integer eid,@RequestBody EnquiryForm enquiryForm) {
		
		String updateEnquiryByEnquiryId = this.enquiryStudentService.updateEnquiryByEnquiryId(eid, enquiryForm);
		
		return updateEnquiryByEnquiryId;
	}
	
}
