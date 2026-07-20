package com.arishi.lms_backend.service;

import org.springframework.core.io.Resource;

import com.arishi.lms_backend.dto.AssignmentDetailDto;
import com.arishi.lms_backend.dto.AssignmentDto;
import com.arishi.lms_backend.dto.AssignmentSummaryDto;
import java.util.List;

public interface AssignmentService {
	
	AssignmentDto createAssignment(AssignmentDto assignmentDto, Long courseId);

	List<AssignmentSummaryDto> getAssignmentsForCourseByStatus(Long courseId, String status);
    
	void submitAssignment(Long assignmentId, org.springframework.web.multipart.MultipartFile file);
    
	org.springframework.core.io.Resource getSubmissionPdf(Long assignmentId, Long studentId);

	java.util.List<com.arishi.lms_backend.dto.AssignmentSubmissionSummaryDto> getSubmissionsForAssignment(Long assignmentId);

	Resource getAssignmentPdf(Long assignmentId);

	AssignmentDetailDto getAssignmentDetails(Long assignmentId);
}
