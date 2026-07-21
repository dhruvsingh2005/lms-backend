package com.arishi.lms_backend.dto.response;

import com.arishi.lms_backend.enums.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponseDTO {
    private Long enrollmentId;

    private Long studentId;

    private Long courseId;

    private EnrollmentStatus status;
}
