package com.arishi.lms_backend.service.impl;

import com.arishi.lms_backend.config.security.CurrentUser;
import com.arishi.lms_backend.dto.InstructorDTO;
import com.arishi.lms_backend.entity.Department;
import com.arishi.lms_backend.entity.Instructor;
import com.arishi.lms_backend.exception.customException.DuplicateResourceException;
import com.arishi.lms_backend.exception.customException.ResourceNotFoundException;
import com.arishi.lms_backend.mapper.InstructorMapper;
import com.arishi.lms_backend.repo.DepartmentRepository;
import com.arishi.lms_backend.repo.InstructorRepository;
import com.arishi.lms_backend.service.InstructorService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public InstructorDTO createInstructor(InstructorDTO request) {

        if (instructorRepository.existsByEmailAndDeletedAtIsNull(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (instructorRepository.existsByMobileNumberAndDeletedAtIsNull(request.getMobileNumber())) {
            throw new DuplicateResourceException("Mobile number already exists");
        }

        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        Instructor instructor = InstructorMapper.toEntity(request, department);

        instructor.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        // instructor.setDepartment(department);

        Instructor saved = instructorRepository.save(instructor);

        return InstructorMapper.toInstructorDto(saved);
    }

    @Override
    public InstructorDTO getInstructorProfile() {

        Long instructorId = CurrentUser.getId();
        String role = CurrentUser.getRole();

        if (!"instructor".equalsIgnoreCase(role)) {
            throw new AccessDeniedException("Only instructor can access this profile");
        }

        Instructor instructor = instructorRepository.findByIdAndDeletedAtIsNull(instructorId).orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));

        return InstructorMapper.toInstructorDto(instructor);
    }


}