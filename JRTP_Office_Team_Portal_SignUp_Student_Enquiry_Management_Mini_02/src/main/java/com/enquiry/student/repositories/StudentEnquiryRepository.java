package com.enquiry.student.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.enquiry.student.entity.StudentEnquiry;

@Repository
public interface StudentEnquiryRepository extends JpaRepository<StudentEnquiry, Integer> {
	
	@Query("select s from StudentEnquiry as s where s.user.uid = :userId")
	public List<StudentEnquiry> findAllByUserId(@Param("userId") Integer userid);
	
	public List<StudentEnquiry> findByCourseNameAndUserUid(String courseName,Integer uid);
	
	public List<StudentEnquiry> findByEnquiryStatusAndUserUid(String enquiry,Integer uid);
	
	public List<StudentEnquiry> findByClassModeAndUserUid(String classMode,Integer uid);
	
	public List<StudentEnquiry> findByCourseNameAndEnquiryStatusAndClassModeAndUserUid(String corseName,String enquiryStatus,String classmode,Integer uid);
	
	public List<StudentEnquiry> findByCourseNameAndEnquiryStatusAndUserUid(String corseName,String enquiryStatus,Integer uid);
	
	public List<StudentEnquiry> findByCourseNameAndClassModeAndUserUid(String courseName,String mode,Integer uid);
	
	public List<StudentEnquiry> findByEnquiryStatusAndClassModeAndUserUid(String status,String mode,Integer uid);
	

}
