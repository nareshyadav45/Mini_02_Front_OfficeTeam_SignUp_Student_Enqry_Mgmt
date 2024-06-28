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
	private String courseName;
	
	//@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	//@JoinColumn(name="Enquiry_Status_id",referencedColumnName = "eid")
	//private EnquiryStatusEntity enquiry_Status;
	
	private String enquiryStatus;
	
	private String classMode;
	
	private Integer phnNumber;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@UpdateTimestamp
	private LocalDate updatedDate;

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getEnquiryStatus() {
		return enquiryStatus;
	}

	public void setEnquiryStatus(String enquiryStatus) {
		this.enquiryStatus = enquiryStatus;
	}

	public String getClassMode() {
		return classMode;
	}

	public void setClassMode(String classMode) {
		this.classMode = classMode;
	}

	public Integer getPhnNumber() {
		return phnNumber;
	}

	public void setPhnNumber(Integer phnNumber) {
		this.phnNumber = phnNumber;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	
	

}
