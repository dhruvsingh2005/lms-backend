package com.arishi.lms_backend.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.arishi.lms_backend.config.security.CurrentUser;
import com.arishi.lms_backend.dto.AssignmentDetailDto;
import com.arishi.lms_backend.dto.AssignmentDto;
import com.arishi.lms_backend.dto.AssignmentSubmissionSummaryDto;
import com.arishi.lms_backend.dto.AssignmentSummaryDto;
import com.arishi.lms_backend.entity.Assignment;
import com.arishi.lms_backend.entity.Course;
import com.arishi.lms_backend.entity.Student;
import com.arishi.lms_backend.enums.EnrollmentStatus;
import com.arishi.lms_backend.exception.customException.CourseAlreadyEndedException;
import com.arishi.lms_backend.exception.customException.InvalidAssignmentDateException;
import com.arishi.lms_backend.mapper.AssignmentMapper;
import com.arishi.lms_backend.repo.AssignmentRepo;
import com.arishi.lms_backend.repo.AssignmentSubmissionRepo;
import com.arishi.lms_backend.repo.CourseRepo;
import com.arishi.lms_backend.repo.StudentRepo;
import com.arishi.lms_backend.repo.EnrollmentRepo;
import com.arishi.lms_backend.service.AssignmentService;
import com.arishi.lms_backend.service.FileStorageService;
import com.arishi.lms_backend.validator.PdfValidator;

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

    private final AssignmentSubmissionRepo assignmentSubmissionRepo;

    private final StudentRepo studentRepository;

    @Override
    public List<AssignmentSummaryDto> getAssignmentsForCourseByStatus(Long courseId, String status) {

        if (!CurrentUser.getRole().equalsIgnoreCase("Instructor")) {
            throw new AccessDeniedException("Unauthorized");
        }

        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("status query param is required");
        }
        String normalized = status.trim();

        // Verify course belongs to current instructor and is not deleted
        courseRepo.findByIdAndInstructorIdAndDeletedAtIsNull(courseId, CurrentUser.getId())
            .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        List<Assignment> assignments;
        LocalDate today = LocalDate.now();
        if ("Active".equalsIgnoreCase(normalized)) {
            assignments = assignmentRepo.findByCourseIdAndDeletedAtIsNullAndDueDateGreaterThanEqual(courseId, today);
        } else if ("Pending".equalsIgnoreCase(normalized)) {
            assignments = assignmentRepo.findByCourseIdAndDeletedAtIsNullAndDueDateBefore(courseId, today);
        } else {
            throw new IllegalArgumentException("Invalid status. Allowed values: Active, Pending");
        }

        List<AssignmentSummaryDto> result = new ArrayList<>();
        for (Assignment a : assignments) {
            result.add(new AssignmentSummaryDto(a.getId(), a.getTitle(), a.getDescription()));
        }
        return result;
    }

    @Override
    @Transactional
    public void submitAssignment(Long assignmentId, MultipartFile file) {

        if (!CurrentUser.getRole().equalsIgnoreCase("Student")) {
            throw new AccessDeniedException("Unauthorized");
        }

        PdfValidator.validatePDF(file);

        Assignment assignment = assignmentRepo.findByIdAndDeletedAtIsNull(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        Long studentId = CurrentUser.getId();

        Long courseId = assignment.getCourse().getId();

        // check enrollment
        boolean enrolled = enrollmentRepo.existsByStudentIdAndCourseIdAndStatusAndDeletedAtIsNull(studentId, courseId,
                EnrollmentStatus.APPROVED);
        if (!enrolled) {
            throw new AccessDeniedException("Student not enrolled in the course");
        }

        // due date check
        if (assignment.getDueDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Assignment due date has passed");
        }

        if (assignmentSubmissionRepo.existsByAssignmentIdAndStudentIdAndDeletedAtIsNull(assignmentId, studentId)) {
            throw new EntityExistsException("Submission already exists");
        }

        // ensure student entity exists
        Student student = studentRepository.findByIdAndDeletedAtIsNull(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        String storageKey = fileStorageService.storeSubmissionPdf(file, assignmentId, studentId);

        com.arishi.lms_backend.entity.AssignmentSubmission submission = new com.arishi.lms_backend.entity.AssignmentSubmission();
        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setSource(storageKey);

        assignmentSubmissionRepo.save(submission);
    }

    @Override
    @Transactional
    public Resource getSubmissionPdf(Long assignmentId, Long studentId) {

        if (!CurrentUser.getRole().equalsIgnoreCase("Instructor")) {
            throw new AccessDeniedException("Unauthorized");
        }

        Assignment assignment = assignmentRepo.findByIdAndDeletedAtIsNull(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        if (!assignment.getCourse().getInstructor().getId().equals(CurrentUser.getId())) {
            throw new AccessDeniedException("Access denied");
        }

        com.arishi.lms_backend.entity.AssignmentSubmission submission = assignmentSubmissionRepo
                .findByAssignmentIdAndStudentIdAndDeletedAtIsNull(assignmentId, studentId)
                .orElseThrow(() -> new EntityNotFoundException("Submission not found"));

        return fileStorageService.loadSubmissionPdf(submission.getSource());
    }

    @Override
    @Transactional
    public List<AssignmentSubmissionSummaryDto> getSubmissionsForAssignment(Long assignmentId) {

        if (!CurrentUser.getRole().equalsIgnoreCase("Instructor")) {
            throw new AccessDeniedException("Unauthorized");
        }

        Assignment assignment = assignmentRepo.findByIdAndDeletedAtIsNull(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        if (!assignment.getCourse().getInstructor().getId().equals(CurrentUser.getId())) {
            throw new AccessDeniedException("Access denied");
        }

        List<com.arishi.lms_backend.entity.AssignmentSubmission> submissions = assignmentSubmissionRepo
                .findByAssignmentIdAndDeletedAtIsNull(assignmentId);

        List<AssignmentSubmissionSummaryDto> result = new ArrayList<>();
        for (com.arishi.lms_backend.entity.AssignmentSubmission s : submissions) {
            Student st = s.getStudent();
            String fullName = st.getFirstName() + (st.getLastName() == null ? "" : " " + st.getLastName());
            String url = "/api/v1/assignment/" + assignmentId + "/submission/" + st.getId() + "/pdf";
            result.add(new AssignmentSubmissionSummaryDto(st.getId(), s.getId(), fullName, s.getCreatedAt(), url));
        }

        return result;
    }

    @Override
    @Transactional
    public AssignmentDto createAssignment(AssignmentDto assignmentDto, Long courseId) {

        if (!CurrentUser.getRole().equalsIgnoreCase("Instructor")) {
            throw new AccessDeniedException("Unauthorized");
        }

        Optional<Course> courseOptional = courseRepo.findByIdAndInstructorIdAndDeletedAtIsNull(courseId, CurrentUser.getId());

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

        if (assignmentRepo.existsByTitleAndCourseIdAndDeletedAtIsNullAndDueDateGreaterThanEqual(assignmentDto.getTitle(), courseId, assignmentDto.getDueDate())) {
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

    @Override
    @Transactional
    public AssignmentDetailDto getAssignmentDetails(Long assignmentId) {
        if (!CurrentUser.getRole().equalsIgnoreCase("Instructor")) {
            throw new AccessDeniedException("Unauthorized");
        }

        Assignment assignment = assignmentRepo.findByIdAndDeletedAtIsNull(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        if (!assignment.getCourse().getInstructor().getId().equals(CurrentUser.getId())) {
            throw new AccessDeniedException("Access denied");
        }

        Long submissionCount = assignmentSubmissionRepo.countByAssignmentIdAndDeletedAtIsNull(assignmentId);
        return AssignmentMapper.toAssignmentDetailDto(assignment, submissionCount);
    }

    private boolean canCurrentUserAccessAssignment(Assignment assignment) {
        Long currentUserId = CurrentUser.getId();
        String currentRole = CurrentUser.getRole();
        Long courseId = assignment.getCourse().getId();

        if ("Instructor".equalsIgnoreCase(currentRole)) {
            return assignment.getCourse().getInstructor().getId().equals(currentUserId);
        }

        if ("Student".equalsIgnoreCase(currentRole)) {
            return enrollmentRepo.existsByStudentIdAndCourseIdAndStatusAndDeletedAtIsNull(currentUserId, courseId, EnrollmentStatus.APPROVED);
        }
        return false;
    }

}
