package com.arishi.lms_backend.controller;

import com.arishi.lms_backend.dto.ApiResponse;
import com.arishi.lms_backend.dto.StudentDTO;
import com.arishi.lms_backend.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/v1/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

//    @PostMapping
//    public ResponseEntity<StudentDTO> createStudent(
//            @Valid @RequestBody StudentDTO request) {
//
//        StudentDTO response = studentService.createStudent(request);
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(response);
//                .body(new ApiResponse(
//                        HttpStatus.CREATED.value(),
//                        List.of("Student created successfully"),
//                        student
//                ));
//    }

    @PostMapping
    public ResponseEntity<ApiResponse> createStudent(
            @Valid @RequestBody StudentDTO request) {

        StudentDTO response = studentService.createStudent(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(
                        HttpStatus.CREATED.value(),
                        List.of("Student created successfully"),
                        response
                ));
    }
}