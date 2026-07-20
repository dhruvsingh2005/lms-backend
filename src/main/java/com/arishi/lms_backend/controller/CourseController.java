package com.arishi.lms_backend.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.arishi.lms_backend.dto.ApiResponse;
import com.arishi.lms_backend.dto.CourseContentResponseDTO;
import com.arishi.lms_backend.dto.CourseResponseDTO;
import com.arishi.lms_backend.dto.request.CourseRequestDTO;
import com.arishi.lms_backend.service.CourseService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/v1/course")
    public ResponseEntity<ApiResponse> createCourse(
            @Valid @RequestBody CourseRequestDTO request) {

        CourseResponseDTO response = courseService.createCourse(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(
                        HttpStatus.CREATED.value(),
                        List.of("Course created successfully."),
                        response
                ));
    }

    @GetMapping("/public/v1/courses")
    public ResponseEntity<ApiResponse> getAvailableCourses(
            @RequestParam(defaultValue = "available") String status) {

        List<CourseResponseDTO> courses =
                courseService.getAvailableCourses(status);

        ApiResponse response;

        if (courses.isEmpty()) {

            response = new ApiResponse(
                    HttpStatus.OK.value(),
                    List.of("No courses found."),
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

    @GetMapping("/public/v1/course/{courseId}")
    public ResponseEntity<ApiResponse> getCourseById(
            @PathVariable Long courseId) {

        CourseResponseDTO course =
                courseService.getCourseById(courseId);

        ApiResponse response = new ApiResponse(
                HttpStatus.OK.value(),
                List.of("Course fetched successfully."),
                course
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/v1/course/{courseId}/content")
    public ResponseEntity<ApiResponse> getCourseContent(
            @PathVariable Long courseId) {

        CourseContentResponseDTO content =
                courseService.getCourseContent(courseId);

        ApiResponse response = new ApiResponse(
                HttpStatus.OK.value(),
                List.of("Course content fetched successfully."),
                content
        );

        return ResponseEntity.ok(response);
    }
}