package com.arishi.lms_backend.repository;

import com.arishi.lms_backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

}
