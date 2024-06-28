package com.enquiry.student.runners;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.enquiry.student.entity.CourseEntity;
import com.enquiry.student.entity.EnquiryStatusEntity;
import com.enquiry.student.repositories.CourseRepository;
import com.enquiry.student.repositories.EnquiryStatusRepository;

@Component
public class DataLoader implements ApplicationRunner{
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private EnquiryStatusRepository enquiryStatusRepository;
	
	

	@Override
	public void run(ApplicationArguments args) throws Exception {
	
		courseRepository.deleteAll();
		
		CourseEntity courseEntity =new CourseEntity();
		courseEntity.setCourseName("Java");
		
		CourseEntity courseEntity2 =new CourseEntity();
		courseEntity2.setCourseName("Python");
		
		CourseEntity courseEntity3 =new CourseEntity();
		courseEntity3.setCourseName(".Net");
		
		
		List<CourseEntity> asList = Arrays.asList(courseEntity,courseEntity2,courseEntity3);
		
		courseRepository.saveAll(asList);
		
		
		
		
		enquiryStatusRepository.deleteAll();
		
		EnquiryStatusEntity statusEntity=new EnquiryStatusEntity();
		statusEntity.setEnquiryStatus("New");
		
		EnquiryStatusEntity statusEntity2=new EnquiryStatusEntity();
		statusEntity2.setEnquiryStatus("Enrolled");
		
		EnquiryStatusEntity statusEntity3=new EnquiryStatusEntity();
		statusEntity3.setEnquiryStatus("Lost");
		
		List<EnquiryStatusEntity> asList2 = Arrays.asList(statusEntity,statusEntity2,statusEntity3);
		
		this.enquiryStatusRepository.saveAll(asList2);
		
	}

}
