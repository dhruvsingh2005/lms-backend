package com.arishi.lms_backend.entity;

import java.time.ZonedDateTime;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Department(String name) {
		super();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public ZonedDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(ZonedDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public ZonedDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(ZonedDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}

	@Column(nullable = false)
	private String name;

	@CreationTimestamp
	private ZonedDateTime createdAt;

	@UpdateTimestamp
	private ZonedDateTime updatedAt;

	private ZonedDateTime deletedAt;
}
