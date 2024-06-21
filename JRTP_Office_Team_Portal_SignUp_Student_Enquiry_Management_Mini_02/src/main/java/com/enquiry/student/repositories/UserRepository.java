package com.enquiry.student.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enquiry.student.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	
	public UserEntity findByEmail(String email);

}
