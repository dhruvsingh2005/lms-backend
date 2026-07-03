package com.arishi.lms_backend.service.impl;

import com.arishi.lms_backend.dto.StudentDTO;
import com.arishi.lms_backend.entity.Student;
import com.arishi.lms_backend.mapper.StudentMapper;
import com.arishi.lms_backend.repository.StudentRepository;
import com.arishi.lms_backend.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentDTO createStudent(StudentDTO request) {

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (studentRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new RuntimeException("Mobile number already exists");
        }

        Student student =
                StudentMapper.toEntity(request);

        Student saved =
                studentRepository.save(student);

        return StudentMapper.toResponse(saved);
    }


}