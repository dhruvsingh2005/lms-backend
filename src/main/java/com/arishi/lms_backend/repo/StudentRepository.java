package com.arishi.lms_backend.repo;

import com.arishi.lms_backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmailAndDeletedAtIsNull(String email);

    boolean existsByMobileNumberAndDeletedAtIsNull(String mobileNumber);

    Optional<Student> findByEmailAndDeletedAtIsNull(String email);

    Optional<Student> findByIdAndDeletedAtIsNull(Long id);
}
