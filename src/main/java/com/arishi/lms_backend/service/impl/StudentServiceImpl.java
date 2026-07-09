package com.arishi.lms_backend.service.impl;

import com.arishi.lms_backend.config.security.CurrentUser;
import com.arishi.lms_backend.dto.StudentDTO;
import com.arishi.lms_backend.entity.Student;
import com.arishi.lms_backend.exception.customException.DuplicateResourceException;
import com.arishi.lms_backend.exception.customException.ResourceNotFoundException;
import com.arishi.lms_backend.mapper.StudentMapper;
import com.arishi.lms_backend.repo.StudentRepository;
import com.arishi.lms_backend.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public StudentDTO createStudent(StudentDTO request) {

        if (studentRepository.existsByEmailAndDeletedAtIsNull(request.getEmail())) {

            throw new DuplicateResourceException("Email already exists");
        }

        if (studentRepository.existsByMobileNumberAndDeletedAtIsNull(request.getMobileNumber())) {

            throw new DuplicateResourceException("Mobile number already exists");
        }
        Student student = StudentMapper.toEntity(request);
        student.setPasswordHash(passwordEncoder.encode(request.getPassword().trim()));
        System.out.println(student);
        Student savedStudent = studentRepository.save(student);

        return StudentMapper.toStudentDto(savedStudent);
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not found"));
        return StudentMapper.toStudentDto(student);
    }

    @Override
    public StudentDTO getStudentProfile() {

        Long studentId = CurrentUser.getId();
        String role = CurrentUser.getRole();

        if (!"student".equalsIgnoreCase(role)) {
            throw new AccessDeniedException("Only student can access this profile");
        }

        Student student = studentRepository.findByIdAndDeletedAtIsNull(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        return StudentMapper.toStudentDto(student);
    }


}