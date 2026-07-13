package com.arishi.lms_backend.mapper;

import com.arishi.lms_backend.dto.StudentDTO;
import com.arishi.lms_backend.dto.StudentUpadateProDTO;
import com.arishi.lms_backend.entity.Student;

public class StudentMapper {

    public static Student toEntity(StudentDTO dto) {

        Student student = new Student();

        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setMobileNumber(dto.getMobileNumber());
        student.setPasswordHash(dto.getPassword());

        return student;
    }

    public static StudentDTO toStudentDto(Student student) {

        return new StudentDTO(student.getId(), student.getFirstName(), student.getLastName(), null, // password response me nahi bhejna
                student.getEmail(), student.getMobileNumber());
    }


    // Update API
    public static void updateEntity(Student student, StudentUpadateProDTO request) {
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setMobileNumber(request.getMobileNumber());
    }

    public static StudentUpadateProDTO toUpdateResponse(Student student) {

        StudentUpadateProDTO response = new StudentUpadateProDTO();

        response.setId(student.getId());
        response.setFirstName(student.getFirstName());
        response.setLastName(student.getLastName());
        response.setEmail(student.getEmail());
        response.setMobileNumber(student.getMobileNumber());

        return response;
    }


}