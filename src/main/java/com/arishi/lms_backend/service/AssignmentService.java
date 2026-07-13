package com.arishi.lms_backend.service;

import org.springframework.core.io.Resource;

import com.arishi.lms_backend.dto.AssignmentDto;

public interface AssignmentService {
	
	AssignmentDto createAssignment(AssignmentDto assignmentDto, Long courseId);

	Resource getAssignmentPdf(Long assignmentId);
}
