package com.arishi.lms_backend.controller;

import com.arishi.lms_backend.dto.ApiResponse;
import com.arishi.lms_backend.dto.InstructorDTO;
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
import com.arishi.lms_backend.dto.CourseDTO;
import com.arishi.lms_backend.service.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InstructorController {

	private final CourseService courseService;

	private final InstructorService instructorService;

	@PostMapping("/public/v1/instructor")
	public ResponseEntity<ApiResponse> createInstructor(@Valid @RequestBody InstructorDTO request) {

		InstructorDTO response = instructorService.createInstructor(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(
				new ApiResponse(HttpStatus.CREATED.value(), List.of("Instructor created successfully"), response));
	}

	@GetMapping("/instructor/v1/courses")
	public ResponseEntity<ApiResponse> getInstructorCourses(
	        @RequestParam(defaultValue = "available") String status) {

	    List<CourseDTO> courses = courseService.getInstructorCourses(status);

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
	    return ResponseEntity.ok(response);
	}

}