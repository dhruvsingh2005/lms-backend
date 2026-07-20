package com.arishi.lms_backend.dto;

import com.arishi.lms_backend.enums.EnrollmentStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EnrollmentDTO {

    private Long id;

    private Long studentId;
   
    @NotBlank(message="Course Id is required ")
    private Long courseId;

    private EnrollmentStatus status;
}
