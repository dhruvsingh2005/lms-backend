package com.arishi.lms_backend.mapper;

import com.arishi.lms_backend.dto.CourseDTO;
import com.arishi.lms_backend.entity.Course;
import com.arishi.lms_backend.entity.Instructor;

public class CourseMapper {
	public static final Course toEntity(CourseDTO dto, Instructor instructor) {
		Course course = new Course();
		course.setTitle(dto.getTitle());
		course.setDescription(dto.getDescription());
		course.setStartDate(dto.getStartDate());
		course.setEndDate(dto.getEndDate());
		course.setInstructor(instructor);
		course.setPrice(dto.getPrice());
		course.setCourseContent(dto.getCourseContent());
		return course;
	}

	public static final CourseDTO toCourseDto(Course course) {
		return new CourseDTO(course.getId(),
				course.getTitle(),
				course.getDescription(),
				course.getInstructor().getId(),
				course.getStartDate(), 
				course.getEndDate(), 
				course.getCourseContent(), 
				course.getPrice()

		);
	}

}
