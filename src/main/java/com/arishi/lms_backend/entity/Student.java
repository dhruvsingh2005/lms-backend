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
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student( String firstName, String lastName, String passwordHash, String email, String mobileNumber) {
		super();
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.passwordHash = passwordHash;
		this.email = email;
		this.mobileNumber = mobileNumber;
		
	}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public ZonedDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(ZonedDateTime created_at) {
		this.created_at = created_at;
	}

	public ZonedDateTime getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(ZonedDateTime updated_at) {
		this.updated_at = updated_at;
	}

	public ZonedDateTime getDeleted_at() {
		return deleted_at;
	}

	public void setDeleted_at(ZonedDateTime deleted_at) {
		this.deleted_at = deleted_at;
	}
	
 
}
