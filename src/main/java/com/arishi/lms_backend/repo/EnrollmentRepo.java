package com.arishi.lms_backend.repo;
import com.arishi.lms_backend.entity.Enrollment;
import com.arishi.lms_backend.entity.Student;
import com.arishi.lms_backend.entity.Course;
import com.arishi.lms_backend.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EnrollmentRepo extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentIdAndCourseIdAndStatusAndDeletedAtIsNull(Long studentId, Long courseId, EnrollmentStatus status);

    boolean existsByStudentAndCourseAndDeletedAtIsNull(Student student, Course course);
}