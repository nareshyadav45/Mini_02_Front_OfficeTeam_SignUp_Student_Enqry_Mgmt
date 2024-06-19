package com.enquiry.student.entity;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="user_table")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Integer uid;
	
	private String userName;
	
    private String email;
    
    private Integer phnNumber;
    
    private String password;
    
    private String accountStatus;
    
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<StudentEnquiry> enquiries;
    
	
    
}
