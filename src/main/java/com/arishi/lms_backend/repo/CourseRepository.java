package com.arishi.lms_backend.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arishi.lms_backend.entity.Course;
import com.arishi.lms_backend.entity.Instructor;

public interface CourseRepository extends JpaRepository<Course, Long> {
	boolean existsByTitleIgnoreCaseAndInstructorId(String title, Long instructorId);
	
	  List<Course> findByEndDateGreaterThanEqual(LocalDate today);

	    List<Course> findByEndDateLessThan(LocalDate today);

		 // Instructor
	    List<Course> findByInstructorIdAndEndDateGreaterThanEqual(
	            Long instructorId,
	            LocalDate today
	    );
	    List<Course> findByInstructorIdAndEndDateLessThan(
	            Long instructorId,
	            LocalDate today
	    );
	    Optional<Course> findById(Long id);

}
