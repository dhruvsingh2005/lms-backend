package com.arishi.lms_backend.controller;

import com.arishi.lms_backend.dto.ApiResponse;
import com.arishi.lms_backend.dto.DepartmentDTO;
import com.arishi.lms_backend.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/public/v1/department")
    public ResponseEntity<ApiResponse> createDepartment(
            @Valid @RequestBody DepartmentDTO request) {

        DepartmentDTO response = departmentService.createDepartment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(
                        HttpStatus.CREATED.value(),
                        List.of("Department created successfully"),
                        response
                ));
    }
}
