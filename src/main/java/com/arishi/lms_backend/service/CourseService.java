package com.arishi.lms_backend.service;

import java.util.List;

import com.arishi.lms_backend.dto.CourseDTO;

import jakarta.validation.Valid;

public interface CourseService {

	CourseDTO createCourse(@Valid CourseDTO request);
   
    List<CourseDTO> getAvailableCourses(String status);

    List<CourseDTO> getInstructorCourses(String status);

    CourseDTO getCourseById(Long courseId);

}
