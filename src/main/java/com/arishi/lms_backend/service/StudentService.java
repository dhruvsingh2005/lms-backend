package com.arishi.lms_backend.service;

import com.arishi.lms_backend.dto.StudentDTO;
import com.arishi.lms_backend.dto.StudentUpdateProfileDTO;
import jakarta.validation.Valid;
import com.arishi.lms_backend.dto.StudentAssignmentDto;

import java.util.List;

public interface StudentService {

    StudentDTO createStudent(StudentDTO request);

    StudentDTO getStudentById(Long id);

    StudentDTO getStudentProfile();

    StudentUpdateProfileDTO updateStudentProfile(StudentUpdateProfileDTO request);

    List<StudentAssignmentDto> getStudentAssignmentsByStatus(Long courseId, String status);
}