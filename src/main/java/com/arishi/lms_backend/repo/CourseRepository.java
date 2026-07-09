package com.arishi.lms_backend.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arishi.lms_backend.entity.Course;
import com.arishi.lms_backend.entity.Instructor;

public interface CourseRepository extends JpaRepository<Course, Long> {
	
	boolean existsByTitleIgnoreCaseAndInstructorIdAndDeletedAtIsNull(String title, Long instructorId);
	
	// Student
	List<Course> findByEndDateGreaterThanEqualAndDeletedAtIsNull(
	        LocalDate today
	);

	List<Course> findByEndDateLessThanAndDeletedAtIsNull(
	        LocalDate today
	);

	// Instructor
	List<Course> findByInstructorIdAndEndDateGreaterThanEqualAndDeletedAtIsNull(
	        Long instructorId,
	        LocalDate today
	);

	List<Course> findByInstructorIdAndEndDateLessThanAndDeletedAtIsNull(
	        Long instructorId,
	        LocalDate today
	);

	// Get Course By Id
	Optional<Course> findByIdAndDeletedAtIsNull(Long id);

}
