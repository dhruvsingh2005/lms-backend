package com.arishi.lms_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Student  extends BaseEntity {
	
	@Column(nullable = false )
	private String firstName;
	
	@Column(nullable = true )
	private String lastName;
	
	@Column(nullable = false )
	private String passwordHash;
	
	@Column(nullable = false ,unique=true)
	private String email;
	
	@Column(nullable = false ,unique=true)
	private String mobileNumber;
}
