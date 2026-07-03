package com.arishi.lms_backend.service.impl;

import com.arishi.lms_backend.dto.StudentDTO;
import com.arishi.lms_backend.entity.Student;
import com.arishi.lms_backend.exception.DuplicateResourceException;
import com.arishi.lms_backend.mapper.StudentMapper;
import com.arishi.lms_backend.repo.StudentRepository;
import com.arishi.lms_backend.service.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository , PasswordEncoder passwordEncoder) {

        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public StudentDTO createStudent(StudentDTO request) {

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (studentRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new DuplicateResourceException("Mobile number already exists");
        }

        Student student =
                StudentMapper.toEntity(request);

        student.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );

        Student saved =
                studentRepository.save(student);

        return StudentMapper.toResponse(saved);
    }


}