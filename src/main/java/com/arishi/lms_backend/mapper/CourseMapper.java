package com.arishi.lms_backend.mapper;

import com.arishi.lms_backend.dto.CourseContentResponseDTO;
import com.arishi.lms_backend.dto.CourseResponseDTO;
import com.arishi.lms_backend.dto.request.CourseRequestDTO;
import com.arishi.lms_backend.entity.Course;
import com.arishi.lms_backend.entity.Instructor;

public class CourseMapper {

    public static Course toEntity(
            CourseRequestDTO dto,
            Instructor instructor
    ) {

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

   
    public static CourseResponseDTO toResponseDto(Course course) {

        return new CourseResponseDTO(

                course.getId(),

                course.getTitle(),

                course.getDescription(),

                course.getInstructor().getId(),

                course.getStartDate(),

                course.getEndDate(),

                course.getPrice()

        );
    }

    public static CourseContentResponseDTO toContentResponseDto(Course course) {

        return new CourseContentResponseDTO(

                course.getId(),

                course.getTitle(),

                course.getDescription(),

                course.getCourseContent()

        );
    }

}