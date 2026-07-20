package com.arishi.lms_backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arishi.lms_backend.entity.AssignmentSubmission;

@Repository
public interface AssignmentSubmissionRepo extends JpaRepository<AssignmentSubmission, Long> {

	boolean existsByAssignmentIdAndStudentIdAndDeletedAtIsNull(Long assignmentId, Long studentId);

	Long countByAssignmentIdAndDeletedAtIsNull(Long assignmentId);

	java.util.Optional<com.arishi.lms_backend.entity.AssignmentSubmission> findByAssignmentIdAndStudentIdAndDeletedAtIsNull(Long assignmentId, Long studentId);

	java.util.List<com.arishi.lms_backend.entity.AssignmentSubmission> findByAssignmentIdAndDeletedAtIsNull(Long assignmentId);

	java.util.List<com.arishi.lms_backend.entity.AssignmentSubmission> findByStudentIdAndAssignment_Course_IdAndDeletedAtIsNull(Long studentId, Long courseId);
}
