package com.arishi.lms_backend.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arishi.lms_backend.config.security.CurrentUser;
import com.arishi.lms_backend.dto.CourseDTO;
import com.arishi.lms_backend.entity.Course;
import com.arishi.lms_backend.entity.Instructor;
import com.arishi.lms_backend.exception.BadRequestException;
import com.arishi.lms_backend.exception.DuplicateResourceException;
import com.arishi.lms_backend.exception.ForbiddenException;
import com.arishi.lms_backend.exception.ResourceNotFoundException;
import com.arishi.lms_backend.mapper.CourseMapper;
import com.arishi.lms_backend.repo.CourseRepository;
import com.arishi.lms_backend.repo.InstructorRepository;
import com.arishi.lms_backend.service.CourseService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {

	private final CourseRepository courseRepository;
	private final InstructorRepository instructorRepository;

	@Override
	public CourseDTO createCourse(CourseDTO request) {

		Long instructorId = CurrentUser.getId();
		 String role = CurrentUser.getRole();
		  
		  if (!"INSTRUCTOR".equalsIgnoreCase(role)) {
	            throw new ForbiddenException("Access denied.");
	        }
		Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(
				() -> new ResourceNotFoundException("Instructor not found with id : " + instructorId));

		if (courseRepository.existsByTitleIgnoreCaseAndInstructorIdAndDeletedAtIsNull(request.getTitle(), instructorId)) {
			throw new DuplicateResourceException(" Same Course already exists for this instructor");
		}
		
		Course course = CourseMapper.toEntity(request, instructor);
		
		Course savedCourse = courseRepository.save(course);
		return CourseMapper.toCourseDto(savedCourse);
	}

    @Override
    public List<CourseDTO> getAvailableCourses(String status) {

        LocalDate today = LocalDate.now();
        List<Course> courses;
        switch (status.toLowerCase()) {

            case "available":
                courses = courseRepository.findByEndDateGreaterThanEqualAndDeletedAtIsNull(today);
                break;

           case "expired":
              throw new BadRequestException(
                     "Students are not allowed to view expired courses.");

            default:
                throw new BadRequestException( "Invalid status. Allowed value is 'available'.");
        }
        return courses.stream().map(CourseMapper::toCourseDto).toList();
    }
    @Override
    public List<CourseDTO> getInstructorCourses(String status) {
    	  String role = CurrentUser.getRole();

        Long instructorId = CurrentUser.getId();
        if (!"INSTRUCTOR".equalsIgnoreCase(role)) {
            throw new ForbiddenException( "Access denied.");
        }

        LocalDate today = LocalDate.now();
        List<Course> courses;

        switch (status.toLowerCase()) {
            case "available":
                courses = courseRepository .findByInstructorIdAndEndDateGreaterThanEqualAndDeletedAtIsNull(instructorId, today );
                break;

            case "expired":
                courses = courseRepository  .findByInstructorIdAndEndDateLessThanAndDeletedAtIsNull(instructorId, today);
                break;
            default:
                throw new BadRequestException( "Invalid status. Allowed values are available and expired.");
        }
        return courses.stream().map(CourseMapper::toCourseDto) .toList();
    }
    @Override
    public CourseDTO getCourseById(Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException( "Course not found with id : " + courseId  ));
        return CourseMapper.toCourseDto(course);
    }
}