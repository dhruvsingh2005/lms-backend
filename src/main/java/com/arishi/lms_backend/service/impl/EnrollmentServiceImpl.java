package com.arishi.lms_backend.service.impl;

import com.arishi.lms_backend.config.security.CurrentUser;
import com.arishi.lms_backend.dto.response.EnrollmentResponseDTO;
import com.arishi.lms_backend.dto.response.StudentEnrollmentCourseResponseDTO;
import com.arishi.lms_backend.entity.Course;
import com.arishi.lms_backend.entity.Enrollment;
import com.arishi.lms_backend.entity.Student;
import com.arishi.lms_backend.enums.EnrollmentStatus;
import com.arishi.lms_backend.exception.customException.BadRequestException;
import com.arishi.lms_backend.exception.customException.DuplicateResourceException;
import com.arishi.lms_backend.exception.customException.ForbiddenException;
import com.arishi.lms_backend.exception.customException.ResourceNotFoundException;
import com.arishi.lms_backend.mapper.EnrollmentMapper;
import com.arishi.lms_backend.repo.CourseRepo;
import com.arishi.lms_backend.repo.EnrollmentRepo;
import com.arishi.lms_backend.repo.StudentRepo;
import com.arishi.lms_backend.service.EnrollmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final StudentRepo studentRepository;
    private final CourseRepo courseRepository;
    private final EnrollmentRepo enrollmentRepository;

    @Override
    @Transactional
    public EnrollmentResponseDTO enrollInCourse(Long courseId) {

        // get currentUser id
        Long studentId = CurrentUser.getId();
        String studentRole = CurrentUser.getRole();

        if (!"student".equalsIgnoreCase(studentRole)) {
            throw new AccessDeniedException("Only students can enroll in courses.");
        }

        Student student = studentRepository.findByIdAndDeletedAtIsNull(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Course course = courseRepository.findByIdAndDeletedAtIsNull(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        //duplicate enrollment check
        if (enrollmentRepository.existsByStudentAndCourseAndDeletedAtIsNull(student, course)) {
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

        @Override
        public List<StudentEnrollmentCourseResponseDTO> getStudentEnrollments(String status) {

            Long studentId = CurrentUser.getId();
            String role = CurrentUser.getRole();

            if (!"STUDENT".equalsIgnoreCase(role)) {
                throw new ForbiddenException("Access denied.");}

            LocalDate today = LocalDate.now();

            switch (status.toLowerCase()) {

                case "ongoing":
                    return enrollmentRepository.findOngoingEnrollments(
                            studentId,
                            EnrollmentStatus.APPROVED,
                            today);

                case "completed":
                    return enrollmentRepository.findCompletedEnrollments(
                            studentId,
                            EnrollmentStatus.APPROVED,
                            today );

                default:
                    throw new BadRequestException(
                            "Invalid status. Allowed values are ongoing and completed." );
            }
        }
    
}