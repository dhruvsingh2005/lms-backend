package com.arishi.lms_backend.controller;

import com.arishi.lms_backend.dto.ApiResponse;
import com.arishi.lms_backend.dto.InstructorDTO;
import com.arishi.lms_backend.service.InstructorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping("/public/v1/instructor")
    public ResponseEntity<ApiResponse> createInstructor(
            @Valid @RequestBody InstructorDTO request) {

        InstructorDTO response = instructorService.createInstructor(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(
                        HttpStatus.CREATED.value(),
                        List.of("Instructor created successfully"),
                        response
                ));
    }
}