package com.arishi.lms_backend.controller;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.arishi.lms_backend.dto.ApiResponse;
import com.arishi.lms_backend.dto.AssignmentDetailDto;
import com.arishi.lms_backend.dto.AssignmentDto;
import com.arishi.lms_backend.service.AssignmentService;
import com.arishi.lms_backend.validator.PdfValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class AssignmentController {
	
	private final AssignmentService assignmentService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "/v1/course/{courseId}/assignment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApiResponse createAssignment(@ModelAttribute @Valid AssignmentDto assignmentDto, @PathVariable Long courseId) {
		PdfValidator.validatePDF(assignmentDto.getAssignmentSource());
		AssignmentDto savedAssignment = assignmentService.createAssignment(assignmentDto, courseId);
		return new ApiResponse(HttpStatus.CREATED.value(), List.of("Assignment created successfully"), savedAssignment);
	}

	@GetMapping(path = "/v1/assignment/{assignmentId}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<Resource> getAssignmentPdf(@PathVariable Long assignmentId) {
		Resource pdfResource = assignmentService.getAssignmentPdf(assignmentId);
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.inline()
						.filename("assignment-" + assignmentId + ".pdf")
						.build()
						.toString())
				.body(pdfResource);
	}

	@GetMapping("/v1/assignment/{assignmentId}/submission/{studentId}/pdf")
	public ResponseEntity<Resource> getSubmissionPdf(@PathVariable Long assignmentId, @PathVariable Long studentId) {
		Resource pdfResource = assignmentService.getSubmissionPdf(assignmentId, studentId);

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.inline()
						.filename("submission-" + assignmentId + "-" + studentId + ".pdf")
						.build()
						.toString())
				.body(pdfResource);
	}

	@GetMapping("/v1/assignment/{assignmentId}/submissions")
	public ApiResponse listSubmissions(@PathVariable Long assignmentId) {

		java.util.List<com.arishi.lms_backend.dto.AssignmentSubmissionSummaryDto> submissions = assignmentService
				.getSubmissionsForAssignment(assignmentId);

		if (submissions.isEmpty()) {
			return new ApiResponse(HttpStatus.OK.value(), List.of("No submissions found"), submissions);
		}

		return new ApiResponse(HttpStatus.OK.value(), List.of("Submissions fetched successfully"), submissions);
	}

	@GetMapping("/v1/assignment/{assignmentId}")
	public ApiResponse getAssignmentDetails(@PathVariable Long assignmentId) {
		AssignmentDetailDto assignment = assignmentService.getAssignmentDetails(assignmentId);
		return new ApiResponse(HttpStatus.OK.value(), List.of("Assignment fetched successfully"), assignment);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "/v1/assignment/{assignmentId}/submission", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApiResponse submitAssignment(@PathVariable Long assignmentId, @RequestPart("file") MultipartFile file) {
		PdfValidator.validatePDF(file);
		assignmentService.submitAssignment(assignmentId, file);
		return new ApiResponse(HttpStatus.CREATED.value(), List.of("Submission created successfully"), null);
	}
}
