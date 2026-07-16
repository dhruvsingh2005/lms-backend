package com.arishi.lms_backend.mapper;

import com.arishi.lms_backend.dto.response.EnrollmentResponseDTO;
import com.arishi.lms_backend.entity.Course;
import com.arishi.lms_backend.entity.Enrollment;
import com.arishi.lms_backend.entity.Student;
import com.arishi.lms_backend.enums.EnrollmentStatus;

public class EnrollmentMapper {

    public static EnrollmentResponseDTO toEnrollmentResponseDTO(Enrollment enrollment) {

        EnrollmentResponseDTO response = new EnrollmentResponseDTO();

        response.setEnrollmentId(enrollment.getId());
        response.setStudentId(enrollment.getStudent().getId());
        response.setCourseId(enrollment.getCourse().getId());
        response.setStatus(enrollment.getStatus());

        return response;
    }

    public static Enrollment toEntity(Student student, Course course) {

        Enrollment enrollment = new Enrollment();

        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus(EnrollmentStatus.PENDING);

        return enrollment;
    }
}