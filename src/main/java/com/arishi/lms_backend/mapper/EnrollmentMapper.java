package com.arishi.lms_backend.mapper;

import com.arishi.lms_backend.dto.EnrollmentDTO;
import com.arishi.lms_backend.entity.Course;
import com.arishi.lms_backend.entity.Enrollment;
import com.arishi.lms_backend.entity.Student;

public class EnrollmentMapper {

    public static Enrollment toEntity(
            EnrollmentDTO dto,
            Student student,
            Course course) {

        Enrollment enrollment = new Enrollment();

        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus(dto.getStatus());

        return enrollment;
    }

    public static EnrollmentDTO toEnrollmentDto(Enrollment enrollment) {

        return new EnrollmentDTO(
                enrollment.getId(),
                enrollment.getStudent().getId(),
                enrollment.getCourse().getId(),
                enrollment.getStatus()
        );
    }
}