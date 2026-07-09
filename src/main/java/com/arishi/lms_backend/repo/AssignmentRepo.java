package com.arishi.lms_backend.repo;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import com.arishi.lms_backend.entity.Assignment;

public interface AssignmentRepo extends JpaRepository<Assignment, Long>{
	
	Boolean existsByTitleAndCourseIdAndDueDateGreaterThanEqual(String title, Long courseId, LocalDate dueDate);;
}
