package com.arishi.lms_backend.repo;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.arishi.lms_backend.entity.Assignment;

public interface AssignmentRepo extends JpaRepository<Assignment, Long>{
	
	Boolean existsByTitleAndCourseIdAndDeletedAtIsNullAndDueDateGreaterThanEqual(String title, Long courseId, LocalDate dueDate);;

	java.util.Optional<Assignment> findByIdAndDeletedAtIsNull(Long id);

	List<Assignment> findByCourseIdAndDeletedAtIsNullAndDueDateGreaterThanEqual(Long courseId, LocalDate date);

	List<Assignment> findByCourseIdAndDeletedAtIsNullAndDueDateBefore(Long courseId, LocalDate date);

	List<Assignment> findByCourseIdAndDeletedAtIsNull(Long courseId);
}
