package com.arishi.lms_backend.service.impl;

import com.arishi.lms_backend.config.security.CurrentUser;
import com.arishi.lms_backend.dto.response.EnrollmentResponseDTO;
import com.arishi.lms_backend.entity.Course;
import com.arishi.lms_backend.entity.Enrollment;
import com.arishi.lms_backend.entity.Student;
import com.arishi.lms_backend.exception.customException.BadRequestException;
import com.arishi.lms_backend.exception.customException.DuplicateResourceException;
import com.arishi.lms_backend.exception.customException.ResourceNotFoundException;
import com.arishi.lms_backend.mapper.EnrollmentMapper;
import com.arishi.lms_backend.repo.CourseRepository;
import com.arishi.lms_backend.repo.EnrollmentRepository;
import com.arishi.lms_backend.repo.StudentRepository;
import com.arishi.lms_backend.service.EnrollmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    @Transactional
    public EnrollmentResponseDTO enrollInCourse(Long courseId) {

        // get currentUser id
        Long studentId = CurrentUser.getId();

        Student student = studentRepository.findByIdAndDeletedAtIsNull(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        // find course
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        //duplicate enrollment check
        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new DuplicateResourceException("Student is already enrolled in this course");
        }

        // student cannot enroll in a completed course
        LocalDate today = LocalDate.now();

        if (!course.getEndDate().isAfter(today)) {
            throw new BadRequestException("Enrollment is closed because the course has ended.");
        }

        Enrollment enrollment = EnrollmentMapper.toEntity(student, course);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        return EnrollmentMapper.toEnrollmentResponseDTO(savedEnrollment);
    }
}