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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.arishi.lms_backend.dto.ApiResponse;
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
}
