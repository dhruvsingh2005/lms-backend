package com.arishi.lms_backend.entity;
import java.time.ZonedDateTime;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Student  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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
	
	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private ZonedDateTime created_at;
	 
	  @Column(nullable = false)
	  @UpdateTimestamp
	private ZonedDateTime updated_at;
	  
	@Column(nullable = false)
	private ZonedDateTime deleted_at;
	
 
}
