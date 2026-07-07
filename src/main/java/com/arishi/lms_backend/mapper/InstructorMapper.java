package com.arishi.lms_backend.mapper;

import com.arishi.lms_backend.dto.InstructorDTO;
import com.arishi.lms_backend.entity.Department;
import com.arishi.lms_backend.entity.Instructor;

public class InstructorMapper {

    public static Instructor toEntity(InstructorDTO dto) {

        Instructor instructor = new Instructor();

        instructor.setFirstName(dto.getFirstName());
        instructor.setLastName(dto.getLastName());
        instructor.setEmail(dto.getEmail());
        instructor.setMobileNumber(dto.getMobileNumber());
        instructor.setPasswordHash(dto.getPassword());
        instructor.setExperienceInMonths(dto.getExperienceInMonths());

        return instructor;
    }

    public static InstructorDTO toInstructorDto(Instructor instructor) {

        return new InstructorDTO(
                instructor.getId(),
                instructor.getFirstName(),
                instructor.getLastName(),
                null,
                instructor.getEmail(),
                instructor.getMobileNumber(),
                instructor.getDepartment() != null
                        ? instructor.getDepartment().getId()
                        : null,
                instructor.getExperienceInMonths()
        );
    }
}