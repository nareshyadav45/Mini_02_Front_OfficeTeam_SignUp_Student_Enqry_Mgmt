package com.enquiry.student.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="Nit_Enquiry_Status")
@Data
public class EnquiryStatusEntity {
	
	@Id
	private Integer eid;
	private String enquiryStatus;
	
	
}
