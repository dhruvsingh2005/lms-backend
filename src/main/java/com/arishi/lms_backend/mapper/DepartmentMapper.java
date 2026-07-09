package com.arishi.lms_backend.mapper;

import com.arishi.lms_backend.dto.DepartmentDTO;
import com.arishi.lms_backend.entity.Department;


public class DepartmentMapper {

    public static Department toEntity(DepartmentDTO dto) {
        Department department = new Department();
        department.setName(dto.getName());
        return department;
    }
    public static DepartmentDTO toDepartmentDTO(Department department) {
        return new DepartmentDTO(
                department.getId(),
                department.getName()
        );
    }
}

