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
@Table(name="Nit_user_table")
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

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPhnNumber() {
		return phnNumber;
	}

	public void setPhnNumber(Integer phnNumber) {
		this.phnNumber = phnNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public List<StudentEnquiry> getEnquiries() {
		return enquiries;
	}

	public void setEnquiries(List<StudentEnquiry> enquiries) {
		this.enquiries = enquiries;
	}

	@Override
	public String toString() {
		return "UserEntity [uid=" + uid + ", userName=" + userName + ", email=" + email + ", phnNumber=" + phnNumber
				+ ", password=" + password + ", accountStatus=" + accountStatus + ", enquiries=" + enquiries + "]";
	}

}
