package com.arishi.lms_backend.service.impl;

import com.arishi.lms_backend.dto.DepartmentDTO;
import com.arishi.lms_backend.entity.Department;
import com.arishi.lms_backend.exception.DuplicateResourceException;
import com.arishi.lms_backend.mapper.DepartmentMapper;
import com.arishi.lms_backend.repo.DepartmentRepository;
import com.arishi.lms_backend.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO request) {
        if (departmentRepository.existsByNameAndDeletedAtIsNull(request.getName())) {
            throw new DuplicateResourceException("Department name already exists");
        }

        Department department = DepartmentMapper.toEntity(request);

        Department savedDepartment = departmentRepository.save(department);

        return DepartmentMapper.toDepartmentDTO(savedDepartment);
    }
}
