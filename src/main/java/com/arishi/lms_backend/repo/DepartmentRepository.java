package com.arishi.lms_backend.repo;

import com.arishi.lms_backend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByNameAndDeletedAtIsNull(String name);
}

