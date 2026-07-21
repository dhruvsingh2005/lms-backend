package com.arishi.lms_backend.service;

import java.util.List;

import com.arishi.lms_backend.dto.request.CourseRequestDTO;
import com.arishi.lms_backend.dto.response.CourseContentResponseDTO;
import com.arishi.lms_backend.dto.response.CourseResponseDTO;

public interface CourseService {


    CourseResponseDTO createCourse(CourseRequestDTO request);

    List<CourseResponseDTO> getAvailableCourses(String status);

    List<CourseResponseDTO> getInstructorCourses(String status);

    CourseResponseDTO getCourseById(Long courseId);

    CourseContentResponseDTO getCourseContent(Long courseId);

}