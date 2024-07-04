package com.enquiry.student.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enquiry.student.binding.DashboardUser;
import com.enquiry.student.binding.EnquiryFilterForm;
import com.enquiry.student.binding.EnquiryForm;
import com.enquiry.student.binding.ListOfEnquiriesResponse;
import com.enquiry.student.constants.AppConstants;
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
			if (ste.getEnquiryStatus().equals(AppConstants.ENQUIRY_STATUS_ENROLLED)) {
				EnrolledEnquiries++;
			} else if (ste.getEnquiryStatus().equals(AppConstants.ENQUIRY_STATUS_LOST)) {
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
		Object attribute = httpSession.getAttribute(AppConstants.LOGGED_IN_USER_ID);
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
		if (byId == null) {
			return AppConstants.INVALID_USER_ID;
		}

		List<EnquiryForm> enquiryFormsBean = new ArrayList<>();

		EnquiryForm formBean;

		if (enquiryFilterForm.getClassMode().isEmpty() && enquiryFilterForm.getCourseName().isEmpty()
				&& enquiryFilterForm.getEnquiryStatus().isEmpty()) {
			List<StudentEnquiry> allEnquiries = this.studenEnquiryRepo.findAllByUserId(UserId);
			for (StudentEnquiry studentEnquiry : allEnquiries) {
				formBean = new EnquiryForm();
				formBean.setStudentName(studentEnquiry.getStudentName());
				formBean.setCourseName(studentEnquiry.getCourseName());
				formBean.setEnquiryStatus(studentEnquiry.getEnquiryStatus());
				formBean.setClassMode(studentEnquiry.getClassMode());
				formBean.setPhnNumber(studentEnquiry.getPhnNumber());
				enquiryFormsBean.add(formBean);
			}
		} else if (!enquiryFilterForm.getClassMode().isEmpty() && !enquiryFilterForm.getCourseName().isEmpty()
				&& !enquiryFilterForm.getEnquiryStatus().isEmpty()) {
			List<StudentEnquiry> list = this.studenEnquiryRepo.findByCourseNameAndEnquiryStatusAndClassModeAndUserUid(
					enquiryFilterForm.getCourseName(), enquiryFilterForm.getEnquiryStatus(),
					enquiryFilterForm.getClassMode(), UserId);

			for (StudentEnquiry enquiry : list) {
				formBean = new EnquiryForm();
				formBean.setStudentName(enquiry.getStudentName());
				formBean.setCourseName(enquiry.getCourseName());
				formBean.setEnquiryStatus(enquiry.getEnquiryStatus());
				formBean.setClassMode(enquiry.getClassMode());
				formBean.setPhnNumber(enquiry.getPhnNumber());
				enquiryFormsBean.add(formBean);
			}
		} else if (!enquiryFilterForm.getCourseName().isEmpty() && !enquiryFilterForm.getEnquiryStatus().isEmpty()) {
			List<StudentEnquiry> list = this.studenEnquiryRepo.findByCourseNameAndEnquiryStatusAndUserUid(
					enquiryFilterForm.getCourseName(), enquiryFilterForm.getEnquiryStatus(), UserId);
			for (StudentEnquiry enquiry : list) {
				formBean = new EnquiryForm();
				formBean.setStudentName(enquiry.getStudentName());
				formBean.setCourseName(enquiry.getCourseName());
				formBean.setEnquiryStatus(enquiry.getEnquiryStatus());
				formBean.setClassMode(enquiry.getClassMode());
				formBean.setPhnNumber(enquiry.getPhnNumber());
				enquiryFormsBean.add(formBean);
			}

		} else if (!enquiryFilterForm.getCourseName().isEmpty() && !enquiryFilterForm.getClassMode().isEmpty()) {

			List<StudentEnquiry> list = this.studenEnquiryRepo.findByCourseNameAndClassModeAndUserUid(
					enquiryFilterForm.getCourseName(), enquiryFilterForm.getClassMode(), UserId);
			for (StudentEnquiry enquiry : list) {
				formBean = new EnquiryForm();
				formBean.setStudentName(enquiry.getStudentName());
				formBean.setCourseName(enquiry.getCourseName());
				formBean.setEnquiryStatus(enquiry.getEnquiryStatus());
				formBean.setClassMode(enquiry.getClassMode());
				formBean.setPhnNumber(enquiry.getPhnNumber());
				enquiryFormsBean.add(formBean);
			}
		} else if (!enquiryFilterForm.getEnquiryStatus().isEmpty() && !enquiryFilterForm.getClassMode().isEmpty()) {
			List<StudentEnquiry> list = this.studenEnquiryRepo.findByEnquiryStatusAndClassModeAndUserUid(
					enquiryFilterForm.getEnquiryStatus(), enquiryFilterForm.getClassMode(), UserId);
			for (StudentEnquiry enquiry : list) {
				formBean = new EnquiryForm();
				formBean.setStudentName(enquiry.getStudentName());
				formBean.setCourseName(enquiry.getCourseName());
				formBean.setEnquiryStatus(enquiry.getEnquiryStatus());
				formBean.setClassMode(enquiry.getClassMode());
				formBean.setPhnNumber(enquiry.getPhnNumber());
				enquiryFormsBean.add(formBean);
			}
		} else if (!enquiryFilterForm.getCourseName().isEmpty()) {
			List<StudentEnquiry> list = this.studenEnquiryRepo
					.findByCourseNameAndUserUid(enquiryFilterForm.getCourseName(), UserId);
			for (StudentEnquiry enquiry : list) {
				formBean = new EnquiryForm();
				formBean.setStudentName(enquiry.getStudentName());
				formBean.setCourseName(enquiry.getCourseName());
				formBean.setEnquiryStatus(enquiry.getEnquiryStatus());
				formBean.setClassMode(enquiry.getClassMode());
				formBean.setPhnNumber(enquiry.getPhnNumber());
				enquiryFormsBean.add(formBean);
			}
		} else if (!enquiryFilterForm.getEnquiryStatus().isEmpty()) {
			List<StudentEnquiry> list = this.studenEnquiryRepo
					.findByEnquiryStatusAndUserUid(enquiryFilterForm.getEnquiryStatus(), UserId);
			for (StudentEnquiry enquiry : list) {
				formBean = new EnquiryForm();
				formBean.setStudentName(enquiry.getStudentName());
				formBean.setCourseName(enquiry.getCourseName());
				formBean.setEnquiryStatus(enquiry.getEnquiryStatus());
				formBean.setClassMode(enquiry.getClassMode());
				formBean.setPhnNumber(enquiry.getPhnNumber());
				enquiryFormsBean.add(formBean);
			}

		} else if (!enquiryFilterForm.getClassMode().isEmpty()) {
			List<StudentEnquiry> list = this.studenEnquiryRepo
					.findByClassModeAndUserUid(enquiryFilterForm.getClassMode(), UserId);
			for (StudentEnquiry enquiry : list) {
				formBean = new EnquiryForm();
				formBean.setStudentName(enquiry.getStudentName());
				formBean.setCourseName(enquiry.getCourseName());
				formBean.setEnquiryStatus(enquiry.getEnquiryStatus());
				formBean.setClassMode(enquiry.getClassMode());
				formBean.setPhnNumber(enquiry.getPhnNumber());
				enquiryFormsBean.add(formBean);
			}

		}
		ListOfEnquiriesResponse listOfEnquiriesResponse = new ListOfEnquiriesResponse();
		listOfEnquiriesResponse.setEnquiriesResponses(enquiryFormsBean);

		return listOfEnquiriesResponse;
	}

	@Override
	public Object studentEnquiries(EnquiryFilterForm enquiryFilterForm) {
		// TODO Get the User Id from Session And for that id get the user
		Object attribute = this.httpSession.getAttribute(AppConstants.LOGGED_IN_USER_ID);
		String string = attribute.toString();
		int userId = Integer.parseInt(string);
		Optional<UserEntity> byId = this.userRepository.findById(userId);

		 List<StudentEnquiry> list=new ArrayList<>();

		if (byId.isPresent()) {
			UserEntity userEntity = byId.get();
			if ((enquiryFilterForm.getCourseName() == null && enquiryFilterForm.getCourseName().equals(AppConstants.STR_EMPTY_CHECK))
					&& (enquiryFilterForm.getEnquiryStatus() == null && enquiryFilterForm.getEnquiryStatus().equals(AppConstants.STR_EMPTY_CHECK))
					&& (enquiryFilterForm.getClassMode() == null && enquiryFilterForm.getClassMode().equals(AppConstants.STR_EMPTY_CHECK))) {
				List<StudentEnquiry> enquiries = userEntity.getEnquiries();
				return enquiries;

			} else if (enquiryFilterForm.getCourseName() != null && !enquiryFilterForm.getCourseName().equals(AppConstants.STR_EMPTY_CHECK)) {
				List<StudentEnquiry> enquiries = userEntity.getEnquiries();
				enquiries = enquiries.stream().filter(c -> c.getCourseName().equals(enquiryFilterForm.getCourseName()))
						.collect(Collectors.toList());
				return enquiries;
			} else if (enquiryFilterForm.getEnquiryStatus().equals(AppConstants.STR_EMPTY_CHECK)
					&& enquiryFilterForm.getEnquiryStatus() == null) {

				List<StudentEnquiry> enquiries = userEntity.getEnquiries();
				enquiries = enquiries.stream()
						.filter(e -> e.getEnquiryStatus().equals(enquiryFilterForm.getEnquiryStatus()))
						.collect(Collectors.toList());
				return enquiries;
			} else if (enquiryFilterForm.getClassMode().equals(AppConstants.STR_EMPTY_CHECK) && enquiryFilterForm.getClassMode() == null) {

				List<StudentEnquiry> enquiries = userEntity.getEnquiries();

				enquiries = enquiries.stream().filter(e -> e.getClassMode().equals(enquiryFilterForm.getClassMode()))
						.collect(Collectors.toList());
				return enquiries;
			}

		}
		return AppConstants.INVALID_USER_ID;

	}

	@Override
	public String updateEnquiryByEnquiryId(Integer enquiryId, EnquiryForm enquiryFormUpdated) {
		// TODO With Given Enquiry Id we need to find enquiry recored and update as user given
		String response =null;
		
		StudentEnquiry stuentEnquiryEntity = this.studenEnquiryRepo.findById(enquiryId).orElse(null);
		//String courseName = stuentEnquiryEntity.getCourseName();
		
		EnquiryForm form=new EnquiryForm();
		
		BeanUtils.copyProperties(enquiryFormUpdated, form);
		
		if(stuentEnquiryEntity==null) {
			  response =AppConstants.STUDENT_INVALID_ENQUIRY_ID;
			
		}else {
			if(enquiryFormUpdated.getCourseName()==null) {
				form.setCourseName(stuentEnquiryEntity.getCourseName());
			} 
			if(enquiryFormUpdated.getClassMode()==null) {
				form.setClassMode(stuentEnquiryEntity.getClassMode());
			}
			if(enquiryFormUpdated.getEnquiryStatus()==null) {
				form.setEnquiryStatus(stuentEnquiryEntity.getClassMode());
			}
			if(enquiryFormUpdated.getPhnNumber()==null) {
				form.setPhnNumber(stuentEnquiryEntity.getPhnNumber());	
			}
			if(enquiryFormUpdated.getStudentName()==null) {
				form.setStudentName(stuentEnquiryEntity.getStudentName());
			}
			
			BeanUtils.copyProperties(form, stuentEnquiryEntity);
			
			StudentEnquiry save = this.studenEnquiryRepo.save(stuentEnquiryEntity);
			if(save!=null) {
				 response= AppConstants.STUDENT_ENQUIRY_SUCCESSFULLY_UPDATE_MSG;
			}
		}
		
		
		return response;
		
		 
	}

	@Override
	public EnquiryForm getEnquiryByEnquiryId(Integer eid) {
		// TODO Auto-generated method stub
		return null;
	}

}
