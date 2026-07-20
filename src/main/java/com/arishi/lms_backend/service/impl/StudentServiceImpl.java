package com.arishi.lms_backend.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.arishi.lms_backend.config.security.CurrentUser;
import com.arishi.lms_backend.dto.StudentAssignmentDto;
import com.arishi.lms_backend.dto.StudentDTO;
import com.arishi.lms_backend.entity.Assignment;
import com.arishi.lms_backend.entity.AssignmentSubmission;
import com.arishi.lms_backend.entity.Student;
import com.arishi.lms_backend.enums.EnrollmentStatus;
import com.arishi.lms_backend.exception.customException.BadRequestException;
import com.arishi.lms_backend.exception.customException.DuplicateResourceException;
import com.arishi.lms_backend.exception.customException.ResourceNotFoundException;
import com.arishi.lms_backend.mapper.StudentMapper;
import com.arishi.lms_backend.repo.AssignmentRepo;
import com.arishi.lms_backend.repo.AssignmentSubmissionRepo;
import com.arishi.lms_backend.repo.CourseRepo;
import com.arishi.lms_backend.repo.EnrollmentRepo;
import com.arishi.lms_backend.repo.StudentRepo;
import com.arishi.lms_backend.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepository;
    
    private final PasswordEncoder passwordEncoder;

    private final CourseRepo courseRepo;

    private final EnrollmentRepo enrollmentRepo;

    private final AssignmentRepo assignmentRepo;

    private final AssignmentSubmissionRepo assignmentSubmissionRepo;

    @Override
    public StudentDTO createStudent(StudentDTO request) {

        if (studentRepository.existsByEmailAndDeletedAtIsNull(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (studentRepository.existsByMobileNumberAndDeletedAtIsNull(request.getMobileNumber())) {
            throw new DuplicateResourceException("Mobile number already exists");
        }
        Student student = StudentMapper.toEntity(request);
        student.setPasswordHash(passwordEncoder.encode(request.getPassword().trim()));
        System.out.println(student);
        Student savedStudent = studentRepository.save(student);

        return StudentMapper.toStudentDto(savedStudent);
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not found"));
        return StudentMapper.toStudentDto(student);
    }

    @Override
    public StudentDTO getStudentProfile() {

        Long studentId = CurrentUser.getId();
        String role = CurrentUser.getRole();

        if (!"student".equalsIgnoreCase(role)) {
            throw new AccessDeniedException("Only student can access this profile");
        }

        Student student = studentRepository.findByIdAndDeletedAtIsNull(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return StudentMapper.toStudentDto(student);
    }

    @Override
    public List<StudentAssignmentDto> getStudentAssignmentsByStatus(Long courseId, String status) {

        // 1. Validate role is student
        Long studentId = CurrentUser.getId();
        String role = CurrentUser.getRole();

        if (!"student".equalsIgnoreCase(role)) {
            throw new AccessDeniedException("Only students can access assignments");
        }

        // 2. Validate query param
        if (status == null || status.isBlank()) {
            throw new BadRequestException("status query param is required");
        }

        String normalized = status.trim().toLowerCase();
        if (!normalized.equals("pending") && !normalized.equals("submitted") && !normalized.equals("failed submissions")) {
            throw new BadRequestException("Invalid status. Allowed values: Pending, Submitted, Failed Submissions");
        }

        // 3. Validate course exists
        courseRepo.findByIdAndDeletedAtIsNull(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        // 4. Validate student is enrolled (APPROVED) in the course
        boolean enrolled = enrollmentRepo.existsByStudentIdAndCourseIdAndStatusAndDeletedAtIsNull(
                studentId, courseId, EnrollmentStatus.APPROVED);
        if (!enrolled) {
            throw new AccessDeniedException("Student is not enrolled in this course");
        }

        // 5. Fetch all assignments for the course and all submissions by this student for the course
        List<Assignment> allAssignments = assignmentRepo.findByCourseIdAndDeletedAtIsNull(courseId);
        List<AssignmentSubmission> studentSubmissions = assignmentSubmissionRepo
                .findByStudentIdAndAssignment_Course_IdAndDeletedAtIsNull(studentId, courseId);

        // Build a set of assignment IDs the student has submitted
        Set<Long> submittedAssignmentIds = studentSubmissions.stream()
                .map(sub -> sub.getAssignment().getId())
                .collect(Collectors.toSet());

        LocalDate today = LocalDate.now();
        List<StudentAssignmentDto> result = new ArrayList<>();

        for (Assignment assignment : allAssignments) {
            boolean hasSubmitted = submittedAssignmentIds.contains(assignment.getId());
            boolean isDueOrFuture = !assignment.getDueDate().isBefore(today); // dueDate >= today

            switch (normalized) {
                case "pending":
                    // Due date is today or future AND student has NOT submitted
                    if (isDueOrFuture && !hasSubmitted) {
                        result.add(toDto(assignment));
                    }
                    break;

                case "submitted":
                    // Student has submitted, irrespective of date
                    if (hasSubmitted) {
                        result.add(toDto(assignment));
                    }
                    break;

                case "failed submissions":
                    // Due date has passed AND student has NOT submitted
                    if (!isDueOrFuture && !hasSubmitted) {
                        result.add(toDto(assignment));
                    }
                    break;

                default:
                    break;
            }
        }

        return result;
    }

    private StudentAssignmentDto toDto(Assignment assignment) {
        return new StudentAssignmentDto(
                assignment.getId(),
                assignment.getTitle(),
                assignment.getDescription(),
                assignment.getDueDate()
        );
    }
}