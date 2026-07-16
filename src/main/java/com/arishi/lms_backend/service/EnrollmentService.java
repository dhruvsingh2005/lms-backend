package com.arishi.lms_backend.service;

import com.arishi.lms_backend.dto.response.EnrollmentResponseDTO;

public interface EnrollmentService {

    EnrollmentResponseDTO enrollInCourse(Long courseId);
}
