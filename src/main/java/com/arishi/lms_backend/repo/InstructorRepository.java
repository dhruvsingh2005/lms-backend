package com.arishi.lms_backend.repo;

import com.arishi.lms_backend.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    boolean existsByEmailAndDeletedAtIsNull(String email);

    boolean existsByMobileNumberAndDeletedAtIsNull(String mobileNumber);

    Optional<Instructor> findByEmailAndDeletedAtIsNull(String email);

    Optional<Instructor> findByIdAndDeletedAtIsNull(Long id);
    
}
