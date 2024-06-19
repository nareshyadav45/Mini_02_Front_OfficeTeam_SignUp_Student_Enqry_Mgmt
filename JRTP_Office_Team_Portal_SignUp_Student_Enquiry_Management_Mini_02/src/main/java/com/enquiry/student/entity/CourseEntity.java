package com.enquiry.student.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="Nit_Courses")
@Data
public class CourseEntity {
	
	@Id
	private Integer cid;
	private String courseName;
	
	

}
