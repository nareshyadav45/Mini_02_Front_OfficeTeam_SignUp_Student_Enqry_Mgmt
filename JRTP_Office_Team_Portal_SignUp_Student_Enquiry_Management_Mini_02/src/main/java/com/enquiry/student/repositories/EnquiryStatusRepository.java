package com.enquiry.student.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.enquiry.student.entity.EnquiryStatusEntity;

public interface EnquiryStatusRepository extends JpaRepository<EnquiryStatusEntity, Integer> {
	
	@Query("select s.enquiryStatus from EnquiryStatusEntity as s")
	public List<String> findAllStatuses();
	

}
