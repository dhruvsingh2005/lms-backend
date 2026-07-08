package com.arishi.lms_backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arishi.lms_backend.entity.Enrollment;
import com.arishi.lms_backend.enums.EnrollmentStatus;

@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment, Long> {

	boolean existsByStudentIdAndCourseIdAndStatus(Long studentId, Long courseId, EnrollmentStatus status);
}
