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
public class Department {
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long id;
	   @Column(nullable = false)
	   private  String name;
	   
	   
	   @CreationTimestamp
	    private ZonedDateTime createdAt;

	    @UpdateTimestamp
	    private ZonedDateTime updatedAt;

	    private ZonedDateTime deletedAt;
	
	

}
