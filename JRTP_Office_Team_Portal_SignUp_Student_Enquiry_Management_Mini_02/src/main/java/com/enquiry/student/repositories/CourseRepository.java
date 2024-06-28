package com.enquiry.student.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.enquiry.student.entity.CourseEntity;

public interface CourseRepository extends JpaRepository<CourseEntity, Integer>{
	
	@Query("SELECT C.courseName FROM CourseEntity AS C")
	public List<String> fetchListOfCourseNames();
	
}
