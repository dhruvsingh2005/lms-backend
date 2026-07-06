package com.arishi.lms_backend.mapper;

import com.arishi.lms_backend.dto.StudentDTO;
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

        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                null, // password response me nahi bhejna
                student.getEmail(),
                student.getMobileNumber()
        );
    }


}