package com.enquiry.student.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enquiry.student.entity.EnquiryStatusEntity;

public interface Enquiry_Status_Repository extends JpaRepository<EnquiryStatusEntity, Integer> {

}
