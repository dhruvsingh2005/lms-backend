package com.arishi.lms_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.arishi.lms_backend.dto.ApiResponse;
import com.arishi.lms_backend.dto.CourseDTO;
import com.arishi.lms_backend.service.CourseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class CourseController {

	private final CourseService courseService;

	@PostMapping("/v1/course")
	public ResponseEntity<ApiResponse> createCourse(@Valid @RequestBody CourseDTO request) 
	{
		CourseDTO response = courseService.createCourse(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse(HttpStatus.CREATED.value(), List.of("Course created successfully"), response));
	}

	@GetMapping("/public/v1/courses")
   	public ResponseEntity<ApiResponse> getAvailableCourses(@RequestParam(defaultValue = "available") String status) {
	    List<CourseDTO> courses = courseService.getAvailableCourses(status);
	    ApiResponse response;
	    if (courses.isEmpty()) {
	        response = new ApiResponse(
	                HttpStatus.OK.value(),
	                List.of("No  courses found."),
	                courses
	        ); }
	    else {
	        response = new ApiResponse(
	                HttpStatus.OK.value(),
	                List.of("Courses fetched successfully."),
	                courses  ); }

	    return ResponseEntity.ok(response);
	}

	@GetMapping("/public/v1/course/{courseId}")
	public ResponseEntity<ApiResponse> getCourseById(@PathVariable Long courseId) {
		CourseDTO course = courseService.getCourseById(courseId);
		ApiResponse response = new ApiResponse(HttpStatus.OK.value(), List.of("Course fetched successfully."), course);
		return ResponseEntity.ok(response);
	}

}
