package com.arishi.lms_backend.controller;

import com.arishi.lms_backend.dto.ApiResponse;
import com.arishi.lms_backend.dto.response.EnrollmentResponseDTO;
import com.arishi.lms_backend.dto.response.StudentEnrollmentCourseResponseDTO;
import com.arishi.lms_backend.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/course/{courseId}/enrollment")
    public ResponseEntity<ApiResponse> enrollInCourse(@PathVariable("courseId") Long courseId) {

        EnrollmentResponseDTO enrollmentResponse = enrollmentService.enrollInCourse(courseId);

        ApiResponse apiResponse = new ApiResponse(HttpStatus.CREATED.value(), List.of("Enrollment successful"), enrollmentResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    
    @GetMapping("student/course/enrollments")
    public ResponseEntity<ApiResponse> getStudentEnrollments(
            @RequestParam(defaultValue = "ongoing") String status) {

        List<StudentEnrollmentCourseResponseDTO> enrollments =
                enrollmentService.getStudentEnrollments(status);

        ApiResponse response;

        if (enrollments.isEmpty()) {

            response = new ApiResponse(
                    HttpStatus.OK.value(), List.of("No " + status + " enrollments found."),enrollments  );

        } else {response = new ApiResponse( HttpStatus.OK.value(), List.of("Enrollments fetched successfully."), enrollments);
        }
        return ResponseEntity.ok(response);
    }
}