package com.enquiry.student.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enquiry.student.entity.CourseEntity;

public interface CourseRepository extends JpaRepository<CourseEntity, Integer>{

}
