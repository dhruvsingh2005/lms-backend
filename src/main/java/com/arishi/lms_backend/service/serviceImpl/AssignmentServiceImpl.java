package com.arishi.lms_backend.service.serviceImpl;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.arishi.lms_backend.config.security.CurrentUser;
import com.arishi.lms_backend.dto.AssignmentDto;
import com.arishi.lms_backend.entity.Assignment;
import com.arishi.lms_backend.entity.Course;
import com.arishi.lms_backend.enums.EnrollmentStatus;
import com.arishi.lms_backend.exception.customException.CourseAlreadyEndedException;
import com.arishi.lms_backend.exception.customException.InvalidAssignmentDateException;
import com.arishi.lms_backend.mapper.AssignmentMapper;
import com.arishi.lms_backend.repo.AssignmentRepo;
import com.arishi.lms_backend.repo.CourseRepo;
import com.arishi.lms_backend.repo.EnrollmentRepo;
import com.arishi.lms_backend.service.AssignmentService;
import com.arishi.lms_backend.service.FileStorageService;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
	
	private final CourseRepo courseRepo;
	
	private final AssignmentRepo assignmentRepo;

	private final EnrollmentRepo enrollmentRepo;

	private final FileStorageService fileStorageService;
	
	@Override
	@Transactional
	public AssignmentDto createAssignment(AssignmentDto assignmentDto, Long courseId) {
		
		if (!CurrentUser.getRole().equalsIgnoreCase("Instructor")) {
			throw new AccessDeniedException("Unauthorized");
		}
		
		Optional<Course> courseOptional = courseRepo.findByIdAndInstructorId(courseId, CurrentUser.getId());

		if (courseOptional.isEmpty()) {
			throw new EntityNotFoundException("Course not found");
		}
		
		Course course = courseOptional.get();
		if (course.getEndDate().isBefore(LocalDate.now())) {
			throw new CourseAlreadyEndedException("Course already ended");
		}
		
		if (course.getEndDate().isBefore(assignmentDto.getDueDate())) {
			throw new InvalidAssignmentDateException("Assignment due date cannot be greater than course end date");
		}
		
		if (assignmentRepo.existsByTitleAndCourseIdAndDueDateAfter(assignmentDto.getTitle(), courseId, assignmentDto.getDueDate())) {
			throw new EntityExistsException("Active assignment with same name, already exists");
		}
		
		Assignment assignment = AssignmentMapper.toEntity(assignmentDto, course);
		String storageKey = fileStorageService.storeAssignmentPdf(assignmentDto.getAssignmentSource(), courseId);
		assignment.setSource(storageKey);
		Assignment savedAssignment = assignmentRepo.save(assignment);
		
		return AssignmentMapper.toAssignmentDto(savedAssignment);
	}

	@Override
	@Transactional
	public Resource getAssignmentPdf(Long assignmentId) {
		Assignment assignment = assignmentRepo.findById(assignmentId)
				.orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

		if (!canCurrentUserAccessAssignment(assignment)) {
			throw new AccessDeniedException("Access denied");
		}

		return fileStorageService.loadAssignmentPdf(assignment.getSource());
	}

	private boolean canCurrentUserAccessAssignment(Assignment assignment) {
		Long currentUserId = CurrentUser.getId();
		String currentRole = CurrentUser.getRole();
		Long courseId = assignment.getCourse().getId();

		if ("Instructor".equalsIgnoreCase(currentRole)) {
			return assignment.getCourse().getInstructor().getId().equals(currentUserId);
		}

		if ("Student".equalsIgnoreCase(currentRole)) {
			return enrollmentRepo.existsByStudentIdAndCourseIdAndStatus(currentUserId, courseId, EnrollmentStatus.APPROVED);
		}

		return false;
	}

}
