
package com.arishi.lms_backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEnrollmentCourseResponseDTO {

    private Long courseId;

    private String title;

    private String description;

    private Long instructorId;

    private String instructorName;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal price;

    private ZonedDateTime enrolledAt;
}