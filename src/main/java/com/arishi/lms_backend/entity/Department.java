package com.arishi.lms_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Department  extends BaseEntity{

	@Column(nullable = false)
	private String name;

}
