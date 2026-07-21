package com.arishi.lms_backend.controller;

import com.arishi.lms_backend.dto.ApiResponse;
import com.arishi.lms_backend.dto.InstructorDTO;
import com.arishi.lms_backend.dto.request.CourseRequestDTO;
import com.arishi.lms_backend.dto.response.CourseResponseDTO;
import com.arishi.lms_backend.service.InstructorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arishi.lms_backend.service.CourseService;
import com.arishi.lms_backend.service.AssignmentService;
import com.arishi.lms_backend.dto.AssignmentSummaryDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InstructorController {

	private final CourseService courseService;

	private final InstructorService instructorService;

	private final AssignmentService assignmentService;

	@PostMapping("/public/v1/instructor")
	public ResponseEntity<ApiResponse> createInstructor(@Valid @RequestBody InstructorDTO request) {

		InstructorDTO response = instructorService.createInstructor(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(
				new ApiResponse(HttpStatus.CREATED.value(), List.of("Instructor created successfully"), response));
	}

	@GetMapping("/instructor/v1/courses")
	public ResponseEntity<ApiResponse> getInstructorCourses(
	        @RequestParam(defaultValue = "available") String status) {

	    List<CourseResponseDTO> courses = courseService.getInstructorCourses(status);

	    ApiResponse response;

	    if (courses.isEmpty()) {
	        response = new ApiResponse(
	                HttpStatus.OK.value(),
	                List.of("No " + status + " courses found for this instructor."),
	                courses
	        );
	    } else {
	        response = new ApiResponse(
	                HttpStatus.OK.value(),
	                List.of("Courses fetched successfully."),
	                courses
	        );
	    }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(), List.of("Instructor created successfully"), response));
    }

    @GetMapping("/v1/Instructor/profile")
    public ResponseEntity<ApiResponse> getInstructorProfile() {

        InstructorDTO instructorDTO = instructorService.getInstructorProfile();

        ApiResponse response = new ApiResponse(200, List.of("Instructor profile fetched successfully"), instructorDTO);

        return ResponseEntity.ok(response);
    }

	@GetMapping("/v1/instructor/course/{courseId}/assignments")
	public ResponseEntity<ApiResponse> getCourseAssignmentsByStatus(@PathVariable Long courseId, @RequestParam String status) {

		List<AssignmentSummaryDto> assignments = assignmentService.getAssignmentsForCourseByStatus(courseId,status);

		ApiResponse response;
		if (assignments.isEmpty()) {
			response = new ApiResponse(HttpStatus.OK.value(), List.of("No " + status + " assignments found."), assignments);
		} else {
			response = new ApiResponse(HttpStatus.OK.value(), List.of("Assignments fetched successfully"), assignments);
		}

		return ResponseEntity.ok(response);
	}
}