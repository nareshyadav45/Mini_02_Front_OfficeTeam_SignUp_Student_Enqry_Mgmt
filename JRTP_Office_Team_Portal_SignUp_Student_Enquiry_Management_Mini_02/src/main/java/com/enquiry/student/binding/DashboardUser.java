package com.enquiry.student.binding;

import lombok.Data;

@Data
public class DashboardUser {

	private Integer totatlEnquiries;
	private Integer enrolled;
	private Integer lostCount;
	public Integer getTotatlEnquiries() {
		return totatlEnquiries;
	}
	public void setTotatlEnquiries(Integer totatlEnquiries) {
		this.totatlEnquiries = totatlEnquiries;
	}
	public Integer getEnrolled() {
		return enrolled;
	}
	public void setEnrolled(Integer enrolled) {
		this.enrolled = enrolled;
	}
	public Integer getLostCount() {
		return lostCount;
	}
	public void setLostCount(Integer lostCount) {
		this.lostCount = lostCount;
	}
	public DashboardUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
