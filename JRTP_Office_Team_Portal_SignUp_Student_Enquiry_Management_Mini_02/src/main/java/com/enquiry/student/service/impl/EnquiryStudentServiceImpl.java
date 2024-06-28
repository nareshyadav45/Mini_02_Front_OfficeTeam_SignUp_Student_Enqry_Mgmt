package com.enquiry.student.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enquiry.student.binding.DashboardUser;
import com.enquiry.student.binding.EnquiryFilterForm;
import com.enquiry.student.binding.EnquiryForm;
import com.enquiry.student.binding.ListOfEnquiriesResponse;
import com.enquiry.student.entity.CourseEntity;
import com.enquiry.student.entity.EnquiryStatusEntity;
import com.enquiry.student.entity.StudentEnquiry;
import com.enquiry.student.entity.UserEntity;
import com.enquiry.student.repositories.CourseRepository;
import com.enquiry.student.repositories.EnquiryStatusRepository;
import com.enquiry.student.repositories.UserRepository;
import com.enquiry.student.service.EnquiryStudentService;
import com.enquiry.student.repositories.StudentEnquiryRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class EnquiryStudentServiceImpl implements EnquiryStudentService {

	@Autowired
	private StudentEnquiryRepository studenEnquiryRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private EnquiryStatusRepository enquiryStatusRepository;

	@Override
	public DashboardUser dashboardUser(Integer userId) {

		DashboardUser dashboardUser = new DashboardUser();

		int EnrolledEnquiries = 0;
		int LostEnquiries = 0;

		List<StudentEnquiry> listOfEnquiries = this.studenEnquiryRepo.findAllByUserId(userId);
		// if(listOfEnquiries.isEmpty()) {
		// return new DashboardUser();
		// }
		int size = listOfEnquiries.size();
		dashboardUser.setTotatlEnquiries(size);

		for (StudentEnquiry ste : listOfEnquiries) {
			if (ste.getEnquiryStatus().equals("Enrolled")) {
				EnrolledEnquiries++;
			} else if (ste.getEnquiryStatus().equals("Lost")) {
				LostEnquiries++;

			}

		}

		dashboardUser.setEnrolled(EnrolledEnquiries);
		dashboardUser.setLostCount(LostEnquiries);

		return dashboardUser;
	}

	@Override
	public List<String> getListOfCourses() {
		// TODO Get The List Of Courses Present In Database Table
		List<CourseEntity> listOfCourseEntity = this.courseRepository.findAll();
		List<String> listOfCourses = new ArrayList<>();

		for (CourseEntity courseEntity : listOfCourseEntity) {
			String courseName = courseEntity.getCourseName();
			listOfCourses.add(courseName);
		}

		// TODO : By custom query appraoch

		List<String> fetchListOfCourseNames = this.courseRepository.fetchListOfCourseNames();

		return fetchListOfCourseNames;
	}

	@Override
	public List<String> getListOfEnquiryStatus() {

		List<String> allStatuses = this.enquiryStatusRepository.findAllStatuses();

		// TODO : Different Approach

		List<EnquiryStatusEntity> all = this.enquiryStatusRepository.findAll();

		List<String> statuses = new ArrayList<>();

		for (EnquiryStatusEntity list : all) {
			String enquiryStatus = list.getEnquiryStatus();
			statuses.add(enquiryStatus);

		}

		return statuses;
	}

	@Override
	public boolean addStudentEnquiry(EnquiryForm enquiryForm) {
		// TODO : Take the data from form and copy it to entity

		StudentEnquiry studentEnquiry = new StudentEnquiry();

		// TODO : Take the User Id from session and set it entity
		Object attribute = httpSession.getAttribute("loggedInUserId");
		String string = attribute.toString();
		Integer userId = Integer.parseInt(string);

		// BeanUtils.copyProperties(enquiryForm, studentEnquiry);
		studentEnquiry.setStudentName(enquiryForm.getStudentName());
		studentEnquiry.setCourseName(enquiryForm.getCourseName());
		studentEnquiry.setEnquiryStatus(enquiryForm.getEnquiryStatus());
		studentEnquiry.setPhnNumber(enquiryForm.getPhnNumber());
		studentEnquiry.setClassMode(enquiryForm.getClassMode());

		// Integer user=3;
		UserEntity byId = this.userRepository.findById(userId).orElse(null);
		if (byId == null) {
			return false;
		}

		studentEnquiry.setUser(byId);
		studenEnquiryRepo.save(studentEnquiry);
		return true;
	}

	@Override
	public Object getStudentEnquiries(Integer UserId, EnquiryFilterForm enquiryFilterForm) {
		// TODO : fetch the list of enquiries based on UserId 
		
	     UserEntity byId = this.userRepository.findById(UserId).orElse(null);
	     if(byId==null) {
	    	 return "Invalid User Id";
	     }
		
		List<EnquiryForm> enquiryFormsBean=new ArrayList<>();
		
		EnquiryForm formBean;
		
		if(enquiryFilterForm.getClassMode().isEmpty()&&enquiryFilterForm.getCourseName().isEmpty()&&enquiryFilterForm.getEnquiryStatus().isEmpty()) {
			
			List<StudentEnquiry> allEnquiries = this.studenEnquiryRepo.findAllByUserId(UserId);
			for(StudentEnquiry studentEnquiry:allEnquiries) {
				formBean=new EnquiryForm();
				formBean.setStudentName(studentEnquiry.getStudentName());
				formBean.setCourseName(studentEnquiry.getCourseName());
				formBean.setEnquiryStatus(studentEnquiry.getEnquiryStatus());
				formBean.setClassMode(studentEnquiry.getClassMode());
				formBean.setPhnNumber(studentEnquiry.getPhnNumber());
				enquiryFormsBean.add(formBean);	
			}
		} else if(!enquiryFilterForm.getClassMode().isEmpty()&& !enquiryFilterForm.getCourseName().isEmpty()&& !enquiryFilterForm.getEnquiryStatus().isEmpty()) {
			List<StudentEnquiry> list = this.studenEnquiryRepo.findByCourseNameAndEnquiryStatusAndClassModeAndUserUid(enquiryFilterForm.getCourseName(), enquiryFilterForm.getEnquiryStatus(), enquiryFilterForm.getClassMode(),UserId);
			
			for(StudentEnquiry enquiry:list) {
				formBean=new EnquiryForm();
				formBean.setStudentName(enquiry.getStudentName());
                formBean.setCourseName(enquiry.getCourseName());	
                formBean.setEnquiryStatus(enquiry.getEnquiryStatus());
                formBean.setClassMode(enquiry.getClassMode());
                formBean.setPhnNumber(enquiry.getPhnNumber());
                enquiryFormsBean.add(formBean);
			}	
		}else if(!enquiryFilterForm.getCourseName().isEmpty()&& !enquiryFilterForm.getEnquiryStatus().isEmpty()) {
			List<StudentEnquiry> list = this.studenEnquiryRepo.findByCourseNameAndEnquiryStatusAndUserUid(enquiryFilterForm.getCourseName(), enquiryFilterForm.getEnquiryStatus(),UserId);
			for(StudentEnquiry enquiry:list) {
				formBean=new EnquiryForm();
				formBean.setStudentName(enquiry.getStudentName());
                formBean.setCourseName(enquiry.getCourseName());	
                formBean.setEnquiryStatus(enquiry.getEnquiryStatus());
                formBean.setClassMode(enquiry.getClassMode());
                formBean.setPhnNumber(enquiry.getPhnNumber());
                enquiryFormsBean.add(formBean);
			}		
		
		}else if(!enquiryFilterForm.getCourseName().isEmpty()&& !enquiryFilterForm.getClassMode().isEmpty()) {
			
			List<StudentEnquiry> list = this.studenEnquiryRepo.findByCourseNameAndClassModeAndUserUid(enquiryFilterForm.getCourseName(), enquiryFilterForm.getClassMode(),UserId);
			for(StudentEnquiry enquiry:list) {
				formBean=new EnquiryForm();
				formBean.setStudentName(enquiry.getStudentName());
                formBean.setCourseName(enquiry.getCourseName());	
                formBean.setEnquiryStatus(enquiry.getEnquiryStatus());
                formBean.setClassMode(enquiry.getClassMode());
                formBean.setPhnNumber(enquiry.getPhnNumber());
                enquiryFormsBean.add(formBean);
			}	
		}else if(!enquiryFilterForm.getEnquiryStatus().isEmpty()&& !enquiryFilterForm.getClassMode().isEmpty()) {
			     List<StudentEnquiry> list = this.studenEnquiryRepo.findByEnquiryStatusAndClassModeAndUserUid(enquiryFilterForm.getEnquiryStatus(), enquiryFilterForm.getClassMode(),UserId);
			     for(StudentEnquiry enquiry:list) {
						formBean=new EnquiryForm();
						formBean.setStudentName(enquiry.getStudentName());
		                formBean.setCourseName(enquiry.getCourseName());	
		                formBean.setEnquiryStatus(enquiry.getEnquiryStatus());
		                formBean.setClassMode(enquiry.getClassMode());
		                formBean.setPhnNumber(enquiry.getPhnNumber());
		                enquiryFormsBean.add(formBean);
					}	
		}else if(!enquiryFilterForm.getCourseName().isEmpty()) {
			List<StudentEnquiry> list = this.studenEnquiryRepo.findByCourseNameAndUserUid(enquiryFilterForm.getCourseName(),UserId);
			for(StudentEnquiry enquiry:list) {
				formBean=new EnquiryForm();
				formBean.setStudentName(enquiry.getStudentName());
                formBean.setCourseName(enquiry.getCourseName());	
                formBean.setEnquiryStatus(enquiry.getEnquiryStatus());
                formBean.setClassMode(enquiry.getClassMode());
                formBean.setPhnNumber(enquiry.getPhnNumber());
                enquiryFormsBean.add(formBean);
			}  
		}else if(!enquiryFilterForm.getEnquiryStatus().isEmpty()) {
			List<StudentEnquiry> list = this.studenEnquiryRepo.findByEnquiryStatusAndUserUid(enquiryFilterForm.getEnquiryStatus(),UserId);
			for(StudentEnquiry enquiry:list) {
				formBean=new EnquiryForm();
				formBean.setStudentName(enquiry.getStudentName());
                formBean.setCourseName(enquiry.getCourseName());	
                formBean.setEnquiryStatus(enquiry.getEnquiryStatus());
                formBean.setClassMode(enquiry.getClassMode());
                formBean.setPhnNumber(enquiry.getPhnNumber());
                enquiryFormsBean.add(formBean);
			}
			
		}else if(!enquiryFilterForm.getClassMode().isEmpty()) {
			List<StudentEnquiry> list = this.studenEnquiryRepo.findByClassModeAndUserUid(enquiryFilterForm.getClassMode(),UserId);
			for(StudentEnquiry enquiry:list) {
				formBean=new EnquiryForm();
				formBean.setStudentName(enquiry.getStudentName());
                formBean.setCourseName(enquiry.getCourseName());	
                formBean.setEnquiryStatus(enquiry.getEnquiryStatus());
                formBean.setClassMode(enquiry.getClassMode());
                formBean.setPhnNumber(enquiry.getPhnNumber());
                enquiryFormsBean.add(formBean);
			}
			
		}
		ListOfEnquiriesResponse listOfEnquiriesResponse=new ListOfEnquiriesResponse();
		listOfEnquiriesResponse.setEnquiriesResponses(enquiryFormsBean);
	   
		
		return listOfEnquiriesResponse;
	}

	@Override
	public EnquiryForm getEnquiryByEnquiryId(Integer eid) {
		// TODO Auto-generated method stub
		return null;
	}

}
