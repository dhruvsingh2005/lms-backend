package com.arishi.lms_backend.repo;
import com.arishi.lms_backend.entity.Enrollment;
import com.arishi.lms_backend.entity.Student;
import com.arishi.lms_backend.dto.response.StudentEnrollmentCourseResponseDTO;
import com.arishi.lms_backend.entity.Course;
import com.arishi.lms_backend.enums.EnrollmentStatus;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface EnrollmentRepo extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentIdAndCourseIdAndStatusAndDeletedAtIsNull(Long studentId, Long courseId, EnrollmentStatus status);

    boolean existsByStudentAndCourseAndDeletedAtIsNull(Student student, Course course);
    @Query("""
            SELECT new com.arishi.lms_backend.dto.response.StudentEnrollmentCourseResponseDTO(c.id,c.title,c.description, i.id,
            CONCAT(i.firstName,' ',i.lastName), c.startDate,c.endDate, c.price, e.createdAt )
            FROM Enrollment e
            JOIN e.course c
            JOIN c.instructor i
            WHERE e.student.id = :studentId
            AND e.status = :status
            AND e.deletedAt IS NULL
            AND c.deletedAt IS NULL
            AND c.endDate >= :today
            ORDER BY e.createdAt DESC""")
        List<StudentEnrollmentCourseResponseDTO> findOngoingEnrollments(
                @Param("studentId") Long studentId,@Param("status") EnrollmentStatus status, @Param("today") LocalDate today
        );
        @Query("""
            SELECT new com.arishi.lms_backend.dto.response.StudentEnrollmentCourseResponseDTO( c.id,c.title,c.description, i.id,
            CONCAT(i.firstName,' ',i.lastName), c.startDate,c.endDate,c.price, e.createdAt )
            FROM Enrollment e
            JOIN e.course c
            JOIN c.instructor i
            WHERE e.student.id = :studentId
            AND e.status = :status
            AND e.deletedAt IS NULL
            AND c.deletedAt IS NULL
            AND c.endDate < :today
            ORDER BY e.createdAt DESC """)
        List<StudentEnrollmentCourseResponseDTO> findCompletedEnrollments(
                @Param("studentId") Long studentId,
                @Param("status") EnrollmentStatus status,
                @Param("today") LocalDate today
        );
}