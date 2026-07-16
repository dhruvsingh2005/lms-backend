package com.arishi.lms_backend.repo;
import com.arishi.lms_backend.entity.Enrollment;
import com.arishi.lms_backend.entity.Student;
import com.arishi.lms_backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;


public interface  EnrollmentRepository  extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentAndCourse(Student student, Course course);

}