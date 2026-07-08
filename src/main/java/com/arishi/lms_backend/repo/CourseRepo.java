package com.arishi.lms_backend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.arishi.lms_backend.entity.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
	
	Optional<Course> findByIdAndInstructorId(Long id, Long instructorId);
}
