package com.arishi.lms_backend.service.impl;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arishi.lms_backend.config.security.CurrentUser;
import com.arishi.lms_backend.dto.CourseContentResponseDTO;
import com.arishi.lms_backend.dto.CourseResponseDTO;
import com.arishi.lms_backend.dto.request.CourseRequestDTO;
import com.arishi.lms_backend.entity.Course;
import com.arishi.lms_backend.entity.Instructor;
import com.arishi.lms_backend.enums.EnrollmentStatus;
import com.arishi.lms_backend.exception.customException.BadRequestException;
import com.arishi.lms_backend.exception.customException.DuplicateResourceException;
import com.arishi.lms_backend.exception.customException.ForbiddenException;
import com.arishi.lms_backend.exception.customException.ResourceNotFoundException;
import com.arishi.lms_backend.mapper.CourseMapper;
import com.arishi.lms_backend.repo.CourseRepo;
import com.arishi.lms_backend.repo.InstructorRepo;
import com.arishi.lms_backend.repo.EnrollmentRepo;
import com.arishi.lms_backend.service.CourseService;


import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {


	private final CourseRepo courseRepository;
	private final InstructorRepo instructorRepository;

    private final EnrollmentRepo enrollmentRepository;
    
    
    @Override
    public CourseResponseDTO createCourse(CourseRequestDTO request) {

        Long instructorId = CurrentUser.getId();

        String role = CurrentUser.getRole();

        if (!"INSTRUCTOR".equalsIgnoreCase(role)) {
            throw new ForbiddenException("Access denied.");
        }

        Instructor instructor = instructorRepository
                .findById(instructorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Instructor not found with id : "
                                        + instructorId));

        if (courseRepository.existsByTitleIgnoreCaseAndInstructorIdAndDeletedAtIsNull(
                request.getTitle(),
                instructorId)) {

            throw new DuplicateResourceException(
                    "Same course already exists for this instructor.");
        }

        Course course =
                CourseMapper.toEntity(request, instructor);

        Course savedCourse =
                courseRepository.save(course);

        return CourseMapper.toResponseDto(savedCourse);
    }
    
    
    @Override
    public List<CourseResponseDTO> getAvailableCourses(String status) {

        LocalDate today = LocalDate.now();

        List<Course> courses;

        switch (status.toLowerCase()) {

            case "available":
                courses = courseRepository
                        .findByEndDateGreaterThanEqualAndDeletedAtIsNull(today);
                break;

            case "expired":
                throw new BadRequestException(
                        "Students are not allowed to view expired courses.");

            default:
                throw new BadRequestException(
                        "Invalid status. Allowed value is 'available'.");
        }

        return courses.stream()
                .map(CourseMapper::toResponseDto)
                .toList();
    }
    
    
    @Override
    public List<CourseResponseDTO> getInstructorCourses(String status) {

        Long instructorId = CurrentUser.getId();

        String role = CurrentUser.getRole();

        if (!"INSTRUCTOR".equalsIgnoreCase(role)) {
            throw new ForbiddenException("Access denied.");
        }

        LocalDate today = LocalDate.now();

        List<Course> courses;

        switch (status.toLowerCase()) {

            case "available":

                courses = courseRepository
                        .findByInstructorIdAndEndDateGreaterThanEqualAndDeletedAtIsNull(
                                instructorId,
                                today
                        );
                break;

            case "expired":

                courses = courseRepository
                        .findByInstructorIdAndEndDateLessThanAndDeletedAtIsNull(
                                instructorId,
                                today
                        );
                break;

            default:
                throw new BadRequestException(
                        "Invalid status. Allowed values are available and expired.");
        }

        return courses.stream()
                .map(CourseMapper::toResponseDto)
                .toList();
    }


    @Override
    public CourseResponseDTO getCourseById(Long courseId) {

        Course course = courseRepository
                .findByIdAndDeletedAtIsNull(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found with id : " + courseId));

        return CourseMapper.toResponseDto(course);
    }
    
    @Override
    public CourseContentResponseDTO getCourseContent(Long courseId) {

        Course course = courseRepository
                .findByIdAndDeletedAtIsNull(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found."));

        Long userId = CurrentUser.getId();
        String role = CurrentUser.getRole();

        if ("INSTRUCTOR".equalsIgnoreCase(role)) {

            if (!course.getInstructor().getId().equals(userId)) {
                throw new ForbiddenException(
                        "You are not allowed to access this course.");
            }

            return CourseMapper.toContentResponseDto(course);
        }

        if ("STUDENT".equalsIgnoreCase(role)) {

            boolean enrolled = enrollmentRepository
                    .existsByStudentIdAndCourseIdAndStatusAndDeletedAtIsNull(
                            userId,
                            courseId,
                            EnrollmentStatus.APPROVED
                    );

            if (!enrolled) {
                throw new ForbiddenException(
                        "You are not enrolled in this course or your enrollment is not approved.");
            }

            return CourseMapper.toContentResponseDto(course);
        }

        throw new ForbiddenException("Access denied.");
    }
}