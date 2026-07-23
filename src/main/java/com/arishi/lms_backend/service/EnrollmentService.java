package com.arishi.lms_backend.service;

import java.util.List;

import com.arishi.lms_backend.dto.response.EnrollmentResponseDTO;
import com.arishi.lms_backend.dto.response.StudentEnrollmentCourseResponseDTO;

public interface EnrollmentService {

    EnrollmentResponseDTO enrollInCourse(Long courseId);
    List<StudentEnrollmentCourseResponseDTO> getStudentEnrollments(String status);
}
