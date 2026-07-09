package com.arishi.lms_backend.controller;

import com.arishi.lms_backend.dto.ApiResponse;
import com.arishi.lms_backend.dto.StudentDTO;
import com.arishi.lms_backend.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/public/v1/student")
    public ResponseEntity<ApiResponse> createStudent(@Valid @RequestBody StudentDTO request) {

        StudentDTO response = studentService.createStudent(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED.value(), List.of("Student created successfully"), response));
    }

    @GetMapping("/v1/student/{id}")
    public ResponseEntity<ApiResponse> getStudentById(@PathVariable Long id) {

        StudentDTO response = studentService.getStudentById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(HttpStatus.OK.value(), List.of("Student fetched successfully"), response));
    }

    @GetMapping("/v1/student/profile")
    public ResponseEntity<ApiResponse> getStudentProfile() {

        StudentDTO student = studentService.getStudentProfile();

        ApiResponse response = new ApiResponse(200, List.of("Student profile fetched successfully"), student);

        return ResponseEntity.ok(response);
    }
}
