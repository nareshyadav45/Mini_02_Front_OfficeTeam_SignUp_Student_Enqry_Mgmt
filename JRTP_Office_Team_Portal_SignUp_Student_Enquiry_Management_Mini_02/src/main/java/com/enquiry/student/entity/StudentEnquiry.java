package com.enquiry.student.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="Nit_Student_Enquiry")
@Data
public class StudentEnquiry {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Integer sid;
	
	@ManyToOne()
	@JoinColumn(name="user_id")
	private UserEntity user;
	
	private String studentName;
	
	//@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	//@JoinColumn(name="course_id",referencedColumnName = "cid")
	//private CourseEntity courseName;
	
	//@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	//@JoinColumn(name="Enquiry_Status_id",referencedColumnName = "eid")
	//private EnquiryStatusEntity enquiry_Status;
	
	private String classMode;
	
	private Integer phnNumber;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	

}
