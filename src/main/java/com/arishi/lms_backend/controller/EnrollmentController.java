package com.arishi.lms_backend.controller;

import com.arishi.lms_backend.dto.ApiResponse;
import com.arishi.lms_backend.dto.response.EnrollmentResponseDTO;
import com.arishi.lms_backend.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/{courseId}/enrollment")
    public ResponseEntity<ApiResponse> enrollInCourse(@PathVariable("courseId") Long courseId) {

        EnrollmentResponseDTO enrollmentResponse = enrollmentService.enrollInCourse(courseId);

        ApiResponse apiResponse = new ApiResponse(HttpStatus.CREATED.value(), List.of("Enrollment successful"), enrollmentResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}